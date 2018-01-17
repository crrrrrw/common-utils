package com.crw.common.utils.date;

public enum DatePattern {

    //-------------------------------------------------------------------------------------------------------------------------------- Normal
    YYYY_MM("yyyy-MM", false),
    YYYY_MM_DD("yyyy-MM-dd", false),
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm", false),
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss", false),

    YYYY_MM_EN("yyyy/MM", false),
    YYYY_MM_DD_EN("yyyy/MM/dd", false),
    YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm", false),
    YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss", false),

    YYYY_MM_CN("yyyy年MM月", false),
    YYYY_MM_DD_CN("yyyy年MM月dd日", false),
    YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm", false),
    YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss", false),

    HH_MM("HH:mm", true),
    HH_MM_SS("HH:mm:ss", true),

    MM_DD("MM-dd", true),
    MM_DD_HH_MM("MM-dd HH:mm", true),
    MM_DD_HH_MM_SS("MM-dd HH:mm:ss", true),

    MM_DD_EN("MM/dd", true),
    MM_DD_HH_MM_EN("MM/dd HH:mm", true),
    MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss", true),

    MM_DD_CN("MM月dd日", true),
    MM_DD_HH_MM_CN("MM月dd日 HH:mm", true),
    MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss", true),

    //-------------------------------------------------------------------------------------------------------------------------------- Pure

    /**
     * 标准日期格式：yyyyMMdd
     */
    PURE_DATE_PATTERN("yyyyMMdd", false),

    /**
     * 标准日期格式：yyyyMMddHHmmss
     */
    PURE_DATETIME_PATTERN("yyyyMMddHHmmss", false),

    /**
     * 标准日期格式：yyyyMMddHHmmssSSS
     */
    PURE_DATETIME_MS_PATTERN("yyyyMMddHHmmssSSS", false),
    /**
     * 标准日期格式：HHmmss
     */
    PURE_TIME_PATTERN("HHmmss", true),

    //-------------------------------------------------------------------------------------------------------------------------------- Others
    /**
     * HTTP头中日期时间格式：EEE, dd MMM yyyy HH:mm:ss z
     */
    HTTP_DATETIME_PATTERN("EEE, dd MMM yyyy HH:mm:ss z", false),

    /**
     * HTTP头中日期时间格式：EEE MMM dd HH:mm:ss zzz yyyy
     */
    JDK_DATETIME_PATTERN("EEE MMM dd HH:mm:ss zzz yyyy", false);

    private String value;

    private boolean isShowOnly;

    DatePattern(String value, boolean isShowOnly) {
        this.value = value;
        this.isShowOnly = isShowOnly;
    }

    public String getValue() {
        return value;
    }

    public boolean isShowOnly() {
        return isShowOnly;
    }
}
