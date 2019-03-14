package com.w.wenda.model;

public class ComprehensiveKnowModel extends BaseKnowModel {
    public ComprehensiveKnowModel(){
        this.type = BaseKnowModel.comprehensive;
    }
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ComprehensiveKnowModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
