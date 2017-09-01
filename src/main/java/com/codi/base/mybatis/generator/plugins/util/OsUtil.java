package com.codi.base.mybatis.generator.plugins.util;

/**
 * 模块名
 *
 * @author shi.pengyan
 * @date 2016-12-22 17:50
 */
public final class OsUtil {

    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUserName() {
        String userName = System.getProperty("user.name");
        if (userName == null) {
            userName = "";
        }
        return userName;
    }
}
