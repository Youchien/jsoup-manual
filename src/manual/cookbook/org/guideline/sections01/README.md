## Jsoup 输入与输出

#### Jsoup 数据输入

* Jsoup 输入
	* [一个HTML字符串 （HTML代码片段）](#input01)
	* [一个文件 （File）](#input02)
	* 一个URL
	* 一个Inputstream 的输入流

**示例演示**

  <a name="input01"></a>
  **1. 解析解析html字符串**

代码示例：
```
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
代码输出结果：
```
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
	**2. 解析一个html文件**

HTML文件：
```
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

代码示例：
```
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
打印结果：
```
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
**示例总结：** 该示例使用了API`parse(File in, String charsetName, String baseUri)`第三个参数为baseURI,就如同HTML文件中`<img src="/img.jpg">`在实际网站中，有的图片，超链接，js文件以及CSS会使用相对路径，使用Jsoup的该带有baseURI方法时，Jsoup会**隐式的**将该baseURI和相对路径进行拼接成一个完整的绝对路径，注意是隐式的，也就是说，它不会真正的改变输出的DOM对象，而是你在调用Jsoup 相关API 获取其超链接或者图片等地的时候，返回的Jsoup对象，带有其和baseURI拼接后的完整链接，这也就是为什么我们看到打印结果中，获取的图片地址为完整的绝对路径，而打印的html仍然和html文件保持一致。

**另外:** 在HTML文件中如果已经有`<base href="http://example.com" />`指定了baseURI，那么**Jsoup会以原HTML文件中的URI为基准**，也就是说，如果原HTML中指定了URI那么即使你调用了带有baseUri的方法，并且指定了另一个URI，那么Jsoup隐式解析出来的RUL仍然是和原来HTML中的URI拼接后的完整绝对路径。如果示例中，如果取消注释`base`标签，在代码中指定了的uri为：`https://github.com/`，那么在输出打印的时候，获取到的图片完整链接为：`http://example.com/img.jpg`而不会是`https://github.com/img.jpg`。

**相关数据输入方法**
* parse(String html)
将输入的HTML解析为一个文档对象（Document）
* parse(String html, String baseUri)
将输入的HTML解析为一个文档对象（Document），baseUri 是用来将相对 URL 转成绝对URL，并指定从哪个网站获取文档。
* parse(File in, String charsetName, String baseUri)
加载和解析一个HTML文件,baseUri 参数用于解决文件中URLs是相对路径的问题。如果不需要可以传入一个空的字符串。
* parse(File in, String charsetName)
加载和解析一个HTML文件，使用文件的路径做为 baseUri。 这个方法适用于如果被解析文件位于网站的本地文件系统，且相关链接也指向该文件系统。
* parse(InputStream in, String charsetName, String baseUri)
读取一个输入流，解析为一个文档对象
* parse(InputStream in, String charsetName, String baseUri, Parser parser)
读取一个输入流，接受编码格式及其baseUri参数和一个解析器（Parser）
* parse(URL url, int timeoutMillis)
输入一个URL地址，并指定超时时间
* parseBodyFragment(String bodyHtml)
解析的HTML代码片段，它可能是不完整的（只有一个div或几个p标签）。
* connect(String url)
等价于`parse(URL url, int timeoutMillis)`，只需要输入一个HTTP地址。返回`Connection`对象，通过`get()`或`post()`方法获取文档对象。

#### Jsoup 数据输出
**Jsoup Parser（Dom解析器） 将会去输入的HTML进行词法解析，修复HTML的完整性**
* 严格的标签闭合 (比如： `<p>Lorem <p>Ipsum` parses to `<p>Lorem</p> <p>Ipsum</p>`)
* 隐式标签 (比如. 它可以自动将 `<td>Table data</td>`包装成`<table><tr><td>?`)
* 创建可靠的文档结构（html标签包含head 和 body，在head只出现恰当的元素）
* HTML 实体转义($、<、> 转化为HTML格式)
