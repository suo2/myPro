package com.pinnet.chargerapp.mvp.model.beans.charger;

/**
 * @author P00558
 * @date 2018/4/27
 */

public class ChargerMathodOption implements Cloneable {
    String showName;
    String value;
    String title;
    boolean checked;

    public ChargerMathodOption(String title, String name, String value) {
        this(title, name, value, false);
    }

    public ChargerMathodOption(String title, String name, String value, boolean isChecked) {
        super();
        this.showName = name;
        this.value = value;
        this.title = title;
        this.checked = isChecked;
    }

    public Object clone() {
        ChargerMathodOption o = null;
        try {
            o = (ChargerMathodOption) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
