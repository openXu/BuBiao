package com.yaxon.bubiao.restory;

/**
 * Author: openXu
 * Time: 2021/4/30 20:53
 * class: SelectItem
 * Description:
 */
public class SelectItem {

    int id;
    String name;
    boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SelectItem(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
