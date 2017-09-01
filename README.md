mybatis-generator-plugins
-----------


## usage

- pom.xml
````
...
<profiles>
        <profile>
            <id>generate-mybatis</id>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.mybatis.generator</groupId>
                        <artifactId>mybatis-generator-maven-plugin</artifactId>
                        <version>1.3.2</version>
                        <configuration>
                            <configurationFile>
                                src/main/resources/generator/generatorConfig.xml
                            </configurationFile>
                            <overwrite>false</overwrite>
                        </configuration>
                        <executions>
                            <execution>
                                <id>mybatis-generator</id>
                                <phase>generate-sources</phase>
                                <goals>
                                    <goal>generate</goal>
                                </goals>
                            </execution>
                        </executions>
                        <dependencies>
                            <dependency>
                                <groupId>mysql</groupId>
                                <artifactId>mysql-connector-java</artifactId>
                                <version>${mysql.connector.version}</version>
                            </dependency>
                            <dependency>
                                <groupId>com.codi.base.mybatis.generator</groupId>
                                <artifactId>mybatis-generator-plugins</artifactId>
                                <version>2.0.0</version>
                            </dependency>
                        </dependencies>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
   
    ...
````
- generatorConfig.xml

````
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE generatorConfiguration
       PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
       "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
   
   <generatorConfiguration>
       <properties resource="generator/config.properties"/>
   
       <context id="Mysql" targetRuntime="MyBatis3">
   
           <!-- 继承BaseDomain -->
           <plugin type="com.codi.base.mybatis.generator.plugins.BaseDomainPlugin">
               <property name="addGWTInterface" value="true"/>
           </plugin>
   
           <!-- 将UserMapper改名为UserDao 接口 -->
           <plugin type="com.codi.base.mybatis.generator.plugins.rename.RenameJavaMapperPlugin">
               <property name="searchString" value="Mapper$"/>
               <property name="replaceString" value="Dao"/>
           </plugin>
   
           <plugin type="com.codi.base.mybatis.generator.plugins.BaseDaoPlugin"/>
   
           <!-- 自定义注释 -->
           <commentGenerator type="com.codi.base.mybatis.generator.plugins.comment.CustomCommentGenerator">
               <property name="suppressDate" value="true"/>
               <property name="suppressAllComments" value="true"/>
           </commentGenerator>
   
           <jdbcConnection driverClass="com.mysql.jdbc.Driver" connectionURL="${db.url}" userId="${db.username}"
                           password="${db.password}">
           </jdbcConnection>
   
           <javaTypeResolver>
               <property name="forceBigDecimals" value="false"/>
           </javaTypeResolver>
   
           <javaModelGenerator targetPackage="${model.package}" targetProject="${model.path}">
               <!-- enableSubPackages:是否让schema作为包的后缀 -->
               <property name="enableSubPackages" value="false"/>
               <!-- 从数据库返回的值被清理前后的空格 -->
               <property name="trimStrings" value="true"/>
           </javaModelGenerator>
   
           <sqlMapGenerator targetPackage="${sqlmap.package}" targetProject="${sqlmap.path}">
               <property name="enableSubPackages" value="false"/>
           </sqlMapGenerator>
   
           <javaClientGenerator type="XMLMAPPER" targetPackage="${dao.package}" targetProject="${dao.path}">
               <property name="enableSubPackages" value="false"/>
           </javaClientGenerator>
   
   
           <!-- 表模型 -->
           <table schema="codi_portal" tableName="FM_PRIV" domainObjectName="Priv"
                  enableCountByExample="false" enableUpdateByExample="false" enableDeleteByExample="false"
                  enableSelectByExample="false"
                  selectByExampleQueryId="false">
               <property name="useActualColumnNames" value="false"/>
           </table>
       </context>
   </generatorConfiguration>

````
- `generator/config.properties`

````
# configuration for mybatis generator plugin
#
#db
db.url=jdbc:mysql://121.40.187.122:3306/codi_portal
db.username=codipot_dev
db.password=cdpotdv580Sql
#
#model file
model.package=com.codi.superman.api.domain
model.path=../superman-api/src/main/java
#
#sqlmap file
sqlmap.package=mybatis
sqlmap.path=../superman-biz/src/main/resources
#
#dao file
dao.package=com.codi.superman.biz.dao
dao.path=../superman-biz/src/main/java
````
