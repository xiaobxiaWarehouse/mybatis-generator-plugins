package com.codi.base.mybatis.generator.plugins;

import com.codi.base.mybatis.generator.plugins.util.CommentUtil;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * 接口集成 BaseDao
 *
 * @author shi.pengyan
 * @date 2016-12-21 16:57
 */
public class BaseDaoPlugin extends PluginAdapter {
    private FullyQualifiedJavaType baseDao;

    public BaseDaoPlugin() {
        super();
//        baseDao = new FullyQualifiedJavaType("com.codi.base.dao.BaseDAO"); //$NON-NLS-1$
        baseDao = new FullyQualifiedJavaType("com.codi.base.dao.v2.GeneratedBaseDao"); //$NON-NLS-1$
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        super.clientGenerated(interfaze, topLevelClass, introspectedTable);
        // System.out.println("interfaze" + interfaze);
        // System.out.println("topLevelClass" + topLevelClass);

        if (interfaze != null) {
            FullyQualifiedJavaType baseDaoAndEntity = new FullyQualifiedJavaType("GeneratedBaseDao<" + introspectedTable.getBaseRecordType() + ">");
            interfaze.addSuperInterface(baseDaoAndEntity);
            interfaze.addImportedType(baseDao);

            String entityJavaType = introspectedTable.getBaseRecordType();
            FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(entityJavaType);
            interfaze.addImportedType(entityType); //导入实体
            
            CommentUtil.makeClassComment(interfaze);
        }

        return true;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }
}
