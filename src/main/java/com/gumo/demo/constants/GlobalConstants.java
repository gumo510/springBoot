package com.gumo.demo.constants;

/**
 * @author hy
 * @since 2022-02-21 10:56
 */

public class GlobalConstants {

    /**
     * 线程数
     */
    public static final Integer THREAD_BLOCKING_QUEUE = 100;

    /**
     * 推送数据catalogId
     */
    public static String CATALOG_ID_9 = "WEB9";
    public static String CATALOG_ID_10 = "WEB10";

    public static final String PASS_RECORD_PERSON_SELECT_URI = "/capture/person/records";

    public static final String PASS_RECORD_STRANGER_SELECT_URI = "/capture/stranger/records";

    public static final String PASS_RECORD_DELETE_URI = "/capture/person/delete";


    /*************************************  分表前缀 start  *****************************************/
    public static final String T_NAME_EVENT = "t_event";
    public static final String T_NAME_EVENT_IMAGE = "t_event_image";
    public static final String T_NAME_FCB_IMAGE = "t_image";
    public static final String T_NAME_FACE = "t_face";
    public static final String T_NAME_CAR = "t_car";
    public static final String T_NAME_BIKE = "t_bike";
    public static final String T_BIG_IMAGE = "big_image";
    public static final String T_SMALL_IMAGE = "small_image";
}
