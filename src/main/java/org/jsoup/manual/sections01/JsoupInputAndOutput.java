/**
 * Copyright (c) 2017-2018 Sekito Lv(bluetata) <sekito.lv@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
