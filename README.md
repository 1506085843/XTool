# XTool
这是一个使用 javaFX 和 jfoenix 开发的桌面应用，使用 Java 版本 1.8

<img src="https://img.shields.io/badge/Language-Java-orange.svg">

本应用集成了一些常用的工具：

url编/解码、
base64编/解码、
Aes加/解密、
json解析、
多文件中搜索、
时间戳工具、
日期计算、
文本对比


## 使用
下载项目代码并构建后运行 MainDemo.java 启动类即可。
或者你可以先[下载并安装 1.8 版本的 jdk ](https://www.oracle.com/java/technologies/downloads/#java8-windows)，然后在本页面的右侧点击 release 下载发行的 XTool.jar ，然后 电脑 cmd 窗口中使用 java -jar XTool.java 命令运行即可。

## 新增菜单页面及修改
1.如果你要新增页面可以新建你自己的fxml文件和页面相关 XXXController 逻辑处理类，将fxml文件加入到 SideMenu.fxml 菜单页面里并修在 SideMenuController.java 里增加代码即可。
2.如果你要修改应用启动后的初始页面，可以把 MainController.java 类里的 UrlEncodeController 替换为你自己页面的逻辑处理类即可。


## 版权和许可
该项目根据 MIT 许可条款获得许可

