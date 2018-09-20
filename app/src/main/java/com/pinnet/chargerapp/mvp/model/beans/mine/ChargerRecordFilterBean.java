package com.pinnet.chargerapp.mvp.model.beans.mine;

/**
 * @author P00558
 * @date 2018/5/29
 */

public class ChargerRecordFilterBean {
    private String filterName;
    private String filterId;
    private boolean isChecked;

    public ChargerRecordFilterBean(String id, String name) {
        this(id, name, false);
    }

    public ChargerRecordFilterBean(String id, String name, boolean isChecked) {
        this.filterId = id;
        this.filterName = name;
        this.isChecked = isChecked;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
