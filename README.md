# 1.作品简介

**食尚-基于深度学习的菜品识别和学习软件**

随着生活水平的提高，人们对于美食的追求越来越高，但同时也面临着繁忙的生活和工作压力，缺乏时间去研究和学习菜谱和烹饪技巧。因此，“食尚”APP 的出现为用户提供了一个方便、实用和健康的学习和分享平台。
我们开发的“食尚”软件，面向各年龄段、各类群体，专注于让每一位用户饮食更健康、饭菜更丰富、烹饪更便捷。用户只需输入想要烹饪的材料或菜品，便可立即得到详细的菜谱介绍，遇到美食，只需随手一拍，即可得到菜品信息，随时随地掌握美食精髓。此外，本软件还开发出随机选餐的功能，当用户还没有确定好今日的饮食计划时，可以通过此功能获得灵感。



# 2.目录介绍

* food_deepLearning：软件中使用到的中西餐识别模型
* shishang_android：基于Android Studio的前端安卓app设计代码
* shishang_springboot：基于SpringBoot的后端代码设计与实现
* shishang_web：基于Vue的后台管理系统设计与实现
* images：README文档图片





# 3.软件介绍



## 3.1 界面设计

食尚软件以白色为主题，界面设计上符合现在主流手机软件的设计风格，简洁大方。上方搜索栏与菜单栏，底部为首页与个人信息的切换导航栏，主体部分用点击或滑动的方式可以在多个Tab 间切换界面。这样的界面布局方式可以在手机屏幕有限的空间内展示出更多更复杂具体的信息，并极大的优化了用户的操作体验。

![image-20240401144607491](https://github.com/LiangZii/shishang/blob/master/images/image-20240401144607491.png)



## 3.2 物理架构设计

本软件属于C/S 架构模型，前端采用Android Studio 进行开发，后端利用SpringBoot框架开发，并搭载了本团队训练的中餐识别模型与西餐识别模型。
系统物理架构如下：

![image-20240401143253187](https://github.com/LiangZii/shishang/blob/master/images/image-20240401143253187.png)

此外，我们还为后台管理人员开发了web 后台管理系统，采用B/S 架构，对数据库中的用户信息，菜谱信息进行可视化集成管理。其系统物理架构如下：

![image-20240401143322800](https://github.com/LiangZii/shishang/blob/master/images/image-20240401143322800.png)

## 3.3 逻辑架构设计

**3.3.1** 食尚app 分为登录子系统、菜谱操作子系统、用户个人界面、主界面和拍照识别系统五大部分：
（1）登录子系统包括注册登录和手机验证码登录。
（2）菜谱子系统中，可以对菜谱进行收藏，搜索，以及联动菜谱创建笔记等操作。
（3）用户个人界面中，包括浏览历史、修改个人信息和随机推荐。
（4）主界面包括菜谱推荐和根据食材推荐菜品。
（5）拍照识别系统中，我们在调用百度API 的同时，实现了自己的西餐识别模型和中餐识别模型。

系统逻辑架构设计如下：

![image-20240401143509701](https://github.com/LiangZii/shishang/blob/master/images/image-20240401143509701.png)

**3.3.2** Web 后台管理系统逻辑架构如下：

![image-20240401143609522](https://github.com/LiangZii/shishang/blob/master/images/image-20240401143609522.png)





# 4.核心技术

## 4.1 Android设计

![image-20240401145553607](https://github.com/LiangZii/shishang/blob/master/images/image-20240401145553607.png)

##  4.2 SpringBoot设计

![image-20240401145947140](https://github.com/LiangZii/shishang/blob/master/images/image-20240401145947140.png)

## 4.3 中餐模型介绍

![image-20240401150717102](https://github.com/LiangZii/shishang/blob/master/images/image-20240401150717102.png)

## 4.4 西餐模型介绍

![image-20240401150754307](https://github.com/LiangZii/shishang/blob/master/images/image-20240401150754307.png)

## 4.5 技术栈一览

![image-20240401144652632](https://github.com/LiangZii/shishang/blob/master/images/image-20240401144652632.png)

# 5.演示视频

作品演示链接：https://pan.baidu.com/s/10monlpvhHfWfbxfyZIVaWA?pwd=o7at 

链接目录：

* 食尚-介绍pdf
* 食尚-软件介绍完整版-8分51秒.mp4
* 食尚-软件演示单版-3分33秒.mp4





感谢~~

