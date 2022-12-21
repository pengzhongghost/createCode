

import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.json.JSONUtil;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import template.vo.TableVO;

public class GeneratorSqlmap {

    private static final String servicePrefix = "/Users/pengzhong/IdeaProjects/all-master/mygen/src/main/java/template/";

    private static final String templateClassName = "ChannelAccountBill";


    public static void main(String[] args) throws Exception {
        try {
            //todo 1.mybatis-generator生成文件
            Configuration config = generator();
            //todo 2.自己复制模版生成controller，service，domain层
            generatorService(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * todo 1.mybatis-generator生成文件
     */
    public static Configuration generator() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指定 逆向工程配置文件
        File configFile = new File("src/main/resources/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);
        return config;
    }

    /**
     * todo 2.自己复制模版生成controller，service，domain层
     */
    public static void generatorService(Configuration config) throws IOException {
        Context context = config.getContext("testTables");
        JavaModelGeneratorConfiguration javaModel = context.getJavaModelGeneratorConfiguration();
        String targetPackage = javaModel.getTargetPackage();
        String targetProject = javaModel.getTargetProject();
        String targetPath = targetProject + "/" + targetPackage.replace(".mysql.entity", "").replace(".", "/") + "/";

        Map<String, String> tableMap = new HashMap<String, String>();
        List<TableConfiguration> testTables = config.getContext("testTables").getTableConfigurations();
        try {
            Field introspectedTables = context.getClass().getDeclaredField("introspectedTables");
            introspectedTables.setAccessible(true);
            List<TableVO> tables = JSONUtil.toList(JSONUtil.toJsonStr(introspectedTables.get(context)), TableVO.class);
            for (TableVO table : tables) {
                tableMap.put(table.getTableConfiguration().getTableName(), table.getRemarks().replace(" 表", "模块"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String> entry : tableMap.entrySet()) {
            replaceTextContent(entry.getKey(), entry.getValue(), targetPath);
        }
    }

    public static void replaceTextContent(String targetTable, String targetTableDesc, String targetPath) throws IOException {
        Map<String, String> replaceMap = new HashMap<String, String>();
        replaceMap.put(templateClassName, targetTable);
        replaceMap.put(templateClassName.substring(0, 1).toLowerCase() + templateClassName.substring(1),
                targetTable.substring(0, 1).toLowerCase() + targetTable.substring(1));
        //操作日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        replaceMap.put("@since 2022/11/14", "@since " + simpleDateFormat.format(new Date()));
        List<String> serviceList = new ArrayList<String>();
        serviceList.add("service");
        serviceList.add("domain");
        serviceList.add("controller");
        for (String service : serviceList) {
            String UpperService = service.substring(0, 1).toUpperCase() + service.substring(1);
            replaceTextContent(servicePrefix + service + "/" + templateClassName + UpperService + ".java",
                    targetPath + service + "/" + targetTable + UpperService + ".java", replaceMap, service, targetTableDesc);
            if (service.equals("controller")) {
                continue;
            }
            replaceTextContent(servicePrefix + service + "/impl/" + templateClassName + UpperService + "Impl.java",
                    targetPath + service + "/impl/" + targetTable + UpperService + "Impl.java", replaceMap, service, targetTableDesc);
        }
    }

    /**
     * 替换文本文件中的 非法字符串
     *
     * @param srcPath
     * @param targetPath
     * @throws IOException
     */
    public static void replaceTextContent(String srcPath, String targetPath, Map<String, String> replaceMap, String service, String targetTableDesc) throws IOException {
        // 读
        File file = new File(srcPath);
        FileReader in = new FileReader(file);
        BufferedReader bufIn = new BufferedReader(in);
        // 内存流, 作为临时流
        CharArrayWriter tempStream = new CharArrayWriter();
        // 替换
        String line = null;
        while ((line = bufIn.readLine()) != null) {
            // 替换每行中, 符合条件的字符串
            for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
                line = line.replaceAll(entry.getKey(), entry.getValue());
            }
            //替换package
            if (line.contains("package")) {
                String str = targetPath.split("java")[1].replace("/", ".");
                str = "package " + str.split(service)[0].substring(1) + service + (srcPath.contains("impl") ? ".impl" : "") + ";";
                line = line.replace(line, str);
            }
            //controller层替换注释
            if (service.equals("controller") && line.contains("@Api(tags = ")) {
                line = line.replace(line.split("@Api\\(tags = ")[1], targetTableDesc + "\")");
            }
            // 将该行写入内存
            tempStream.write(line);
            // 添加换行符
            tempStream.append(System.getProperty("line.separator"));
        }
        // 关闭 输入流
        bufIn.close();
        // 将内存中的流 写入 文件
        File targetFile = new File(targetPath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        FileWriter out = new FileWriter(targetPath);
        tempStream.writeTo(out);
        out.close();
    }

}
