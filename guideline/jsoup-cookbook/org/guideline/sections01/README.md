## Jsoup 输入与输出

### 实例过程
**这是一个非常简单的示例：**
* 输入一个不完整HTML代码片段
```
<html><head><title>First parse</title></head>"  + "<body><p>Parsed HTML into a doc.</p></body></html>
```
* 中间无任何操作
* 结果数据：
```
<html>
 <head>
  <title>First parse</title>
 </head>
 <body>
  <p>Parsed HTML into a doc.</p>
  <br>
 </body>
</html>
```

### 实例说明
* Jsoup 是所有数据操作的入口
* Jsoup 将输出完整HTML数据格式。

#### Jsoup 数据输入

* Jsoup 数据输入
	* 一个字符串 （HTML的代码片段）
	* 一个文件 （File）
	* 一个URL
	* 一个Inputstream 的输入流

**示例演示**
```
    Document doc = null;
        try {
            // 输入一个 HTML 片段
            String html = "<html><head><title>First parse</title></head><body><p>Parsed HTML into a doc.</p></body></html>";
            doc = Jsoup.parse(html);

            // 输入一个文件,指定其编码
            doc = Jsoup.parse(new File("resources/index.html"), "utf-8");

            //输入一个URL,指定其超时时间
            doc = Jsoup.parse(new URL("http://www.baidu.com"), 1000);

            //输一个HTTP地址,等价同上
            doc = Jsoup.connect("http://www.baidu.com").get();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 使用Document对象 进行数据提取或遍历  
```

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
