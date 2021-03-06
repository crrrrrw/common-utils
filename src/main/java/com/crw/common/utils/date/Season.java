package com.crw.common.utils.date;

/**
 * 季度枚举<br>
 *
 * @see #SPRING
 * @see #SUMMER
 * @see #AUTUMN
 * @see #WINTER
 */
public enum Season {

    /**
     * 春季（第一季度）
     */
    SPRING(1),
    /**
     * 夏季（第二季度）
     */
    SUMMER(2),
    /**
     * 秋季（第三季度）
     */
    AUTUMN(3),
    /**
     * 冬季（第四季度）
     */
    WINTER(4);

    // ---------------------------------------------------------------
    private int value;

    private Season(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    /**
     * 将 季度int转换为Season枚举对象<br>
     *
     * @param intValue 季度int表示
     * @return {@link Season}
     * @see #SPRING
     * @see #SUMMER
     * @see #AUTUMN
     * @see #WINTER
     */
    public static Season of(int intValue) {
        switch (intValue) {
            case 1:
                return SPRING;
            case 2:
                return SUMMER;
            case 3:
                return AUTUMN;
            case 4:
                return WINTER;
            default:
                return null;
        }
    }

    /**
     * 转换为中文名
     *
     * @return 季节的中文名
     */
    public String toChinese() {
        switch (this) {
            case SPRING:
                return "春";
            case SUMMER:
                return "夏";
            case AUTUMN:
                return "秋";
            case WINTER:
                return "冬";
            default:
                return null;
        }
    }
}
