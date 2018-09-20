package com.pinnet.chargerapp.third.citypicker.adapter;


import com.pinnet.chargerapp.third.citypicker.model.City;

public interface InnerListener {
    void dismiss(int position, City data);
    void locate();
}
