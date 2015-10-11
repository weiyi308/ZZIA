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

    public static final String SCORE_VIEWSTATE = "dDw0MjY4MTY5NTE7dDxwPGw8dGpxcjs+O2w8MTs+PjtsPGk8MT47PjtsPHQ8O2w8aTwxPjtpPDc+O2k8OT47PjtsPHQ8dDw7dDxpPDE2PjtAPOWFqOmDqDsyMDAxLTIwMDI7MjAwMi0yMDAzOzIwMDMtMjAwNDsyMDA0LTIwMDU7MjAwNS0yMDA2OzIwMDYtMjAwNzsyMDA3LTIwMDg7MjAwOC0yMDA5OzIwMDktMjAxMDsyMDEwLTIwMTE7MjAxMS0yMDEyOzIwMTItMjAxMzsyMDEzLTIwMTQ7MjAxNC0yMDE1OzIwMTUtMjAxNjs+O0A85YWo6YOoOzIwMDEtMjAwMjsyMDAyLTIwMDM7MjAwMy0yMDA0OzIwMDQtMjAwNTsyMDA1LTIwMDY7MjAwNi0yMDA3OzIwMDctMjAwODsyMDA4LTIwMDk7MjAwOS0yMDEwOzIwMTAtMjAxMTsyMDExLTIwMTI7MjAxMi0yMDEzOzIwMTMtMjAxNDsyMDE0LTIwMTU7MjAxNS0yMDE2Oz4+Oz47Oz47dDw7bDxpPDA+O2k8MT47aTwyPjs+O2w8dDw7bDxpPDA+Oz47bDx0PHA8bDxpbm5lcmh0bWw7PjtsPDIwMTQtMjAxNeWtpuW5tOesrDHlrabmnJ/lrabkuaDmiJDnu6k7Pj47Oz47Pj47dDw7bDxpPDA+O2k8MT47aTwyPjs+O2w8dDxwPGw8aW5uZXJodG1sOz47bDzlrablj7fvvJoxMjEwMDYyMjI7Pj47Oz47dDxwPGw8aW5uZXJodG1sOz47bDzlp5PlkI3vvJrpmK7kuJzlt507Pj47Oz47dDxwPGw8aW5uZXJodG1sOz47bDzlrabpmaLvvJrorqHnrpfmnLrnp5HlrabkuI7lupTnlKjns7s7Pj47Oz47Pj47dDw7bDxpPDA+O2k8MT47PjtsPHQ8cDxsPGlubmVyaHRtbDs+O2w85LiT5Lia77ya6K6h566X5py656eR5a2m5LiO5oqA5pyvOz4+Ozs+O3Q8cDxsPGlubmVyaHRtbDs+O2w86KGM5pS/54+t77yaMTIxMDA2Mjs+Pjs7Pjs+Pjs+Pjt0PEAwPHA8cDxsPFBhZ2VDb3VudDtfIUl0ZW1Db3VudDtfIURhdGFTb3VyY2VJdGVtQ291bnQ7RGF0YUtleXM7PjtsPGk8MT47aTw2PjtpPDY+O2w8Pjs+Pjs+Ozs7Ozs7Ozs7Oz47bDxpPDA+Oz47bDx0PDtsPGk8MT47aTwyPjtpPDM+O2k8ND47aTw1PjtpPDY+Oz47bDx0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0PjtpPDU+O2k8Nj47aTw3PjtpPDg+O2k8OT47aTwxMD47aTwxMT47aTwxMj47aTwxMz47aTwxND47aTwxNT47aTwxNj47aTwxNz47aTwxOD47aTwxOT47aTwyMD47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8KDIwMTQtMjAxNS0xKS01MTAwOTktMDAyNDgxLTE7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIwMTQtMjAxNTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8NTEwMDk5Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlpKflnovmlbDmja7lupPorr7orqE7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOW/heS/ruivvjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8My41Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDw4Mzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Nzg7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDgwOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzorqHnrpfmnLrnp5HlrabkuI7lupTnlKjns7s7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjs+Pjt0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0PjtpPDU+O2k8Nj47aTw3PjtpPDg+O2k8OT47aTwxMD47aTwxMT47aTwxMj47aTwxMz47aTwxND47aTwxNT47aTwxNj47aTwxNz47aTwxOD47aTwxOT47aTwyMD47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8KDIwMTQtMjAxNS0xKS01MTAwMDYtMDAyODI4LTE7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIwMTQtMjAxNTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8NTEwMDA2Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzorqHnrpfmnLrnu4TmiJDljp/nkIY7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOW/heS/ruivvjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8NC41Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDw4MDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8NTc7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDkwOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDw2Njs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86K6h566X5py656eR5a2m5LiO5bqU55So57O7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47Pj47dDw7bDxpPDA+O2k8MT47aTwyPjtpPDM+O2k8ND47aTw1PjtpPDY+O2k8Nz47aTw4PjtpPDk+O2k8MTA+O2k8MTE+O2k8MTI+O2k8MTM+O2k8MTQ+O2k8MTU+O2k8MTY+O2k8MTc+O2k8MTg+O2k8MTk+O2k8MjA+Oz47bDx0PHA8cDxsPFRleHQ7PjtsPCgyMDE0LTIwMTUtMSktNTEwMDc1LTAwMjQ4MS0xOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwyMDE0LTIwMTU7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDE7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDUxMDA3NTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8572R56uZ6KeE5YiS5LiO6K6+6K6hOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlv4Xkv67or747Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDQuMDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86Imv5aW9Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzorqHnrpfmnLrnp5HlrabkuI7lupTnlKjns7s7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjs+Pjt0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0PjtpPDU+O2k8Nj47aTw3PjtpPDg+O2k8OT47aTwxMD47aTwxMT47aTwxMj47aTwxMz47aTwxND47aTwxNT47aTwxNj47aTwxNz47aTwxOD47aTwxOT47aTwyMD47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8KDIwMTQtMjAxNS0xKS01MTAyMzMtMDAyNDgxLTE7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIwMTQtMjAxNTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8NTEwMjMzOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlpKflnovmlbDmja7lupPor77nqIvorr7orqE7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWunui3teivvjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MS4wOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzkvJjnp4A7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOiuoeeul+acuuenkeWtpuS4juW6lOeUqOezuzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+Oz4+O3Q8O2w8aTwwPjtpPDE+O2k8Mj47aTwzPjtpPDQ+O2k8NT47aTw2PjtpPDc+O2k8OD47aTw5PjtpPDEwPjtpPDExPjtpPDEyPjtpPDEzPjtpPDE0PjtpPDE1PjtpPDE2PjtpPDE3PjtpPDE4PjtpPDE5PjtpPDIwPjs+O2w8dDxwPHA8bDxUZXh0Oz47bDwoMjAxNC0yMDE1LTEpLTUxMDEwMi0wMDI4NjQtMTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MjAxNC0yMDE1Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwxOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDw1MTAxMDI7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPEoyTUXlupTnlKjlvIDlj5E7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOmAieS/ruivvjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8NC41Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzkvJjnp4A7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOS8mOengDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85LyY56eAOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzorqHnrpfmnLrnp5HlrabkuI7lupTnlKjns7s7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjs+Pjt0PDtsPGk8MD47aTwxPjtpPDI+O2k8Mz47aTw0PjtpPDU+O2k8Nj47aTw3PjtpPDg+O2k8OT47aTwxMD47aTwxMT47aTwxMj47aTwxMz47aTwxND47aTwxNT47aTwxNj47aTwxNz47aTwxOD47aTwxOT47aTwyMD47PjtsPHQ8cDxwPGw8VGV4dDs+O2w8KDIwMTQtMjAxNS0xKS01MTAwMTAtMDAyNzc0LTE7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDIwMTQtMjAxNTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8NTEwMDEwOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzkv6Hlj7flpITnkIbmpoLorro7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOmAieS/ruivvjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8My41Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDw4MDs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8Jm5ic3BcOzs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8NDI7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPDUzOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwmbmJzcFw7Oz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzorqHnrpfmnLrnp5HlrabkuI7lupTnlKjns7s7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPCZuYnNwXDs7Pj47Pjs7Pjs+Pjs+Pjs+Pjs+Pjs+Pjs+UFWMPBbgAfciEQmYq48MXYmDh2Q=";

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
    public static String getScoreUrl(){
        return HttpUtil.GetCookieUrl(url) + "xscjcx_dq.aspx?xh="+userid+"&xm="+
                name+"&gnmkdm=N121618";
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
