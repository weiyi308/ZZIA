package com.rdc.ruan.zzia.Main.HttpUtils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Ruan on 2015/3/13.
 */
public class MyJsoup {
    /**
     * 获取名字
     * @param string
     * @return
     */
    public static String getName(String string){
        Document document = Jsoup.parse(string);
        Element element = document.select("span#xhxm").first();
        return element.text();
    }
    /**
     * 获取学年下拉列表
     */
    public static String[] getSpinnerYear(String string){
        Document document = Jsoup.parse(string);
        Element element =document.select("[name=\"ddlxn\"]").first();
        Elements elements = element.select("option");
        String result[]=new String[elements.size()];
        int i=0;
        for (Element link:elements){
            result[i]=link.text();
            i++;
        }
        return result;
    }
    /**
     * 获取学期下拉表
     */
    public static String[] getSpinnerTerm(String string){
        Document document = Jsoup.parse(string);
        Element element =document.select("[name=\"ddlxq\"]").first();
        Elements elements = element.select("option");
        String result[]=new String[elements.size()];
        int i=0;
        for (Element link:elements){
            result[i]=link.text();
            i++;
        }
        return result;
    }
}
