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
