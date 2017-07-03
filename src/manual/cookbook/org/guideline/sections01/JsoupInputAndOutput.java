package org.guideline.sections01;

import java.io.File;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Session01:Jsoup 数据输入与输出
 */
public class JsoupInputAndOutput {

    public static void main(String[] args) {

        jsoupIOTest03();
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

}
