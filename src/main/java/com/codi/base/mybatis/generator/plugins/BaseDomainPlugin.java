package com.codi.base.mybatis.generator.plugins;

import com.codi.base.mybatis.generator.plugins.util.CommentUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.*;

/**
 * 模型继承BaseDomain
 *
 * @author shi.pengyan
 * @date 2016-12-21 16:14
 */
public class BaseDomainPlugin extends PluginAdapter {

    private FullyQualifiedJavaType superParent;
    private FullyQualifiedJavaType jsonFieldImport;
    private boolean suppressJavaInterface;

    private final Collection<Annotations> annotations; // 自定义注解

    public BaseDomainPlugin() {
        super();
        superParent = new FullyQualifiedJavaType("com.codi.base.domain.BaseDomain"); //$NON-NLS-1$
        jsonFieldImport = new FullyQualifiedJavaType("com.alibaba.fastjson.annotation.JSONField");
        annotations = new HashSet<Annotations>(Annotations.values().length);
    }

    public boolean validate(List<String> warnings) {
        // this plugin is always valid
        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        suppressJavaInterface = Boolean.valueOf(properties.getProperty("suppressJavaInterface")); //$NON-NLS-1$

        //@Data is default annotation
        annotations.add(Annotations.DATA);
        annotations.add(Annotations.ACCESSOR);
        annotations.add(Annotations.NO_ARGS_CONSTRUCTOR);
//        annotations.add(Annotations.ALL_ARGS_CONSTRUCTOR); //意义不大
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        makeSuperParent(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        makeSuperParent(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        makeSuperParent(topLevelClass, introspectedTable);
        return true;
    }

    /**
     * 阻止生成set方法
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * 阻止生成get方法
     *
     * @param method
     * @param topLevelClass
     * @param introspectedColumn
     * @param introspectedTable
     * @param modelClassType
     * @return
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }


    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        super.modelFieldGenerated(field, topLevelClass, introspectedColumn, introspectedTable, modelClassType);

        addFieldAnnotation(field, topLevelClass);

        return true;
    }


    /**
     * 添加父类和UID
     *
     * @param topLevelClass
     * @param introspectedTable
     */
    protected void makeSuperParent(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        addDataAnnotation(topLevelClass);

        if (!suppressJavaInterface) {
            topLevelClass.addImportedType(superParent);
            topLevelClass.setSuperClass(superParent);

            Field field = new Field();
            field.setFinal(true);
            field.setInitializationString("1L"); //$NON-NLS-1$
            field.setName("serialVersionUID"); //$NON-NLS-1$
            field.setStatic(true);
            field.setType(new FullyQualifiedJavaType("long")); //$NON-NLS-1$
            field.setVisibility(JavaVisibility.PRIVATE);
            context.getCommentGenerator().addFieldComment(field, introspectedTable);

            topLevelClass.addField(field);

            CommentUtil.makeClassComment(topLevelClass);
        }
    }


    /**
     * 处理字段上的注解
     *
     * @param field
     * @param topLevelClass
     */
    protected void addFieldAnnotation(Field field, TopLevelClass topLevelClass) {
        addDateAnnotation(field, topLevelClass);
    }

    /**
     * 为日期型字段添加注解
     *
     * @param field
     */
    protected void addDateAnnotation(Field field, TopLevelClass topLevelClass) {

        String fieldType = field.getType().getFullyQualifiedName();
//        System.out.println("field type is " + fieldType);

        if ("java.util.Date".equalsIgnoreCase(fieldType)) {
            field.addAnnotation("@JSONField(format = \"yyyy-MM-dd HH:mm:ss\")");
            topLevelClass.addImportedType(jsonFieldImport);
        }

    }


    /**
     * Adds the lombok annotations' imports and annotations to the class
     *
     * @param topLevelClass
     */
    private void addDataAnnotation(TopLevelClass topLevelClass) {
        for (Annotations annotation : annotations) {
            topLevelClass.addImportedType(annotation.javaType);
            topLevelClass.addAnnotation(annotation.name);
        }
    }

    /**
     * Lombok 注解
     */
    private enum Annotations {
        DATA("data", "@Data", "lombok.Data"),
        //BUILDER("builder", "@Builder", "lombok.Builder"),
        ACCESSOR("accessors", "@Accessors(chain = true)", "lombok.experimental.Accessors"),
        ALL_ARGS_CONSTRUCTOR("allArgsConstructor", "@AllArgsConstructor", "lombok.AllArgsConstructor"),
        NO_ARGS_CONSTRUCTOR("noArgsConstructor", "@NoArgsConstructor", "lombok.NoArgsConstructor"),
        TO_STRING("toString", "@ToString", "lombok.ToString");


        private final String paramName;
        private final String name;
        private final FullyQualifiedJavaType javaType;


        Annotations(String paramName, String name, String className) {
            this.paramName = paramName;
            this.name = name;
            this.javaType = new FullyQualifiedJavaType(className);
        }

        private static Annotations getValueOf(String paramName) {
            for (Annotations annotation : Annotations.values())
                if (String.CASE_INSENSITIVE_ORDER.compare(paramName, annotation.paramName) == 0)
                    return annotation;

            return null;
        }

        private static Collection<Annotations> getDependencies(Annotations annotation) {
            if (annotation == ALL_ARGS_CONSTRUCTOR)
                return Collections.singleton(NO_ARGS_CONSTRUCTOR);
            else
                return Collections.emptyList();
        }
    }

}
