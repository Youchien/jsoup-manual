package org.jsoup.manual.sections09;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ParseHTML {
    
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
