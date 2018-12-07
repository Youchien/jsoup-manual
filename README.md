# Jsoup 开发指南/操作手册


> *Read this in other languages: [English](README-en.md).*

**Jsoup** QQ交流群：**5**069**5**11**5** </br>
Last update date：11/27/2018 19:16

## 目录

1. [文档概述](#intro)
2. [基本信息](#profile)
3. [手册目录](#content)
   - [x] [Jsoup 解析方式与数据的输入和输出](#input-output)
        - [加载解析一个HTML字符串 （HTML代码片段）](#input01)
        - [加载解析一个文件（HTML File）](#input02)
        - [加载解析一个URL](#input03)
        - [加载解析Inputstream输入流](#input04)
        - [Jsoup 的数据输出](#output01)
   - [x] [Jsoup 模拟浏览器设置选项的相关说明](#simulation-option)
        - [浏览器设置选项](#setting-option01)
        - [浏览器设置选项](#setting-option02)
   - [ ] [Jsoup Dom 操作](#)
   - [ ] [Jsoup 的循环遍历](#)
   - [ ] [jsoup 防止XSS攻击](#)
   - [ ] [Jsoup Utils](#)

4. [参与贡献者](#contributors)
5. [交流讨论](#communication)
6. [版权声明](#license)

<a name="intro"></a>
## 文档概述

本 Repo.手册为Jsoup相关操作的说明，意为对Jsoup使用者及爬虫行业的同学提供资料参考。  


<a name="content"></a>
## Jsoup手册


<a name="input-output"></a>
### Jsoup 解析方式与数据的输入和输出

> 本节所有源代码 [点击这里](/src/main/java/org/jsoup/manual/sections01)

#### 解析方式与数据的输入
  1. [加载解析一个HTML字符串 （HTML代码片段）](#input01)
  2. [加载解析一个文件（HTML File）](#input02)
  3. [加载解析一个URL](#input03)
  4. [加载解析Inputstream输入流](#input04)


<a name="input01"></a>
**1. 加载解析一个HTML字符串 （HTML代码片段）**

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

    String fileName = "../jsoup-manual/src/manual/resources/section01.html";
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

**示例总结：** 该示例使用了Jsoup API：`parse(File in, String charsetName, String baseUri)`
第三个参数为baseURI,就如同HTML文件中`<img src="/img.jpg">`在实际网站中，有的图片、
超链接、js文件、CSS等会使用相对路径，使用Jsoup的带有baseURI方法时，Jsoup会**隐式的**
将该<u>baseURI</u>和<u>其相对路径</u>进行拼接成一个完整的绝对路径，注意：这是隐式的，
也就是说，它不会真正的改变输出的DOM对象，而是你在调用Jsoup 相关API 获取其超链接或者图片等
的时候，返回的Jsoup对象会带有和baseURI拼接后的完整链接，这也就是为什么我们看到打印结果中，
获取的图片地址为完整的绝对路径，而打印的html仍然和html文件保持一致。

**另外：** 在HTML文件中如果已经有`<base href="http://example.com" />`指定了baseURI，
那么**Jsoup会以原HTML文件中的URI为基准**，也就是说，如果原HTML中指定了URI那么即使你调用了
带有baseUri的方法，并且指定了另一个URI，那么Jsoup隐式解析出来的RUL仍然是和原来HTML中的URI
拼接后的完整绝对路径。如果示例中，如果取消注释`base`标签，在代码中指定了的URI为：
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

**解释说明：** connect(String url) 方法创建一个新的 Connection，timeout(int millis)
设置超时时间，userAgent(String userAgent)设置浏览器user-Agent的，userAgent更加详细的
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


**相关数据输入的方法列表**
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


<a name="output01"></a>
#### Jsoup 的数据输出
**Jsoup Parser（Dom解析器） 将会去输入的HTML进行词法解析，修复HTML的完整性**
* 严格的标签闭合 (比如： `<p>Lorem <p>Ipsum` parses to `<p>Lorem</p> <p>Ipsum</p>`)
* 隐式标签 (比如. 它可以自动将 `<td>Table data</td>`包装成`<table><tr><td>?`)
* 创建可靠的文档结构（html标签包含head 和 body，在head只出现恰当的元素）
* HTML 实体转义($、<、> 转化为HTML格式)
* 隐式的以uri为base补全html中相对RUL


<a name="simulation-option"></a>
### Jsoup 模拟浏览器设置选项的相关说明

> 本节所有源代码 [点击这里](/src/main/java/org/jsoup/manual/sections02)

<a name="setting-option01"></a>
#### Jsoup模拟浏览器：`Jsoup.connect(String url)` 的相关选项设置
`Jsoup.connect(String url)` 模拟浏览器访问一个URL，通过`get()`或`post()`
方式返回dom对象。</br>

**相关主要设置说明：**
* `userAgent(String userAgent)`
设置user-Agent，让被访问网站相信这个请求是在一个真实的浏览器访问。
* `ignoreContentType(boolean ignoreContentType)`
默认情况下，Jsoup只允许使用HTML和XML内容类型的工作,你需要为了与其他内容类型,需要指定为true。
* `followRedirects(boolean followRedirects)`
设置重定向是否跳转，默认值为true,即为此次访问会redirect到重定向后的地址。
* `proxy(Proxy proxy)`
设置代理。
* `timeout(int millis)` 请求的超时时间。默认为：30000 millis。
* `parser(Parser parser)` Jsoup 提供`Parser.xmlParser()`和`Parser.htmlParser()`
两种类型的解析器。</br>
默认为：`Parser.htmlParser()`。如果要解析XML，如RSS或Atom，需要改变解析器类型的XML
解析器，否则将无法正常工作。
* `data(String key, String value)` 请求参数，利用该方法可以直接绑定网页提交参数，
cookie，session，url上get提交的参数等。
* `get(), post()` 设置get和post请求方式。


<a name="setting-option02"></a>
#### Jsoup输出 `Document.OutputSettings` 设置选项
该操作输出的类为：**org.jsoup.nodes.Document.OutputSettings**</br>
* `charset` 设置 输出文档字符集，默认为UTF-8
* `escapeMode` 设置 HTML的转义输出模式，默认为：Entities.EscapeMode.base
* `indentAmount` 设置 输出缩进数(空格数)，默认为：indentAmount(1)，一个空格
* `outline` 打开/关闭 自动换行，默认为false
* `prettyPrint` 打开/关闭  pretty printing mode，默认为：true
* `syntax` 设置对象输出的语法，要么是带有空标签或者boolean属性的html，要么是自带闭合的xml。

```java
    public static void jsoupOption01() throws Exception{
        String url = "http://sample.com/";

        // 设置输入选项。将一个URL 地址内容转换为文档对象(Document)
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36")                             // 无默认值
                .ignoreContentType(false)    // 默认为：false
                .timeout(5 * 1000)           // 默认为： 3000
                .parser(Parser.htmlParser()) // 默认为：Parser.htmlParser()
                .data("key", "value")        // 无默认值
                .post();                     // post请求

        // 设置输出选项
        Document.OutputSettings settings = new Document.OutputSettings();
        settings.charset("utf-8")            // 字符集为UTF-8   默认为：UTF-8
                .indentAmount(4)             // 4个空格缩进     默认为：1
                .outline(true)               // 自动换行        默认：false
                .escapeMode(EscapeMode.base) // 转义模式        默认为：Entities.EscapeMode.base
                .prettyPrint(true)           // 优雅输出        默认为：true
                .syntax(Syntax.html);        // 指定输出语法    HTML/XML

        doc = doc.outputSettings(settings);

        System.out.println(doc.html());
    }
```



<a name="input-output"></a>
### 使用Jsoup消除不受信任的HTML (防止XSS攻击)


#### 1. 如何使用Jsoup进行清除HTML标签操作

使用Jsoup的clean 方法进行清除HTML标签操作(该方法位于JsoupAPI：**org.jsoup.Jsoup**下)

```java
static String clean(String strHTML, Whitelist whitelist)
```

该方法会清除在你所指定的白名单whitelist中的所有HTML标签。默认的Jsoup提供了5种Whitelist（org.jsoup.safety.Whitelist）的API，
具体介绍如下：

  **1)**： none()
    该API会清除所有HTML标签，仅保留文本节点。

  **2)**： simpleText()
    该API仅会保留*b, em, i, strong, u* 标签，除此之外的所有HTML标签都会被清除。

  **3)**： basic()
    该API会保留 *a, b, blockquote, br, cite, code, dd, dl, dt, em, i, li, ol, p, pre, q, small, span, strike, strong, sub, sup, u, ul* 和其适当的属性标签，除此之外的所有HTML标签都会被清除，且该API不允许出现图片(img tag)。另外该API中允许出现的超链接中可以允许其指定http, https, ftp, mailto 且在超链接中强制追加rel=nofollow属性。

  **4)**： basicWithImages()
    该API在保留basic()中允许出现的标签的同时也允许出现图片(img tag)和img的相关适当属性，且其src允许其指定 http 或 https。

  **5)**： relaxed()
    该API仅会保留 *a, b, blockquote, br, caption, cite, code, col, colgroup, dd, div, dl, dt, em, h1, h2, h3, h4, h5, h6, i, img, li, ol, p, pre, q, small, span, strike, strong, sub, sup, table, tbody, td, tfoot, th, thead, tr, u, ul 标签，除此之外的所有HTML标签都会被清除，且在超链接中不会强制追加rel=nofollow属性。


> “Jsoup提供了默认的5个白名单过滤器，在此基础上你也可以进行过滤器的扩展和自定义，方法见下文”


#### 2. 如何使用Whitelist过滤器进行清除HTML标签操作


<a name="contributors"></a>
## 参与贡献者
<!-- ALL-CONTRIBUTORS-LIST：START - Do not remove or modify this section -->
<!-- prettier-ignore -->

<!-- ALL-CONTRIBUTORS-LIST：END -->


<a name="communication"></a>
## 交流讨论
可以加置顶群讨论交流或发任何问题或者建议于[Issues](https://github.com/bluetata/jsoup-manual/issues)区域


<a name="license"></a>
## 开源许可

Copyright (c) Sekito Lv(bluetata) <sekito.lv@gmail.com> All rights reserved.

Licensed under the [MIT](LICENSE) License.