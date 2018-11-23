## Jsoup 输入输出设置的相关说明

### Jsoup模拟浏览器：Jsoup.connect(String url) 的相关设置
**Jsoup.connect（String url） 接受一个URL，通过`get()`和`post（）`方法返回dom对象。**
**相关主要设置说明**
* **userAgent(String userAgent)**
设置user-Agent，让被访问网站相信这个请求是在一个真实的浏览器访问。
* **ignoreContentType(boolean ignoreContentType)**
默认情况下，Jsoup只允许使用HTML和XML内容类型的工作,你需要为了与其他内容类型,需要指定为true。
* **followRedirects(boolean followRedirects)**
设置重定向是否跳转，默认值为true,即为此次访问会redirect到重定向后的地址。
* **proxy(Proxy proxy)**
设置代理。
* **timeout(int millis)**
请求的超时时间。默认为：30000 millis。
* **parser(Parser parser)**
Jsoup 提供`Parser.xmlParser()`和`Parser.htmlParser()`两种类型的解析器。默认为：`Parser.htmlParser()`。如果要解析XML，如RSS或Atom，需要改变解析器类型的XML解析器，否则将无法正常工作。
* **data(String key, String value)**
请求参数，利用该方法可以直接绑定网页提交参数，cookie，session，url上get提交的参数等。
* **get(),post()**
设置get和post请求方式。


### Document.OutputSettings 输出设置  
该操作输出的类为：org.jsoup.nodes.Document.OutputSettings
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
