package com.pinnet.chargerapp.mvp.presenter.mine;

import android.content.Context;
import android.os.Environment;

import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseRxPresenter;
import com.pinnet.chargerapp.mvp.contract.mine.SettingContract;
import com.pinnet.chargerapp.mvp.model.DataManager;
import com.pinnet.chargerapp.mvp.model.beans.BaseBean;
import com.pinnet.chargerapp.utils.RxUtils;
import com.pinnet.chargerapp.widget.CommonSubscriber;

import java.io.File;
import java.math.BigDecimal;

import javax.inject.Inject;

/**
 * @author P00558
 * @date 2018/4/9
 */

public class SettingPresenter extends BaseRxPresenter<SettingContract.View> implements SettingContract.Presenter {
    private DataManager mDataManager;

    @Inject
    SettingPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void onLoginOut() {
        addSubscribe(mDataManager.fetchLoginOut()
                .compose(RxUtils.<BaseBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<BaseBean>(mView, false) {
                    @Override
                    public void onNext(BaseBean baseBean) {

                    }
                }));
        mDataManager.onLoginOut();
    }

    @Override
    public String getCacheSize(Context context) {

        try {
            return getTotalCacheSize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0KB";
    }

    @Override
    public void clearCache(Context context) {
        clearAllCache(context);
    }

    /**
     * @param context
     * @return
     * @throws Exception 获取当前缓存
     */
    public String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        cacheSize += getFolderSize(new File(Constants.PATH_SDCARD));
        return getFormatSize(cacheSize);
    }

    /**
     * @param context 删除缓存
     */
    public void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
        deleteDir(new File(Constants.PATH_SDCARD));
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            int size = 0;
            if (children != null) {
                size = children.length;
                for (int i = 0; i < size; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }

        }
        if (dir == null) {
            return true;
        } else {

            return dir.delete();
        }
    }

    // 获取文件
    // Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
    // 目录，一般放一些长时间保存的数据
    // Context.getExternalCacheDir() -->
    // SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            int size2 = 0;
            if (fileList != null) {
                size2 = fileList.length;
                for (int i = 0; i < size2; i++) {
                    // 如果下面还有文件
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);
                    } else {
                        size = size + fileList[i].length();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     * 计算缓存的大小
     *
     * @param size
     * @return
     */
    public String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            // return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
