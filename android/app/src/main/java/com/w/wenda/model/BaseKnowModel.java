package com.w.wenda.model;

public class BaseKnowModel {
    public int type = 1;
    public static final int comprehensive = 1;
    public static final int humanity = 2;
    public static final int society = 3;
    public static final int history = 4;
    public static final int natural = 5;

    public String id;
    public String key;
    public String answer;
    public String probably_answer;
    public String label;
    public String first_classification;
    public String second_classification;
    public String remark;
    public String create_time;
    public String del;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static int getComprehensive() {
        return comprehensive;
    }

    public static int getHumanity() {
        return humanity;
    }

    public static int getSociety() {
        return society;
    }

    public static int getHistory() {
        return history;
    }

    public static int getNatural() {
        return natural;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getProbably_answer() {
        return probably_answer;
    }

    public void setProbably_answer(String probably_answer) {
        this.probably_answer = probably_answer;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFirst_classification() {
        return first_classification;
    }

    public void setFirst_classification(String first_classification) {
        this.first_classification = first_classification;
    }

    public String getSecond_classification() {
        return second_classification;
    }

    public void setSecond_classification(String second_classification) {
        this.second_classification = second_classification;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    @Override
    public String toString() {
        return "BaseKnowModel{" +
                "type=" + type +
                ", id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", answer='" + answer + '\'' +
                ", probably_answer='" + probably_answer + '\'' +
                ", label='" + label + '\'' +
                ", first_classification='" + first_classification + '\'' +
                ", second_classification='" + second_classification + '\'' +
                ", remark='" + remark + '\'' +
                ", create_time='" + create_time + '\'' +
                ", del='" + del + '\'' +
                '}';
    }
}
