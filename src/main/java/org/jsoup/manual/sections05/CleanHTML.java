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
package org.jsoup.manual.sections05;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * @date     12/07/18 17:46</br>
 * @version  jsoup-manual version(1.0)</br>
 * @author   bluetata / Sekito.Lv@gmail.com</br>
 * @since    JDK 1.8</br>
 */
public class CleanHTML {

    public static void main(String[] args) {
        String strHTML = "<html>" +
                "<head>" +
                "<title> Clean HTML By Jsoup Whitelist</title>" +
                "</head>" +
                "<body bgcolor=\"000000\">" +
                "<center><img src=\"image.jpg\" align=\"bottom\"> </center>" +
                "<hr>" +
                "<a href=\"http://blog.csdn.net/dietime1943\">bluetata</a>" +
                "<h1>heading tags H1</h1>" +
                "<h2>heading tags H2</h2>" +
                "My email link <a href=\"mailto:dietime1943@gmail.com\">" +
                "dietime1943@gmail.com</a>." +
                "<p>Para tag</p>" +
                "<p><b>bold paragraph</b>" +
                "<br><b><i>bold italics text.</i></b>" +
                "<hr>Horizontal line" +
                "</body>" +
                "</html>";

        //clean HTML using none whitelist (remove all HTML tags)
        String cleanedHTML = Jsoup.clean(strHTML, Whitelist.none());
        System.out.println("None whitelist");
        System.out.println(cleanedHTML);

        System.out.println("===================================");

        //clean HTML using relaxed whitelist
        cleanedHTML = Jsoup.clean(strHTML, Whitelist.relaxed());
        System.out.println("Relaxed whitelist");
        System.out.println(cleanedHTML);
    }

}
