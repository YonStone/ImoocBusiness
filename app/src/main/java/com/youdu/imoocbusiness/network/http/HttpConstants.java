package com.youdu.imoocbusiness.network.http;

/**
 * @author YonStone
 * @date 2019/09/06
 * @desc 所有请求相关地址
 */
public class HttpConstants {
    private static final String ROOT_URL = "http://imooc.com/api";

    /**
     * 请求本地产品列表
     */
    public static String PRODUCT_LIST = ROOT_URL + "/fund/search.php";

    /**
     * 本地产品列表更新时间措请求
     */
    public static String PRODUCT_LATESAT_UPDATE = ROOT_URL + "/fund/upsearch.php";

    /**
     * 登陆接口
     */
    public static String LOGIN = ROOT_URL + "/user/login_phone.php";

    /**
     * 检查更新接口
     */
    public static String CHECK_UPDATE = ROOT_URL + "/config/check_update.json";

    /**
     * 首页产品请求接口
     */
    public static String HOME_RECOMMAND = ROOT_URL + "/product/home_data.json";

    /**
     * 课程详情接口
     */
    public static String COURSE_DETAIL = ROOT_URL + "/product/course_detail.json";
}
