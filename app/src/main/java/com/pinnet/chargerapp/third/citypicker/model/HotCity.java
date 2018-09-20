package com.pinnet.chargerapp.third.citypicker.model;

public class HotCity extends City {
private boolean isChecked;
    public HotCity(String name, String province, String code) {
        super(name, province, "热门城市", code);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
