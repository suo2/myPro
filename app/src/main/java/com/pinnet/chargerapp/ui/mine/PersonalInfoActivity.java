package com.pinnet.chargerapp.ui.mine;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pinnet.chargerapp.R;
import com.pinnet.chargerapp.app.Constants;
import com.pinnet.chargerapp.base.BaseActivity;
import com.pinnet.chargerapp.mvp.contract.mine.PersonalInfoContract;
import com.pinnet.chargerapp.mvp.model.beans.mine.PersonalInfoBean;
import com.pinnet.chargerapp.mvp.presenter.mine.PersonalInfoPresenter;
import com.pinnet.chargerapp.utils.LogUtils;
import com.pinnet.chargerapp.widget.CircleImageView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author P00558
 * @date 2018/4/11
 * 个人信息
 */

public class PersonalInfoActivity extends BaseActivity<PersonalInfoPresenter> implements PersonalInfoContract.View {
    public static final String TAG = PersonalInfoActivity.class.getSimpleName();
    private static final int TAKE_PHOTO = 0x11;
    private static final int CHOOSE_PHOTO = 0x12;
    public static final int MODIFY_NICKNAME = 0x21;
    public static final int MODIFY_ADDRESS = 0x22;
    public static final String MODIFY_TYPE = "modify_type";
    public static final String MODIFY_CONTENT = "modify_content";
    public static final String MODIFY_BEAN = "modify_bean";
    @BindView(R.id.tv_header_title)
    TextView tvHeaderTitle;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_address)
    TextView tvUserAddress;
    @BindView(R.id.iv_user_header)
    CircleImageView ivUserHeader;
    private SelectPicPopupWindow mPopupWindow;
    private Uri mFileUri;
    private String mFilePath;
    private File mFile;
    private PersonalInfoBean personalInfoBean;
    private RxPermissions rxPermissions;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.mine_personal_info_activity;
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        rxPermissions = new RxPermissions(this);
        tvHeaderTitle.setText(R.string.mine_personal_info_title);
        mPopupWindow = new SelectPicPopupWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.bt_pop_camera:  //拍照
                        mPopupWindow.dismiss();
                        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File file = getFile();
                        mFileUri = Uri.fromFile(file);
                        intent1.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                        startActivityForResult(intent1, TAKE_PHOTO);
                        break;

                    case R.id.bt_pop_album:  //从相册中选择照片
                        mPopupWindow.dismiss();
                        Intent intent2 = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent2, CHOOSE_PHOTO);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onQueryPersonalInfo();
    }

    @OnClick({R.id.iv_back, R.id.rl_user_personal_code, R.id.rl_user_name, R.id.rl_user_header, R.id.rl_user_address})
    void onViewClicked(final View view) {
        Intent intent;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.iv_back:
                finishAct();
                break;
            case R.id.rl_user_personal_code:
                break;
            case R.id.rl_user_name:
                intent = new Intent(this, PersonalInfoModifyActivity.class);
                bundle = new Bundle();
                bundle.putInt(MODIFY_TYPE, MODIFY_NICKNAME);
                bundle.putString(MODIFY_CONTENT, getNickName());
                bundle.putSerializable(MODIFY_BEAN, personalInfoBean);
                intent.putExtras(bundle);
                startAct(intent);
                break;
            case R.id.rl_user_address:
                intent = new Intent(this, PersonalInfoModifyActivity.class);
                bundle = new Bundle();
                bundle.putInt(MODIFY_TYPE, MODIFY_ADDRESS);
                bundle.putString(MODIFY_CONTENT, getAddress());
                bundle.putSerializable(MODIFY_BEAN, personalInfoBean);
                intent.putExtras(bundle);
                startAct(intent);
                break;
            case R.id.rl_user_header:
                rxPermissions.requestEach(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(@NonNull Permission permission) throws Exception {
                                if (permission.granted) {
                                    mPopupWindow.showAtLocation(tvHeaderTitle, Gravity.CENTER, 0, 0);
                                }
                            }
                        });

                break;
            default:
                break;
        }

    }

    /**
     * 创建存储拍摄照片的文件
     *
     * @return 创建的文件
     */
    private File getFile() {
        String fileName = "userImage.jpg";
        File dir = getDirFile();
        File file = new File(dir, fileName);
        mFilePath = file.getAbsolutePath();
        return file;
    }

    /**
     * 创建存储照片的父目录
     *
     * @return 创建的目录
     */
    private File getDirFile() {
        File dir = new File(Constants.PATH_PICTURE_USERIMAGE);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    @Override
    public String getAddress() {
        return tvUserAddress.getText().toString();
    }

    @Override
    public String getNickName() {
        return tvUserName.getText().toString();
    }

    @Override
    public void updatePersonalInfo(PersonalInfoBean bean) {
        if (!TextUtils.isEmpty(bean.getNickName())) {
            tvUserName.setText(bean.getNickName());
        }
        if (!TextUtils.isEmpty(bean.getUserAddr())) {
            tvUserAddress.setText(bean.getUserAddr());
        }
        personalInfoBean = bean;
        mPresenter.onDownloadFile(personalInfoBean.getUserAvatarUpdateTIme());
    }

    @Override
    public void onCompressed(String filePath) {
        mPresenter.onUploadImg(filePath);
    }

    @Override
    public void onLoadUserHeader(String path) {
        Glide.with(this).load(path).asBitmap().placeholder(R.drawable.mine_icon_defaule_header).error(R.drawable.mine_icon_defaule_header).into(ivUserHeader);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            //相册选择照片处理
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            if (null != c) {
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                mFilePath = c.getString(columnIndex);
                mPresenter.onCompressFile(mFilePath);
                Toast.makeText(this, "图片压缩中", Toast.LENGTH_SHORT).show();
                c.close();
            } else {
                Log.i(TAG, "onActivityResult: cursor is null");
            }
        } else if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            //相机拍照处理
            mPresenter.onCompressFile(mFilePath);
            Toast.makeText(this, "图片压缩中", Toast.LENGTH_SHORT).show();
        }
    }
}
