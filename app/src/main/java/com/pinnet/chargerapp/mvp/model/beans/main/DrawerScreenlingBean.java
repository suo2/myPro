package com.pinnet.chargerapp.mvp.model.beans.main;

import java.util.List;

/**
 * @author P00558
 * @date 2018/5/3
 */

public class DrawerScreenlingBean {
    String itemId;
    String itemName;
    List<ScreenlingItemBean> itemList;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<ScreenlingItemBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ScreenlingItemBean> itemList) {
        this.itemList = itemList;
    }

    public static class ScreenlingItemBean {
        public ScreenlingItemBean() {
            super();
        }

        public ScreenlingItemBean(String itemId, String showName) {
            this(itemId, showName, false);
        }

        public ScreenlingItemBean(String itemId, String showName, boolean isSelected) {
            this.itemId = itemId;
            this.itemName = showName;
            this.isSelected = isSelected;
        }

        String itemName;
        String itemId;
        boolean isSelected;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
