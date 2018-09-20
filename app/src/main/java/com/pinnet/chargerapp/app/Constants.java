package com.pinnet.chargerapp.app;

import android.os.Environment;

import java.io.File;

/**
 * @author P00558
 * @date 2018/4/12
 */

public class Constants {
    //================= HTTP ====================
    public static final String HTTP_HTTP = "http://182.150.21.253:9998/";
    //http://182.150.21.253:9998/
    public static final String HTTP_HTTPS = "https://10.10.11.77:8080/";
    /**
     * Cookie关键字
     */
    public static final String COOKIE = "Cookie";
    /**
     * 登陆类型
     */
    public static final String LOGIN_TYPE = "loginType";
    /**
     * 语言类型
     */
    public static final String LANGUAGE_CODE = "language_code";
    /**
     * 语言类型
     */
    public static final String PREFER_LANG = "Prefer_Lang";
    /**
     * 客户端类型关键字
     */
    public static final String CLIENT_SOURCE = "source";
    /**
     * 发送数据请求内容关键字
     */
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    /**
     * 接收数据请求内容关键字
     */
    public static final String HEADER_ACCEPT = "Accept";
    /**
     * 请求头 用户ID
     */
    public static final String HEADER_USERID = "userid";
    /**
     * 请求头 tokenId
     */
    public static final String HEADER_TOKENID = "tokenId";
    /**
     * 发送数据类型为Json格式
     */
    public static final String AUTH_CONTENT_TYPE = "application/json;charset=UTF-8";
    /**
     * 接收数据格式为Json
     */
    public static final String AUTH_ACCEPT = "application/json";

    /***/
    public static final String EQUAL_MARK = "=";

    //================= Constants data ====================

    public static final String MAIN_CHARGER_SPLIT = "-";
    public static final int ANIMATION_DUREATION = 500;

    //================= PATH ====================


    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ChargerApp";
    public static final String PATH_LOGS = PATH_SDCARD + File.separator + "Logs";
    public static final String PATH_PICTURE = PATH_SDCARD + File.separator + "Picture";
    public static final String PATH_PICTURE_USERIMAGE = PATH_PICTURE + File.separator + "userImage";

    public static final String PATH_DATA = PATH_SDCARD + File.separator + "data";


    public static final String PATH_CACHE = PATH_DATA + File.separator + "NetCache";
    public static final String PATH_CACHE_PICTURE = PATH_CACHE + File.separator + "Picture";

    /**
     * 配置文件名称
     */
    public static final String KEY_PLATFORM_CONFIG = "platform_conf.properties";


    //================= SharePrefUtils ====================
    /**
     * 已登录用户
     */
    public final static String LOGINED_USERNAME = "logined_username";
    public final static String LOGINED_USERID = "logined_id";
    public final static String LOGINED_TOKEN_ID = "tokenId";
    public final static String LOGINED_ACCOUNT = "logined_account";
    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static String APP_ID = "wxce3e5ad15c03e1e6";


    //================= Activity Code ====================
    /**
     * 支付宝支付
     */
    public static final int PAY_ALI_CODE = 0x12;
    /**
     * 微信支付
     */
    public static final int PAY_WEIXIN_CODE = 0x13;
    /**
     * 退出登录
     */
    public static final String MAIN_LOGIN_OUT = "login_out";
    /**
     * 订单详情页，查看类型
     */
    public static final String ORDER_DETAIL_TYPE = "order_detail_type";
    /**
     * 订单详情
     */
    public static final String ORDER_DETAIL_BEAN = "order_detail_bean";
    /**
     * 充电记录
     */
    public static final String CHARGER_RECORD_BEAN = "charger_record_bean";
    /**
     * 充电记录
     */
    public static final String CHARGER_CHARGING_BEAN = "charger_charging_bean";
    /**
     * 用户当前定位
     */
    public static final String CHARGER_USER_LOCATION = "charger_user_location";
    /**
     * 订单详情——支付
     */
    public static final int ORDER_DETAIL_TYPE_PAY = 0x14;
    /**
     * 订单详情——查询
     */
    public static final int ORDER_DETAIL_TYPE_LOOK = 0x15;
    /**
     * 订单详情——未完成支付
     */
    public static final int ORDER_DETAIL_TYPE_UNPAY = 0x16;
    /**
     * 高德APK的包名
     */
    public static final String AMAP_PACKGE_NAME = "com.autonavi.minimap";
    /**
     * 百度地图APP的包名
     */
    public static final String BAIDUMAP_PACKGE_NAME = "com.baidu.BaiduMap";
    /**
     * 纬度
     */
    public static final String AMAP_LATITUDE = "amap_latitude";
    /**
     * 经度
     */
    public static final String AMAP_LONGTITUDE = "amap_longtitude";
    /**
     * 电站Id
     */
    public static final String CHAGER_SID = "charger_sid";
    /**
     * 电桩编号
     */
    public static final String CHARGER_CHARGE_NUMBER = "charge_number";
    /**
     * 枪口编号
     */
    public static final String CHARGER_MUZZLE_NUMBER = "muzzle_number";

    /**
     * 路径 起点
     */
    public static final String CHARGER_ROUTE_START_POINT = "route_start_point";
    /**
     * 路径 终点
     */
    public static final String CHARGER_ROUTE_END_POINT = "route_end_point";
    /**
     * 首页-requestCode
     */
    public static final int CHARGER_HOME_REQUEST_CODE = 10001;
    /**
     * 扫码-resultCode
     */
    public static final int CHARGER_SCAN_RESULT_CODE = 10002;
    /**
     * 电站列表-requestCode
     */
    public static final int CHARGER_STATION_LIST_REQUEST_CODE = 10003;
    /**
     * 电站搜索-requestCode
     */
    public static final int CHARGER_STATION_SEARCH_RESULT_CODE = 10004;
    /**
     * 电站搜索-key
     */
    public static final String CHARGER_STATION_SEARCH_RESULT = "station_search_result";
    /**
     * 扫码-
     */
    public static final String CHARGER_SCAN_RESULT = "zxing_scan_result";
    /**
     * 路径规划-resultCode
     */
    public static final int CHARGER_ROUTE_RESULT_CODE = 11001;
    /**
     * 路径规划-requestCode
     */
    public static final int CHARGER_ROUTE_REQUEST_CODE = 11002;
    /**
     * 路径规划地址类型
     */
    public static final String CHARGER_ROUTE_PLAN_TYPE = "charger_route_plan_type";
    /**
     * 路径规划地址
     */
    public static final String CHARGER_ROUTE_PLAN_ADDRESS_TIP = "charger_route_plan_address_tip";
    /**
     * 起点
     */
    public static final int CHARGER_ROUTE_PLAN_START_POINT = 11;
    /**
     * 终点
     */
    public static final int CHARGER_ROUTE_PLAN_END_POINT = 12;
    /**
     * 家
     */
    public static final int CHARGER_ROUTE_PLAN_HOME_POINT = 13;
    /**
     * 公司
     */
    public static final int CHARGER_ROUTE_PLAN_COMPANY_POINT = 14;

    //================= BroadCast ====================
    /**
     * 推送广播
     */
    public static final String PUSH_RECIVER_ACTION = "com.pinnet.chargerapp.push.RECEIVER";
}
