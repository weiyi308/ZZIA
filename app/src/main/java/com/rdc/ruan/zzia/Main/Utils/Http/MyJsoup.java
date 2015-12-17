package com.rdc.ruan.zzia.Main.Utils.Http;


import com.rdc.ruan.zzia.Main.Utils.ClassInfo;

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
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        Document document = Jsoup.parse(str);
        Element element = document.select("table.datelist").first();
        Elements trs = element.select("tr");
        trs.remove(0);
        if (trs.isEmpty()){
            return list;
        }
        for (Element tr:trs){
            Map<String,String> map = new HashMap<String, String>();
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
        Map<String,String> map = new HashMap<String, String>();
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
    private static String userid;
    private static String name;
    private static String url;

    public static final String SCORE_VIEWSTATE = "dDw0MTg3MjExMDA7dDw7bDxpPDE+Oz47bDx0PDtsPGk8MT47aTwxNT47aTwxNz47aTwyMz47aTwyNT47aTwyNz47aTwyOT47aTwzMD47aTwzMj47aTwzND47aTwzNj47aTw0OD47aTw1Mj47PjtsPHQ8dDw7dDxpPDE2PjtAPFxlOzIwMDEtMjAwMjsyMDAyLTIwMDM7MjAwMy0yMDA0OzIwMDQtMjAwNTsyMDA1LTIwMDY7MjAwNi0yMDA3OzIwMDctMjAwODsyMDA4LTIwMDk7MjAwOS0yMDEwOzIwMTAtMjAxMTsyMDExLTIwMTI7MjAxMi0yMDEzOzIwMTMtMjAxNDsyMDE0LTIwMTU7MjAxNS0yMDE2Oz47QDxcZTsyMDAxLTIwMDI7MjAwMi0yMDAzOzIwMDMtMjAwNDsyMDA0LTIwMDU7MjAwNS0yMDA2OzIwMDYtMjAwNzsyMDA3LTIwMDg7MjAwOC0yMDA5OzIwMDktMjAxMDsyMDEwLTIwMTE7MjAxMS0yMDEyOzIwMTItMjAxMzsyMDEzLTIwMTQ7MjAxNC0yMDE1OzIwMTUtMjAxNjs+Pjs+Ozs+O3Q8cDw7cDxsPG9uY2xpY2s7PjtsPHByZXZpZXcoKVw7Oz4+Pjs7Pjt0PHA8O3A8bDxvbmNsaWNrOz47bDx3aW5kb3cuY2xvc2UoKVw7Oz4+Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWtpuWPt++8mjEyMTAwNjIyMjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85aeT5ZCN77ya6Ziu5Lic5bedOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlrabpmaLvvJrorqHnrpfmnLrnp5HlrabkuI7lupTnlKjns7s7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOS4k+S4mu+8mjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86K6h566X5py656eR5a2m5LiO5oqA5pyvOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzooYzmlL/nj63vvJoxMjEwMDYyOz4+Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8WlpJQVNUOz4+Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjs+Pjs+Pjs+7jdVhImSH7ZUwxnw0VEUXVv8wH8=";

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        MyJsoup.url = url;
    }

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String userid) {
        MyJsoup.userid = userid;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        MyJsoup.name = name;
    }

    public static String getMainUrl(){
        return HttpUtil.GetCookieUrl(url) + "xs_main.aspx?xh=" + userid;
    }
    public static String getDetailUrl(){
        return HttpUtil.GetCookieUrl(url) + "xsgrxx.aspx?xh="+userid+"&xm="+
                name+"&gnmkdm=N121618";
    }
    /*public static String getScoreUrl(){
        return HttpUtil.GetCookieUrl(url) + "xscjcx_dq.aspx?xh="+userid+"&xm="+
                name+"&gnmkdm=N121618";
    }*/
    public static String getScoreUrl(){
        return HttpUtil.GetCookieUrl(url) + "xscj.aspx?xh="+userid+"&xm="+
                name+"&gnmkdm=N121617";
    }
    public static String getCetUrl(){
        return HttpUtil.GetCookieUrl(url) + "xsdjkscx.aspx?xh="+userid+"&xm="+
                name+"&gnmkdm=N121618";
    }
    public static String getClassTableUrl(){
        return HttpUtil.GetCookieUrl(url) + "xsxkqk.aspx?xh="+
                userid+"&xm="+
                name+"&gnmkdm=N121616";
    }
    public static List<Map<String,String>> getClassTable(String string){
        Document document = Jsoup.parse(string);
        Element element = document.select("table.datelist").first();
        Elements trs = element.select("tr");
        trs.remove(0);
        String result="";
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        for (Element tr:trs){
            Elements tds = tr.select("td");
            Map<String,String> map = new HashMap<String, String>();
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
        return element == null?"" :element.text();
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
        Element table = document.select("table.datelist").first();
        Elements trs = table.select("tr");
        List<ClassInfo> list = new ArrayList<ClassInfo>();
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
