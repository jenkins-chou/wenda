package com.w.wenda.model;

public class PreferenceModel {
    private String preference_name;
    private String level;
    private String label;
    private String type;
    private String key;
    private String remark;
    private String create_time;
    private String del;

    public String getPreference_name() {
        return preference_name;
    }

    public void setPreference_name(String preference_name) {
        this.preference_name = preference_name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
        return "PreferenceModel{" +
                "preference_name='" + preference_name + '\'' +
                ", level='" + level + '\'' +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", key='" + key + '\'' +
                ", remark='" + remark + '\'' +
                ", create_time='" + create_time + '\'' +
                ", del='" + del + '\'' +
                '}';
    }
}
