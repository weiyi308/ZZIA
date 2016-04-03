package com.rdc.ruan.zzia.Main.Utils;

import android.util.Log;

/**
 * Created by Administrator on 2015/6/15.
 */
public class ClassInfo {
    //private String year;
    //private String term;
    private String classID;
    private String className;
    private String classType;
    private String classOf;
    private String classCredit;
    private String score;
    //private String midScore;
    private String finalScore;
    private String bukaoScore;//补考成绩
    //private String totalScore;
    private String chongxiuScore;//重修成绩
    //private String isRestudy;
    //private String collage;
    //private String notes;
    private String makeupNotes;

    public ClassInfo(String[] strings){
        int i = 0;
        /*year = strings[i++];
        term = strings[i++];*/
        classID = strings[i++];

        className = strings[i++];
        classType = strings[i++];
        finalScore = strings[i++];
        score = strings[i++];
        classOf = strings[i++];
        bukaoScore = strings[i++];
        Log.i("classID", bukaoScore);
        chongxiuScore = strings[i++];
        classCredit = strings[i++];
        makeupNotes = strings[i++];
        /*midScore = strings[i++];


        totalScore = strings[i++];

        isRestudy = strings[i++];
        collage = strings[i++];
        notes = strings[i++];
        */
    }



    public void setClassInfo(String[] strings){
        int i = -1;
        classID = strings[i++];
        className = strings[i++];
        classType = strings[i++];
        finalScore = strings[i++];
        score = strings[i++];
        classOf = strings[i++];
        bukaoScore = strings[i++];
        chongxiuScore = strings[i++];
        classCredit = strings[i++];
        makeupNotes = strings[i++];
        /*//year = strings[i++];
        //term = strings[i++];
        classID = strings[i++];
        className = strings[i++];
        classType = strings[i++];
        classOf = strings[i++];
        classCredit = strings[i++];
        score = strings[i++];
        //midScore = strings[i++];
        finalScore = strings[i++];
        bukaoScore = strings[i++];
        //totalScore = strings[i++];
        chongxiuScore = strings[i++];
        //isRestudy = strings[i++];
        //collage = strings[i++];
        //notes = strings[i++];
        makeupNotes = strings[i++];*/
    }

    @Override
    public String toString() {
        return " "+
        //term+" "+
        classID+" "+
        className+" "+
        classType+" "+
        classOf+" "+
        classCredit+" "+
                score +" "+
                //midScore+" "+
        finalScore+" "+
                bukaoScore +" "+
        //totalScore+" "+
                chongxiuScore +" "+
        //isRestudy+" "+
        //collage+" "+
        //notes+" "+
        makeupNotes;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassOf() {
        return classOf;
    }

    public void setClassOf(String classOf) {
        this.classOf = classOf;
    }

    public String getClassCredit() {
        return classCredit;
    }

    public void setClassCredit(String classCredit) {
        this.classCredit = classCredit;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(String finalScore) {
        this.finalScore = finalScore;
    }

    public String getBukaoScore() {
        return bukaoScore;
    }

    public void setBukaoScore(String bukaoScore) {
        this.bukaoScore = bukaoScore;
    }

    public String getChongxiuScore() {
        return chongxiuScore;
    }

    public void setChongxiuScore(String chongxiuScore) {
        this.chongxiuScore = chongxiuScore;
    }

    public String getMakeupNotes() {
        return makeupNotes;
    }

    public void setMakeupNotes(String makeupNotes) {
        this.makeupNotes = makeupNotes;
    }
}
