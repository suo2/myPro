package com.pinnet.chargerapp.dagger.component;

import com.pinnet.chargerapp.dagger.module.ActivityModule;
import com.pinnet.chargerapp.dagger.scope.ActivityScope;
import com.pinnet.chargerapp.ui.charger.ChargerChargingActivity;
import com.pinnet.chargerapp.ui.charger.ChargerMethodActivity;
import com.pinnet.chargerapp.ui.charger.ChargerOrderDetailActivity;
import com.pinnet.chargerapp.ui.charger.ChargerOrderPayActivity;
import com.pinnet.chargerapp.ui.charger.ChargerChargingAutoFillActivity;
import com.pinnet.chargerapp.ui.charger.ChargerStationDetailActivity;
import com.pinnet.chargerapp.ui.charger.ChargerStationListActivity;
import com.pinnet.chargerapp.ui.charger.RouteHistoryActivity;
import com.pinnet.chargerapp.ui.charger.RoutePlanActivity;
import com.pinnet.chargerapp.ui.login.LoginActivity;
import com.pinnet.chargerapp.ui.login.RegisterActivity;
import com.pinnet.chargerapp.ui.login.ResetPasswordActivity;
import com.pinnet.chargerapp.ui.main.MainActivity;
import com.pinnet.chargerapp.ui.mine.ChargingRecordActivity;
import com.pinnet.chargerapp.ui.mine.PersonalInfoActivity;
import com.pinnet.chargerapp.ui.mine.PersonalInfoModifyActivity;
import com.pinnet.chargerapp.ui.mine.SettingActivity;

import dagger.Component;

/**
 * @author P00558
 * @date 2018/4/2
 */
@ActivityScope
@Component(dependencies = ChargerAppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);

    void inject(ResetPasswordActivity resetPasswordActivity);

    void inject(ChargingRecordActivity chargingRecordActivity);

    void inject(PersonalInfoActivity activity);

    void inject(PersonalInfoModifyActivity activity);

    void inject(ChargerOrderPayActivity activity);

    void inject(SettingActivity activity);

    void inject(ChargerMethodActivity activity);

    void inject(ChargerChargingActivity activity);

    void inject(ChargerChargingAutoFillActivity activity);

    void inject(ChargerOrderDetailActivity activity);

    void inject(RoutePlanActivity activity);

    void inject(RouteHistoryActivity activity);

    void inject(ChargerStationListActivity activity);

    void inject(ChargerStationDetailActivity activity);
}
