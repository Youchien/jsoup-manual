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
package org.jsoup.manual.sections02;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.parser.Parser;

import java.io.IOException;

/**
 * 通常，开发者使用Jsoup的默认选项
 */
public class JsoupOptions {

    public static void main(String[] args) throws Exception {
        jsoupOption01();    // Test case 01

    }

    public static void jsoupOption01() throws Exception{
        String url = "http://sample.com/";

        // 设置输入选项。将一个URL 地址内容转换为文档对象(Document)
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36")                             // 无默认值
                .ignoreContentType(false)    // 默认为：false
                .timeout(5 * 1000)           // 默认为： 3000
                .parser(Parser.htmlParser()) // 默认为：Parser.htmlParser()
                .data("key", "value")        // 无默认值
                .post();                     // post请求


        // 设置输出选项
        Document.OutputSettings settings = new Document.OutputSettings();
        settings.charset("utf-8")            // 字符集为UTF-8   默认为：UTF-8
                .indentAmount(4)             // 4个空格缩进     默认为：1
                .outline(true)               // 自动换行        默认：false
                .escapeMode(EscapeMode.base) // 转义模式        默认为：Entities.EscapeMode.base
                .prettyPrint(true)           // 优雅输出        默认为：true
                .syntax(Syntax.html);        // 指定输出语法    HTML/XML

        doc = doc.outputSettings(settings);

        System.out.println(doc.html());
    }
}