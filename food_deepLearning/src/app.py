import os
import pickle
import time

import torch

import torchvision
from flask import Flask, request

from torchvision import transforms
from PIL import Image
from model import get_model
import pandas
from args import get_parser
from utils.output_utils import prepare_output
from translate import translate






app = Flask(__name__)

# use_gpu = False
# device = torch.device('cuda' if torch.cuda.is_available() and use_gpu else 'cpu')


data_dir = '../data'
print()
use_gpu = True
device = torch.device('cuda' if torch.cuda.is_available() and use_gpu else 'cpu')
map_loc = None if torch.cuda.is_available() and use_gpu else 'cpu'
model_path = os.path.join(data_dir, 'modelbest.ckpt')

ingrs_vocab = pickle.load(open(os.path.join(data_dir, 'ingr_vocab.pkl'), 'rb'))
# ingrs_vocab = pickle.load(open("./data/ingr_vocab.pkl"))
vocab = pickle.load(open(os.path.join(data_dir, 'instr_vocab.pkl'), 'rb'))
ingr_vocab_size = len(ingrs_vocab)
instrs_vocab_size = len(vocab)
output_dim = instrs_vocab_size
t = time.time()
args = get_parser()
args.maxseqlen = 15
args.ingrs_only = False

enfood_model = get_model(args, ingr_vocab_size, instrs_vocab_size)

enfood_model.load_state_dict(torch.load(model_path, map_location=map_loc))
enfood_model.to(device)
enfood_model.eval()
enfood_model.ingrs_only = False
enfood_model.recipe_only = False
image_folder = os.path.join(data_dir, 'demo_imgs')
transf_list_batch = []
transf_list_batch.append(transforms.ToTensor())
transf_list_batch.append(transforms.Normalize((0.485, 0.456, 0.406),
                                              (0.229, 0.224, 0.225)))
to_input_transf = transforms.Compose(transf_list_batch)
greedy = [True, False, False]
beam = [-1, -1, -1, -1]
temperature = 1.0
numgens = len(greedy)

use_urls = False  # set to true to load images from demo_urls instead of those in test_imgs folder
show_anyways = False  # if True, it will show the recipe even if it's not valid


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/predict_chfood', methods=['POST'])
def predict_chfood():
    # device = 'cpu'
    classname = pandas.read_excel('class_names.xls')
    img = request.files['image']
    img = Image.open(img).convert('RGB')
    # device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")
    # device = 'cpu'
    model = torchvision.models.resnet50(pretrained=False).to(device)

    checkpoint = torch.load('model_data/resmodel-32-1.069.pt',map_location="cpu")
    model.load_state_dict(checkpoint['model'])
    model.eval() # 制定model.eval()固定dropout和BN层。
    # img = Image.open('./ChineseFoodNet/release_data/train/000/000000.jpg').convert('RGB')
    # img = Image.open('dataset/000000.jpg').convert('RGB')
    img = transforms.Compose([
        transforms.CenterCrop(500),
        transforms.Resize(128),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.52011104, 0.44459117, 0.30962785], std=[0.25595631, 0.25862494, 0.26925405])
    ])(img)
    img = img.reshape(1, 3, 128, 128).to(device)
    pre = model(img)

    _, predicted_top3 = pre.topk(3, dim=1)
    pretop3 = predicted_top3.tolist()

    pre_json = {}

    pre_json["top1-chname"] = classname["chname"][pretop3[0][0]]
    pre_json["top1-enname"] = classname["enname"][pretop3[0][0]]
    # print(pretop3[0][0])
    # print(pre[0])
    # pre_json
    pre_json["top1-acc"] = "{:.2f}".format(pre.tolist()[0][pretop3[0][0]]*10) + "%"
    pre_json["top2-chname"] = classname["chname"][pretop3[0][1]]
    pre_json["top2-enname"] = classname["enname"][pretop3[0][1]]
    pre_json["top2-acc"] = "{:.2f}".format(pre.tolist()[0][pretop3[0][1]] * 10) + "%"
    pre_json["top3-chname"] = classname["chname"][pretop3[0][2]]
    pre_json["top3-enname"] = classname["enname"][pretop3[0][2]]
    pre_json["top3-acc"] = "{:.2f}".format(pre.tolist()[0][pretop3[0][2]] * 10) + "%"

    print(pre_json)





    return pre_json



@app.route('/predict_enfood', methods=['POST'])
def predict_enfood():
    image = request.files['image']

    # model = torch.jit.load('path/to/model.pth')


    image = Image.open(image).convert('RGB')

    transf_list = []
    transf_list.append(transforms.Resize(256))
    transf_list.append(transforms.CenterCrop(224))
    transform = transforms.Compose(transf_list)
    image_transf = transform(image)
    image_tensor = to_input_transf(image_transf).unsqueeze(0).to(device)


    num_valid = 1
    prejson = {}
    for i in range(numgens):
        with torch.no_grad():
            outputs = enfood_model.sample(image_tensor, greedy=greedy[i],
                                   temperature=temperature, beam=beam[i], true_ingrs=None)

        ingr_ids = outputs['ingr_ids'].cpu().numpy()
        recipe_ids = outputs['recipe_ids'].cpu().numpy()

        outs, valid = prepare_output(recipe_ids[0], ingr_ids[0], ingrs_vocab, vocab)

        if valid['is_valid'] or show_anyways:

            print('RECIPE', num_valid)
            str0 = "top" + str(num_valid) + "-isvalid"
            str1 = "top" + str(num_valid) + "-name"
            str2 = "top" + str(num_valid) + "-ingredients"
            str3 = "top" + str(num_valid) + "-instructions"
            num_valid += 1


            # print ("greedy:", greedy[i], "beam:", beam[i])

            BOLD = '\033[1m'
            END = '\033[0m'
            print(BOLD + '\nTitle:' + END, outs['title'])

            print(BOLD + '\nIngredients:' + END)
            print(', '.join(outs['ingrs']))

            print(BOLD + '\nInstructions:' + END)
            print('-' + '\n-'.join(outs['recipe']))

            print('=' * 20)
            prejson[str0] = "is_valid"
            # str = outs['recipe']
            instruct = ""
            ingrs = ""
            for i in range(len(outs['recipe'])):
                outs['recipe'][i] = translate(outs['recipe'][i])
                # instruct = instruct + outs['recipe'][i]

            for i in range(len(outs['ingrs'])):
                outs['ingrs'][i] = translate(outs['ingrs'][i])


            # print(str(outs['recipe']))
            prejson[str1] = translate(outs['title'])
            prejson[str2] = outs['recipe']
            prejson[str3] = outs['ingrs']

        else:
            pass
            prejson[str0] = "not_valid"
            prejson["reason"] = translate(valid['reason'])

            print("Not a valid recipe!")
            print("Reason: ", valid['reason'])



    return prejson



if __name__ == '__main__':
    app.run(host='0.0.0.0',port = 5000)
# host='127.0.0.1',port = 5000

