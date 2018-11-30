/**
 * Copyright (c) 2006-2018 bluetata/Sekito.Lv@gmail.com
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;

/**
 * @date     11/23/18 16:32</br>
 * @version  jsoup-manual version(1.0)</br>
 * @author   bluetata / Sekito.Lv@gmail.com</br>
 * @since    JDK 1.8</br>
 */
public class JsoupTest {

    public static void main(String[] args){

        Document doc = null;
        String url = "https://en.wikipedia.org/wiki/Nico_Ditch";
        try {
            doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Elements els = doc.select(".mw-parser-output > p:not(#toc ~ p)");
        System.out.println(els);
        // System.out.println(doc);
    }

}
