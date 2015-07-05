package com.rdc.ruan.zzia.Main.Utils;

/**
 * Created by Administrator on 2015/6/15.
 */
public class ClassInfo {
    private String year;
    private String term;
    private String classID;
    private String className;
    private String classType;
    private String classOf;
    private String classCredit;
    private String regularScore;
    private String midScore;
    private String finalScore;
    private String experScore;
    private String totalScore;
    private String makeupScore;
    private String isRestudy;
    private String collage;
    private String notes;
    private String makeupNotes;

    public ClassInfo(String[] strings){
        int i = 0;
        year = strings[i++];
        term = strings[i++];
        classID = strings[i++];
        className = strings[i++];
        classType = strings[i++];
        classOf = strings[i++];
        classCredit = strings[i++];
        regularScore = strings[i++];
        midScore = strings[i++];
        finalScore = strings[i++];
        experScore = strings[i++];
        totalScore = strings[i++];
        makeupScore = strings[i++];
        isRestudy = strings[i++];
        collage = strings[i++];
        notes = strings[i++];
        makeupNotes = strings[i++];
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
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

    public String getRegularScore() {
        return regularScore;
    }

    public void setRegularScore(String regularScore) {
        this.regularScore = regularScore;
    }

    public String getMidScore() {
        return midScore;
    }

    public void setMidScore(String midScore) {
        this.midScore = midScore;
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

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getMakeupScore() {
        return makeupScore;
    }

    public void setMakeupScore(String makeupScore) {
        this.makeupScore = makeupScore;
    }

    public String getIsRestudy() {
        return isRestudy;
    }

    public void setIsRestudy(String isRestudy) {
        this.isRestudy = isRestudy;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMakeupNotes() {
        return makeupNotes;
    }

    public void setMakeupNotes(String makeupNotes) {
        this.makeupNotes = makeupNotes;
    }

    public void setClassInfo(String[] strings){
        int i = -1;
        year = strings[i++];
        term = strings[i++];
        classID = strings[i++];
        className = strings[i++];
        classType = strings[i++];
        classOf = strings[i++];
        classCredit = strings[i++];
        regularScore = strings[i++];
        midScore = strings[i++];
        finalScore = strings[i++];
        experScore = strings[i++];
        totalScore = strings[i++];
        makeupScore = strings[i++];
        isRestudy = strings[i++];
        collage = strings[i++];
        notes = strings[i++];
        makeupNotes = strings[i++];
    }

    @Override
    public String toString() {
        return year+" "+
        term+" "+
        classID+" "+
        className+" "+
        classType+" "+
        classOf+" "+
        classCredit+" "+
        regularScore+" "+
                midScore+" "+
        finalScore+" "+
        experScore+" "+
        totalScore+" "+
        makeupScore+" "+
        isRestudy+" "+
        collage+" "+
        notes+" "+
        makeupNotes;
    }
}
