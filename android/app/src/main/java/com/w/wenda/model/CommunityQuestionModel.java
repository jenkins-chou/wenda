package com.w.wenda.model;

public class CommunityQuestionModel {
    private int id;
    private String title;
    private String detail;
    private String creator_id;
    private String creator_name;
    private String creator_avatar;
    private String useful;
    private String useless;
    private String remark;
    private String create_time;
    private String del;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getUseful() {
        return useful;
    }

    public void setUseful(String useful) {
        this.useful = useful;
    }

    public String getUseless() {
        return useless;
    }

    public void setUseless(String useless) {
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
        return "CommunityQuestionModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", creator_id='" + creator_id + '\'' +
                ", creator_name='" + creator_name + '\'' +
                ", creator_avatar='" + creator_avatar + '\'' +
                ", useful='" + useful + '\'' +
                ", useless='" + useless + '\'' +
                ", remark='" + remark + '\'' +
                ", create_time='" + create_time + '\'' +
                ", del='" + del + '\'' +
                '}';
    }
}
