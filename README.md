# Jsoup 开发指南/操作手册

> *Read this in other languages: [English](README-en.md).*

QQ交流群：**5**069**5**11**5**


## 目录

1. [文档概述](#intro)
2. [基本信息](#profile)
3. [手册目录](#content)
   - [x] [Jsoup 输入与输出](/src/manual/cookbook/org/guideline/sections01/README.md)
   - [x] [Jsoup 输入输出设置的相关说明](/src/manual/cookbook/org/guideline/sections02/README.md)


4. [参与贡献者](#contributors)
5. [交流讨论](#communication)
6. [版权声明](#copyright)

<a name="intro"></a>
## 文档概述

Jsoup经验总结；如果出现链接失效等情况，可提交 [Issues](https://github.com/bluetata/jsoup-manual/issues) 提醒我修改相关内容。

<a name="profile"></a>
## 基本信息

| 文档名称 | Jsoup-Manual / Jsoup开发手册                   |
| ------- | --------------------------------------------- |
| 当前版本 | v1.2 beta                                     |
| 文档发起 | [bluetata](http://blog.csdn.net/dietime1943/) |
| 参与人群 | Jimmy                                         |
| 最后更新 | 2018/11/25                                    |

<a name="content"></a>
## Jsoup手册

### Jsoup 输入与输出

#### Jsoup 输入
  1. [加载解析一个HTML字符串 （HTML代码片段）](#input01)
  2. [加载解析一个文件（HTML File）](#input02)
  3. [加载解析一个URL](#input03)
  4. [加载解析Inputstream输入流](#input04)


<a name="input01"></a>
**1. 解析解析HTML字符串**

**代码示例：**
```java
public static void jsoupIOTest01(){

    String htmlText = // " <html>" +
                      // "   <head>" +
                         "     <title>JsoupInputAndOutput</title>" +
                         "   </head>" +
                      // "   <body>" +
                         "     <h1>Hello World!</h1>" +
                      // "   </body>" +
                         " </html>";
    Document doc1 = Jsoup.parse(htmlText);
    System.out.println(doc1.html());
}
```
**代码输出结果：**
```html
<html>
 <head>
  <title>JsoupInputAndOutput</title>
 </head>
 <body>
  <h1>Hello World!</h1>  
 </body>
</html>
```
从代码输出结果可以看出：
Jsoup在解析代码片段的时候可以补全基本的html标准格式，即使html块中缺失了head，body等标签。

<a name="input02"></a>
**2. 加载解析一个文件（HTML File）**

**HTML文件：**
```html
<!-- HTML file -->
<!DOCTYPE html>
<html>
    <head>
        <title>JsoupInputTest</title>
        <meta charset="UTF-8">
    </head>
    <!-- <base href="http://example.com" /> -->
    <body>
        <div id="mydiv">test parsing input file by jsoup</div>
        <img src="/img.jpg">
        <a href="/a.jpg">s1 test</a>
    </body>
</html>
```

**代码示例：**
```java
public static void jsoupIOTest02() throws IOException{

    String fileName = "../jsoup-manual-cookbook/src/manual/resources/section01.html";
    File in = new File(fileName);
    Document doc = Jsoup.parse(in, "UTF-8", "https://github.com/");

    System.out.println(doc.select("img").first().absUrl("src"));
    System.out.println(doc.select("a[href]").first().absUrl("href"));

    System.out.println("====================================");

    System.out.println(doc.html());
}
```
**代码输出结果：**
```html
https://github.com/img.jpg
https://github.com/a.jpg
====================================
<!-- HTML file --><!doctype html>
<html>
 <head>
  <title>JsoupInputTest</title>
  <meta charset="UTF-8">
 </head>
 <!-- <base href="http://example.com" /> -->
 <body>
  <div id="mydiv">
   test parsing input file by jsoup
  </div>
  <img src="/img.jpg">
  <a href="/a.jpg">s1 test</a>  
 </body>
</html>
```
**示例总结：** 该示例使用了API`parse(File in, String charsetName, String baseUri)`
第三个参数为baseURI,就如同HTML文件中`<img src="/img.jpg">`在实际网站中，有的图片，
超链接，js文件以及CSS会使用相对路径，使用Jsoup的该带有baseURI方法时，
Jsoup会**隐式的**将该baseURI和相对路径进行拼接成一个完整的绝对路径，注意是隐式的，
也就是说，它不会真正的改变输出的DOM对象，而是你在调用Jsoup 相关API 获取其超链接或者
图片等地的时候，返回的Jsoup对象，带有其和baseURI拼接后的完整链接，这也就是为什么我们看到
打印结果中，获取的图片地址为完整的绝对路径，而打印的html仍然和html文件保持一致。

**另外：** 在HTML文件中如果已经有`<base href="http://example.com" />`指定了baseURI，
那么**Jsoup会以原HTML文件中的URI为基准**，也就是说，如果原HTML中指定了URI那么即使你调用了
带有baseUri的方法，并且指定了另一个URI，那么Jsoup隐式解析出来的RUL仍然是和原来HTML中的URI
拼接后的完整绝对路径。如果示例中，如果取消注释`base`标签，在代码中指定了的uri为：
`https://github.com/`，那么在输出打印的时候，获取到的图片完整链接为：
`http://example.com/img.jpg`而不会是`https://github.com/img.jpg`。

<a name="input03"></a>
**3. 加载解析一个URL**

**代码示例：**
```java
public static void jsoupIOTest03() {
    Document doc = null;
    try {
        doc = Jsoup.connect("http://www.csdn.net/").timeout(4000).userAgent("Mozilla").get();
        System.out.println(doc);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```
**解释说明：** connect(String url) 方法创建一个新的 Connection,timeout(int millis) 
设置超时时间,userAgent(String userAgent)设置浏览器user-Agent的,userAgent更加详细的
讲解可以参看：「[浏览器的UserAgent大全](http://blog.csdn.net/dietime1943/article/details/62433531)」
关于和get() 取得和解析一个HTML文件。如果从该URL获取HTML时发生错误，便会抛出 IOException，
应适当的进行处理。该示例为get()方式进行模拟浏览器进行提交,另一种方式为post()方式进行提交，
详细参照：[模拟浏览器：post方式模拟登陆获取网页数据（二）](http://blog.csdn.net/dietime1943/article/details/73294442)


<a name="input04"></a>
**4. 加载解析Inputstream输入流**

基础API方法：`parse(InputStream in, String charsetName, String baseUri)`</br>
拓展API方法：`parse(InputStream in, String charsetName, String baseUri, Parser parser)`

**代码示例01：** 读取一个流文件
```java
public static void jsoupIOTest04_1() {
    // 效率最快的方式进行读文件成input流后，Jsoup进行解析。
    InputStream input = null;
    Document doc = null;
    try {
        input = new DataInputStream(new BufferedInputStream(new FileInputStream("../jsoup-manual-cookbook/src/manual/resources/section01.txt")));
        doc = Jsoup.parse(input, "UTF-8", "https://github.com/", Parser.htmlParser());  
        input.close();
    } catch (IOException e) {
        e.printStackTrace();
    }  
    System.out.println(doc);  
}
```
**代码示例02：** 另一种应用，加载一个URL转换成流，并且进行转码后利用Jsoup进行解析。
```java
public static void jsoupIOTest04_2() {
    Document doc = null;
    String url = "http://www.csdn.net/";
    try {
        doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
    } catch (IOException e) {
        e.printStackTrace();
    }  
    System.out.println(doc);  
}
```
**示例总结：** 两种方法都是通过parse方法来加载流，并且在加载流的过程中，进行了相应的
转码操作，这里应该尽量避免使用`new String(doc.getBytes("ISO8859-1"), "UTF-8")`
这种方式进行转码。


**相关数据输入方法**
* parse(String html)
将输入的HTML解析为一个文档对象（Document）
* parse(String html, String baseUri)
将输入的HTML解析为一个文档对象（Document），baseUri 是用来将相对 URL 转成绝对URL，
并指定从哪个网站获取文档。
* parse(File in, String charsetName, String baseUri)
加载和解析一个HTML文件,baseUri 参数用于解决文件中URLs是相对路径的问题。
如果不需要可以传入一个空的字符串。
* parse(File in, String charsetName)
加载和解析一个HTML文件，使用文件的路径做为 baseUri。 这个方法适用于如果被解析文件位于
网站的本地文件系统，且相关链接也指向该文件系统。
* parse(InputStream in, String charsetName, String baseUri)
读取一个输入流，解析为一个文档对象
* parse(InputStream in, String charsetName, String baseUri, Parser parser)
读取一个输入流，接受编码格式及其baseUri参数和一个解析器（Parser），注意该解析器有两种：
HTML解析器即`Parser.htmlParser`,XML解析器`Parser.xmlParser`。
* parse(URL url, int timeoutMillis)
输入一个URL地址，并指定超时时间
* parseBodyFragment(String bodyHtml)
解析的HTML代码片段，它可能是不完整的（只有一个div或几个p标签）。
* connect(String url)
等价于`parse(URL url, int timeoutMillis)`，只需要输入一个HTTP地址。
返回`Connection`对象，通过`get()`或`post()`方法获取Dom对象。

#### Jsoup 数据输出
**Jsoup Parser（Dom解析器） 将会去输入的HTML进行词法解析，修复HTML的完整性**
* 严格的标签闭合 (比如： `<p>Lorem <p>Ipsum` parses to `<p>Lorem</p> <p>Ipsum</p>`)
* 隐式标签 (比如. 它可以自动将 `<td>Table data</td>`包装成`<table><tr><td>?`)
* 创建可靠的文档结构（html标签包含head 和 body，在head只出现恰当的元素）
* HTML 实体转义($、<、> 转化为HTML格式)
* 隐式的以uri为base补全html中相对RUL

### Jsoup 输入输出设置的相关说明




<a name="contributors"></a>
## 参与贡献者
<!-- ALL-CONTRIBUTORS-LIST：START - Do not remove or modify this section -->
<!-- prettier-ignore -->

<!-- ALL-CONTRIBUTORS-LIST：END -->


<a name="communication"></a>
## 交流讨论
可以加置顶群讨论交流或发任何问题或者建议于[Issues](https://github.com/bluetata/jsoup-manual/issues)区域


<a name="copyright"></a>
## 版权声明
除注明外，本Repository均采用 Creative Commons [BY-NC-ND 4.0](http://creativecommons.org/licenses/by-nc-nd/4.0/deed.zh)（自由转载-保持署名-非商用-禁止演绎）协议发布。
