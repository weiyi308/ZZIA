package com.rdc.ruan.zzia.Main.HttpUtils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruan on 2015/3/13.
 */
public class MyJsoup {
    /**
     * 获取课程表
     */
    private static String mainUrl;
    public static void setMainUrl(String str){
        mainUrl=str;
    }
    public static String getMainUrl(){
        return mainUrl;
    }
    public static String getClassTable(String string){
        Document document = Jsoup.parse(string);
        Element element = document.select("table.datelist").first();
        String result=element.html();
        return result;
    }
    /**
     * 获取成绩表
     */
    public static String getTable(String string){
        Document document = Jsoup.parse(string);
        Element element = document.select("table.datelist").first();
        String result=element.html();
        return result;
    }

    public static String getCetTable(String string){
        Document document = Jsoup.parse(string);
        Element element = document.select("table.cetTable").first();
        String result=element.html();
        return result;
    }
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
    /**
     * 解析课程表
     */
    public static List<ClassInfo> getClassInfos(String string){
        Document document = Jsoup.parse(string);
        Elements table = document.select("table.datelist");
        Elements trs = table.select("tr");
        List<ClassInfo> list = new ArrayList<>();
        for (Element tr:trs){
            Elements item = tr.select("td");
            String[] tds = new String[item.size()];
            int i = 0;
            for (Element td:item){
                tds[i] = td.text();
                i++;
            }
            ClassInfo classInfo = new ClassInfo(tds);
            list.add(classInfo);
        }
        return list;
    }
}
