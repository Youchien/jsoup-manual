/**
 * Copyright (c) 2017-2018 Sekito Lv(bluetata) <sekito.lv@gmail.com>
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
package org.jsoup.manual.sections06;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @date     12/10/18 17:17</br>
 * @version  jsoup-manual version(1.0)</br>
 * @author   bluetata / Sekito.Lv@gmail.com</br>
 * @since    JDK 1.8</br>
 */
public class ParseJSWebSite {

    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {

        // 屏蔽HtmlUnit等系统 log
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);

        String url = "https://www.newsmth.net/nForum/#!section/Estate";
        System.out.println("Loading page now-----------------------------------------------: "+url);

        /* HtmlUnit 模拟浏览器 */
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);              // 启用JS解释器，默认为true
        webClient.getOptions().setCssEnabled(false);                    // 禁用css支持
        webClient.getOptions().setThrowExceptionOnScriptError(false);   // js运行错误时，是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(10 * 1000);                   // 设置连接超时时间
        HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(30 * 1000);               // 等待js后台执行30秒

        String pageAsXml = page.asXml();

        /* Jsoup解析处理 */
        // Document doc = Jsoup.parse(pageAsXml, "https://bluetata.com/");
        Document doc = Jsoup.parse(pageAsXml);
        //Elements pngs = doc.select("img[src$=.png]");                   // 获取所有图片元素集

        Elements eles = doc.select("td.title_1");
        // 其他操作
        System.out.println(eles.toString());
    }


//    public void testCrawler() throws Exception {
//        /**HtmlUnit请求web页面*/
//        WebClient wc = new WebClient();
//        wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
//        wc.getOptions().setCssEnabled(false); //禁用css支持
//        wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
//        wc.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
//        HtmlPage page = wc.getPage("http://cq.qq.com/baoliao/detail.htm?294064");
//        String pageXml = page.asXml(); //以xml的形式获取响应文本
//
//        /**jsoup解析文档*/
//        Document doc = Jsoup.parse(pageXml, "http://cq.qq.com");
//        Element pv = doc.select("#feed_content span").get(1);
//        System.out.println(pv.text());
//        // Assert.assertTrue(pv.text().contains("浏览"));
//
//        System.out.println("Thank God!");
//    }

}
