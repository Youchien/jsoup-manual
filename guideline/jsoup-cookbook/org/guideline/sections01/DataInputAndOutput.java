package org.guideline.sections01;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Jsoup 数据输入与输出
 */
public class DataInputAndOutput {

	public static void main(String[] args) {
		
		// 一个不完整的HTML代码片段
		String html = "<title>First parse<body><p>Parsed HTML into a doc.</p></br>";
		
		//Jsoup 的数据输入操作大部分都是通过重载的parse() 方法完成。 并返回Document(文档对象)
		Document doc = Jsoup.parse(html);
		
		//Document 包含大量DOM操作
		
		// 使用文档对象(document) 进行数据输出
		System.out.println(doc.html());
		
	}
	
}
