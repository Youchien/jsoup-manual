/**
 * Copyright 2017 [https://github.com/bluetata] All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the 'License'); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
