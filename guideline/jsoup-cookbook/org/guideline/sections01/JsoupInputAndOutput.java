package org.guideline.sections01;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Session01:Jsoup 数据输入与输出
 */
public class JsoupInputAndOutput {

    public static void main(String[] args) {

       jsoupIOTest01();
    }
    
    
    public static void jsoupIOTest01(){
        
        String htmlText = // " <html>" +
                          // "   <head>" +
                             "     <title>JsoupInputAndOutput</title>" +
                             "   </head>" +
                             "   <body>" +
                             "     <h1>Hello World!</h1>" +
                             "   </body>" +
                             " </html>";
        Document doc1 = Jsoup.parse(htmlText);
        System.out.println(doc1.html());
    }

}
