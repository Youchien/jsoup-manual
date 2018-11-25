package org.jsoup.manual.sections01;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

/**
 * Session01:Jsoup 数据输入与输出
 */
public class JsoupInputAndOutput {

    public static void main(String[] args) {

        jsoupIOTest04_2();
    }

    public static void jsoupIOTest01() {

        String htmlText = //  " <html>" +
                          //  "   <head>" +
                              "     <title>JsoupInputAndOutput</title>" + 
                              "   </head>" +
                          //  "   <body>" +
                              "     <h1>Hello World!</h1>" +
                          //  "   </body>" +
                              " </html>";
        Document doc = Jsoup.parse(htmlText);
        System.out.println(doc.html());
    }

    public static void jsoupIOTest02() throws IOException {

        String fileName = "../jsoup-manual-cookbook/src/manual/resources/section01.html";
        File in = new File(fileName);
        Document doc = Jsoup.parse(in, "UTF-8", "https://github.com/");

        System.out.println(doc.select("img").first().absUrl("src"));
        System.out.println(doc.select("a[href]").first().absUrl("href"));

        System.out.println("====================================");

        System.out.println(doc.html());
    }

    public static void jsoupIOTest03() {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.csdn.net/").timeout(4000).userAgent("Mozilla").get();
            System.out.println(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
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
    
    public static void jsoupIOTest04_2() {
        Document doc = null;
        String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/45/14/25/451425202.html";
        try {
            doc = Jsoup.parse(new URL(url).openStream(), "GBK", url);
        } catch (IOException e) {
            e.printStackTrace();
        }  
        System.out.println(doc);  
    }

}
