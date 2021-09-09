package com.xe.core.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.List;

public class XssFiltUtils {

    private Whitelist whiteList;

    /**
     * 配置过滤化参数,不对代码进行格式化
     */
    private Document.OutputSettings outputSettings;

    /**
     * 静态创建HtmlFilter方法
     *
     * @param whiteList 白名单标签
     * @param pretty    是否格式化
     * @return HtmlFilter
     */
    public static XssFiltUtils create(Whitelist whiteList, boolean pretty) {
        XssFiltUtils filter = new XssFiltUtils();
        if (whiteList == null) {
            filter.whiteList = whiteList.relaxed();
        }
        filter.outputSettings = new Document.OutputSettings().prettyPrint(pretty);
        return filter;
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @return HtmlFilter
     */
    public static XssFiltUtils create() {
        return create(null, false);
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @param whiteList 白名单标签
     * @return HtmlFilter
     */
    public static XssFiltUtils create(Whitelist whiteList) {
        return create(whiteList, false);
    }

    /**
     * 静态创建HtmlFilter方法
     *
     * @param excludeTags 例外的特定标签
     * @param includeTags 需要过滤的特定标签
     * @param pretty      是否格式化
     * @return HtmlFilter
     */
    public static XssFiltUtils create(List<String> excludeTags, List<String> includeTags, boolean pretty) {
        XssFiltUtils filter = create(null, pretty);
        //要过滤的标签
        if (includeTags != null && !includeTags.isEmpty()) {
            String[] tags = (String[]) includeTags.toArray(new String[0]);
            filter.whiteList.removeTags(tags);
        }
        //例外标签
        if (excludeTags != null && !excludeTags.isEmpty()) {
            String[] tags = (String[]) excludeTags.toArray(new String[0]);
            filter.whiteList.addTags(tags);
        }
        return filter;
    }


    /**
     * 静态创建HtmlFilter方法
     *
     * @param excludeTags 例外的特定标签
     * @param includeTags 需要过滤的特定标签
     * @return HtmlFilter
     */
    public static XssFiltUtils create(List<String> excludeTags, List<String> includeTags) {
        return create(includeTags, excludeTags, false);
    }

    /**
     * @param content 需要过滤内容
     * @return 过滤后的String
     */
    public String clean(String content) {
        return Jsoup.clean(content, "", this.whiteList, this.outputSettings);

    }

    public static void main(String[] args) {
        //String text = "<a href=\"http://www.baidu.com/a\" onclick=\"alert(1);\"></a><script>alert(0);</script><b style=\"xxx\" onclick=\"<script>alert(0);</script>\">abc</>";
        String text="<script>alert(000)</script>";
        System.out.println(XssFiltUtils.create().clean(text));
    }

}
