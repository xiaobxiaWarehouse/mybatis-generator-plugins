package com.codi.base.mybatis.generator.plugins.util;

import org.mybatis.generator.api.dom.java.JavaElement;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类注释util
 *
 * @author shi.pengyan
 * @date 2016-12-21 20:26
 */
public final class CommentUtil {

    /**
     * 生成类注释
     *
     * @param javaElement
     */
    public static void makeClassComment(JavaElement javaElement) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        javaElement.addJavaDocLine("/**");
        javaElement.addJavaDocLine(" * 亲，写个类注释呗");
        javaElement.addJavaDocLine(" * @author " + OsUtil.getUserName());
        javaElement.addJavaDocLine(" * @date " + sdf.format(new Date()));
        javaElement.addJavaDocLine(" */");
    }
}
