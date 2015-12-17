package com.rdc.ruan.zzia.Main.Utils;

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
    private String experScore;
    //private String totalScore;
    private String makeupScore;
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
        experScore = strings[i++];
        makeupScore = strings[i++];
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
        experScore = strings[i++];
        makeupScore = strings[i++];
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
        experScore = strings[i++];
        //totalScore = strings[i++];
        makeupScore = strings[i++];
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
        experScore+" "+
        //totalScore+" "+
        makeupScore+" "+
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

    public String getExperScore() {
        return experScore;
    }

    public void setExperScore(String experScore) {
        this.experScore = experScore;
    }

    public String getMakeupScore() {
        return makeupScore;
    }

    public void setMakeupScore(String makeupScore) {
        this.makeupScore = makeupScore;
    }

    public String getMakeupNotes() {
        return makeupNotes;
    }

    public void setMakeupNotes(String makeupNotes) {
        this.makeupNotes = makeupNotes;
    }
}
