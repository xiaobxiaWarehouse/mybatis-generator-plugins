package com.codi.base.mybatis.generator.plugins.comment;

import com.codi.base.mybatis.generator.plugins.util.CommentUtil;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;

/**
 * 类注释
 *
 * @author shi.pengyan
 * @date 2016-12-21 19:37
 */
public class CustomCommentGenerator extends DefaultCommentGenerator {


    public CustomCommentGenerator() {
        super();
    }


    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        // TODO Auto-generated method stub
        classComment(innerClass);
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        // TODO Auto-generated method stub
        classComment(innerClass);
    }

    private void classComment(InnerClass innerClass) {
        System.out.println("CustomCommonent innerClass" + innerClass);
        CommentUtil.makeClassComment(innerClass);
    }


}
