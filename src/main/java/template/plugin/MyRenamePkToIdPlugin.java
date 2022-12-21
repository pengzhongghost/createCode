package template.plugin;

import java.lang.reflect.Field;
import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.ibatis2.sqlmap.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 重命名 Mapper.java 和 Mapper.xml 文件方法名带 PrimaryKey 的改为 ID。<br>
 * 如：selectByPrimaryKey 改为 selectById
 *
 * @author pengzhong
 * @since 2022/12/14
 */
public class MyRenamePkToIdPlugin extends PluginAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyRenamePkToIdPlugin.class);
    private static final String PK = "PrimaryKey";
    private static final String ID = "Id";

    public boolean validate(List<String> warnings) {
        return true;
    }

    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        AbstractXmlElementGenerator plugin = new AbstractXmlElementGenerator() {
            @Override
            public void addElements(XmlElement parentElement) {
                Field f = null;
                try {
                    f = Attribute.class.getDeclaredField("value");
                    f.setAccessible(true);
                    LOGGER.debug(Attribute.class.getName() + " exists declared field 'value'");
                } catch (NoSuchFieldException e) {
                    throw new IllegalStateException(
                            Attribute.class.getName() + " not exists declared field 'value'");
                }

                for (Element e : parentElement.getElements()) {
                    if (e instanceof XmlElement) {
                        XmlElement x = (XmlElement) e;
                        if (x.getName().equals("insert")
                                || x.getName().equals("delete")
                                || x.getName().equals("update")
                                || x.getName().equals("select")) {
                            for (Attribute a : x.getAttributes()) {
                                //if (a.getName().equals("id") && a.getValue().endsWith(PK)) {
                                if (a.getValue().contains(PK)) {
                                    try {
                                        f.set(a, renamePkToId(a.getValue()));
                                    } catch (IllegalAccessException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };

        plugin.setContext(context);
        plugin.setIntrospectedTable(introspectedTable);
        plugin.addElements(document.getRootElement());

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public boolean clientGenerated(Interface theInterface, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        AbstractJavaMapperMethodGenerator plugin = new AbstractJavaMapperMethodGenerator() {
            @Override
            public void addInterfaceElements(Interface interfaze) {
                for (Method e : interfaze.getMethods()) {
//                    if (e.getName().endsWith(PK)) {
                    if (e.getName().contains(PK)) {
                        e.setName(renamePkToId(e.getName()));
                    }
                }
            }
        };

        plugin.setContext(context);
        plugin.setIntrospectedTable(introspectedTable);
        plugin.addInterfaceElements(theInterface);

        return super.clientGenerated(theInterface, topLevelClass, introspectedTable);
    }

    private static String renamePkToId(String name) {
        if (name.contains(PK)) {
            //name = name.substring(0, name.length() - PK.length()) + ID;
            name = name.replace(PK, ID);
        }
        return name;
    }
}

