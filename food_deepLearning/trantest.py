from src.translate import Translator
translator=Translator(from_lang="chinese",to_lang="english")
translation = translator.translate("你吃了吗？")
print(translation)
translator2=Translator(from_lang="english",to_lang="chinese")
translation = translator2.translate("Did you eat？")
print(translation)
