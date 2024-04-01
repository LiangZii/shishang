import json, requests

def translate(query):
    url = 'http://fanyi.youdao.com/translate'
    data = {
        "i": query,  # 待翻译的字符串
        "from": "AUTO",
        "to": "AUTO",
        "smartresult": "dict",
        "client": "fanyideskweb",
        "salt": "16081210430989",
        "doctype": "json",
        "version": "2.1",
        "keyfrom": "fanyi.web",
        "action": "FY_BY_CLICKBUTTION"
    }
    res = requests.post(url, data=data).json()
    # print(json.dumps(res, indent=2, ensure_ascii=False))
    transresult = ""
    for i in range(len(res['translateResult'][0])):
        transresult = transresult + res['translateResult'][0][i]['tgt']

    # print(res['translateResult'][0][]['tgt'])
    # print(transresult)
    return transresult


translate("-Season chicken thighs with pepper to taste.-Heat olive oil in a skillet over medium heat; add chicken to skillet and cook, turning once, until browned and no longer pink inside, about 5 minutes.-Place chicken and onion into the skillet and continue cooking until tender and lightly browned on each side, about 5 minutes more.-Stir tomato sauce and water into the skillet and bring to a boil.-Mix in rice and cook until the liquid is almost completely absorbed, about 10 minutes.")
