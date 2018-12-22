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
