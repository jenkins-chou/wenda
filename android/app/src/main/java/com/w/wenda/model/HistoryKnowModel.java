package com.w.wenda.model;

public class HistoryKnowModel extends BaseKnowModel {
    public HistoryKnowModel(){
        this.type = BaseKnowModel.history;
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
