package com.w.wenda.model;

public class CommunityAnswerModel {
    private int id;
    private String question_id;
    private String answer;
    private String creator_id;
    private String creator_name;
    private String creator_avatar;
    private int useful;
    private int useless;
    private String remark;
    private String create_time;
    private String del;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getCreator_avatar() {
        return creator_avatar;
    }

    public void setCreator_avatar(String creator_avatar) {
        this.creator_avatar = creator_avatar;
    }

    public int getUseful() {
        return useful;
    }

    public void setUseful(int useful) {
        this.useful = useful;
    }

    public int getUseless() {
        return useless;
    }

    public void setUseless(int useless) {
        this.useless = useless;
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
        return "CommunityAnswerModel{" +
                "id=" + id +
                ", question_id='" + question_id + '\'' +
                ", answer='" + answer + '\'' +
                ", creator_id='" + creator_id + '\'' +
                ", creator_name='" + creator_name + '\'' +
                ", creator_avatar='" + creator_avatar + '\'' +
                ", useful=" + useful +
                ", useless=" + useless +
                ", remark='" + remark + '\'' +
                ", create_time='" + create_time + '\'' +
                ", del='" + del + '\'' +
                '}';
    }
}
