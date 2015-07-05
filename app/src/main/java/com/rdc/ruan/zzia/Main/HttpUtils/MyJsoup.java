package com.rdc.ruan.zzia.Main.HttpUtils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ruan on 2015/3/13.
 */
public class MyJsoup {
    /**
     *获取四六级成绩
     */
    public static List<Map<String,String>> getCet(String str){
        List<Map<String,String>> list = new ArrayList<>();
        Document document = Jsoup.parse(str);
        Element element = document.select("table.datelist").first();
        Elements trs = element.select("tr");
        trs.remove(0);
        if (trs.isEmpty()){
            return list;
        }
        for (Element tr:trs){
            Map<String,String> map = new HashMap<>();
            Elements tds = tr.select("td");
            String year = tds.get(0).text();
            String term = tds.get(1).text();
            String name = tds.get(2).text();
            String time = tds.get(4).text();
            String score = tds.get(5).text();
            map.put("year",year);
            map.put("term",term);
            map.put("name",name);
            map.put("time",time);
            map.put("score",score);
            list.add(map);
        }
        return list;
    }
    /**
     * 获取个人信息
     */
    public static Map<String,String> getDetail(String str){
        Document document = Jsoup.parse(str);
        Map<String,String> map = new HashMap<>();
        Element element = document.select("table.formlist").first();
        String name = element.select("span#xm").text();
        map.put("name",name);
        String image = element.select("img#xszp").attr("src");
        map.put("image",image);
        String id = element.select("span#xh").text();
        map.put("id",id);
        String sex = element.select("span#lbl_xb").text();
        map.put("sex",sex);
        String zy = element.select("span#lbl_zymc").text();
        map.put("zy",zy);
        String stdnum = element.select("span#lbl_ksh").text();
        map.put("stdnum",stdnum);
        String zzmm = element.select("span#lbl_zzmm").text();
        map.put("zzmm",zzmm);
        String highSchool = element.select("span#lbl_byzx").text();
        map.put("highSchool",highSchool);
        String zkzh = element.select("span#lbl_zkzh").text();
        map.put("zkzh",zkzh);
        String sfz = element.select("span#lbl_sfzh").text();
        map.put("sfz",sfz);
        String phone = element.select("input#TELNUMBER").attr("value");
        map.put("phone",phone);
        return map;
    }
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
    public static List<Map<String,String>> getClassTable(String string){
        Document document = Jsoup.parse(string);
        Element element = document.select("table.datelist").first();
        Elements trs = element.select("tr");
        trs.remove(0);
        String result="";
        List<Map<String,String>> list = new ArrayList<>();
        for (Element tr:trs){
            Elements tds = tr.select("td");
            Map<String,String> map = new HashMap<>();
            map.put("classid",tds.get(1).text());
            map.put("name",tds.get(2).text());
            map.put("type",tds.get(3).text());
            map.put("teacher",tds.get(5).text());
            map.put("credit",tds.get(6).text());
            map.put("time",tds.get(8).text());
            //map.put("classid",tds.get(6).text());
            /*for (int i=0;i<tds.size();i++){
                temp += tds.get(i).text();
            }*/
            list.add(map);
        }
        return list;
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
    public static String getYearSelected(String string){
        Document document = Jsoup.parse(string);
        Element element =document.select("[name=\"ddlxn\"]").first();
        Element sel = element.select("[selected=\"selected\"]").first();
        return sel.text();
    }
    public static String getTermSelected(String string){
        Document document = Jsoup.parse(string);
        Element element =document.select("[name=\"ddlxq\"]").first();
        Element sel = element.select("[selected=\"selected\"]").first();
        return sel.text();
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
                //Log.i("html",td.html());
                if (td.html().equals("&nbsp;"))
                    tds[i] = "";
                else
                    tds[i] = td.text();
                i++;
            }
            ClassInfo classInfo = new ClassInfo(tds);
            list.add(classInfo);
        }
        return list;
    }
}
