## Jsoup 设置选项

**Jsoup提供一些操作选项**

### Jsoup.connect(String url) 输入设置
**Jsoup.connect（String url） 接受一个URL，通过`get()``post（）`方法返回文档对象。**
**相关设置说明**
* userAgent(String userAgent)
**让服务器感觉访问者更像一个真实的浏览器在访问。**
* ignoreContentType(boolean ignoreContentType)
**默认情况下，Jsoup只允许使用HTML和XML内容类型的工作,你需要为了与其他内容类型,需要指定为true**
* timeout(int millis)
**请求的超时时间。默认为：3000**
* parser(Parser parser)
**Jsoup 提供`Parser.xmlParser()`和`Parser.htmlParser()`两种类型的解析器。默认为：`Parser.htmlParser()`。如果要解析XML，如RSS或Atom，需要改变解析器类型的XML解析器，否则将无法正常工作**
* data(String key, String value)
**请求参数**
* get() post()
**get和post请求方式**

### Document.OutputSettings 输出设置  
* Character set
**获取/设置 输出文档字符集**
* Escape mode
**获得/设置  HTML的转义输出模式（ escape mode）**
* Indentation
**获取/设置 输出缩进数(空格数)**
* Outline
**打开/关闭 HTML outline mode**
* Pretty print
**打开/关闭  pretty printing mode**
