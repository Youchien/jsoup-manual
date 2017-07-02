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

       // jsoupIOTest01();
        try {
            jsoupIOTest03();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    public static void jsoupIOTest01(){
        
        String htmlText = // " <html>" +
                          // "   <head>" +
                             "     <title>JsoupInputAndOutput</title>" +
                             "   </head>" +
                          // "   <body>" +
                             "     <h1>Hello World!</h1>" +
                          // "   </body>" +
                             " </html>";
        Document doc = Jsoup.parse(htmlText);
        System.out.println(doc.html());
    }
    
    
    public static void jsoupIOTest02() throws IOException{

        String fileName = "../jsoup-manual-cookbook/src/manual/resources/section01.html";
        File in = new File(fileName);
        Document doc = Jsoup.parse(in, "UTF-8", "https://github.com/");

        System.out.println(doc.select("img").first().absUrl("src"));
        System.out.println(doc.select("a[href]").first().absUrl("href"));
        
        System.out.println("====================================");
        
        System.out.println(doc.html());
    }
    
    public static void jsoupIOTest03() throws IOException{

        String h = "<dl class='test'>" +
                   "  <dt>"+
                   "    Category"+
                   "  </dt>"+
                   "  <dd> "+
                   "    <a href='/free'>Free</a>" + 
                   "  </dd> ";
        Document d = Jsoup.parse(h);
        String s = d.select("dl > dt").html();
        String s2 = d.select("a").first().parent().previousElementSibling().html();
        System.out.println(s);
        System.out.println(s2);
        
        
       }

}
