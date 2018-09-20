package com.pinnet.chargerapp.ui;

import android.app.Activity;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

/**
 * @author P00558
 * @date 2018/4/21
 */

public class CustomCaptureManager extends CaptureManager {
    public CustomCaptureManager(Activity activity, DecoratedBarcodeView barcodeView) {
        super(activity, barcodeView);
    }

}