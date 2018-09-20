package com.pinnet.chargerapp.dagger.component;

import com.pinnet.chargerapp.dagger.module.FragmentModule;
import com.pinnet.chargerapp.dagger.scope.FragmentScope;
import com.pinnet.chargerapp.ui.charger.ChargerCommentFragment;
import com.pinnet.chargerapp.ui.charger.ChargerHomeFragment;
import com.pinnet.chargerapp.ui.charger.ChargerStationDetailFragment;
import com.pinnet.chargerapp.ui.charger.ChargerStatusFragment;
import com.pinnet.chargerapp.ui.login.ResetPasswordConfirmFragment;
import com.pinnet.chargerapp.ui.login.ResetPasswordSendCodeFragment;
import com.pinnet.chargerapp.ui.mine.MineHomeFragment;

import dagger.Component;

/**
 * @author P00558
 * @date 2018/4/19
 */
@FragmentScope
@Component(dependencies = ChargerAppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(ResetPasswordSendCodeFragment fragment);

    void inject(ResetPasswordConfirmFragment fragment);

    void inject(ChargerHomeFragment fragment);

    void inject(ChargerStationDetailFragment fragment);

    void inject(ChargerStatusFragment fragment);

    void inject(MineHomeFragment fragment);

    void inject(ChargerCommentFragment fragment);

}
