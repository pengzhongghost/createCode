import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengzhong
 * @since 2022/12/14
 */
public class GeneratorVO {

    public static void main(String[] args) throws IOException {


    }

    public static void handle(List<String> tables) throws Exception {
        for (String table : tables) {
            //todo 1.生成带注解的VO
            String srcPath = "/Users/pengzhong/IdeaProjects/all-master/mygen/src/main/java/com/redu/tianzhi/mysql/entity/BillEntity.java".replace("Bill", table);
            String targetPath = "/Users/pengzhong/IdeaProjects/all-master/mygen/src/main/java/com/redu/tianzhi/vo/BillVO.java".replace("Bill", table);
            replaceTextContent(srcPath, targetPath, null);
            //todo 2.处理mapper
            Map<String, String> replaceMap = new HashMap<String, String>();
            replaceMap.put("ChannelAccountBill", table);
            String otherTemplate = getMapperTemplate("/Users/pengzhong/IdeaProjects/all-master/mygen/src/main/resources/templateMapper.txt", replaceMap);
            String mapperSrcPath = "/Users/pengzhong/IdeaProjects/all-master/mygen/src/main/java/com/redu/tianzhi/mysql/mapper/BillMapper.java".replace("Bill", table);
            //名字一样
            replaceTextContent(mapperSrcPath, mapperSrcPath, otherTemplate);
            //todo 3.处理mapper.xml
            String xmlSrcPath = "/Users/pengzhong/IdeaProjects/all-master/mygen/src/main/java/com/redu/tianzhi/mysql/mapper/BillMapper.xml".replace("Bill", table);
            otherTemplate = getMapperTemplate("/Users/pengzhong/IdeaProjects/all-master/mygen/src/main/resources/template.xml", replaceMap);
            replaceTextContent(xmlSrcPath, xmlSrcPath, otherTemplate);
        }
    }

    public static String getMapperTemplate(String srcPath, Map<String, String> replaceMap) throws IOException {
        // 读
        File file = new File(srcPath);
        FileReader in = new FileReader(file);
        BufferedReader bufIn = new BufferedReader(in);
        String line;
        StringBuilder sb = new StringBuilder();
        while (null != (line = bufIn.readLine())) {
            if (null != replaceMap && replaceMap.size() > 0) {
                for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
                    line = line.replaceAll(entry.getKey(), entry.getValue());
                }
            }
            sb.append("    ").append(line);
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }


    public static void replaceTextContent(String srcPath, String targetPath, String otherTemplate) throws IOException {
        // 读
        File file = new File(srcPath);
        FileReader in = new FileReader(file);
        BufferedReader bufIn = new BufferedReader(in);
        // 内存流, 作为临时流
        CharArrayWriter tempStream = new CharArrayWriter();
        // 替换
        String line, beforeLine = null, beforeBeforeLine = null;
        while ((line = bufIn.readLine()) != null) {
            //添加knife4j注释
            if (null != beforeLine && beforeLine.equals("     */")) {
                String comment = "";
                String[] split = beforeBeforeLine.split("\\* ");
                if (split.length > 1) {
                    comment = split[1];
                }
                tempStream.write("    @ApiModelProperty(value = \"" + comment + "\")");
                // 添加换行符
                tempStream.append(System.getProperty("line.separator"));
            }
            //Entity转VO
            if (!srcPath.contains("Mapper") && !srcPath.contains("xml")) {
                if (line.contains("Entity")) {
                    line = line.replace("Entity", "VO");
                }
            } else if (srcPath.contains("xml")) {
                if (line.contains("</mapper>")) {
                    tempStream.write(otherTemplate);
                    // 添加换行符
                    tempStream.append(System.getProperty("line.separator"));
                }
            } else {
                if (line.contains("}")) {
                    tempStream.write(otherTemplate);
                    // 添加换行符
                    tempStream.append(System.getProperty("line.separator"));
                }
            }
            //添加import引入包
            if (line.contains("import lombok.Data;")) {
                tempStream.write("import io.swagger.annotations.ApiModelProperty;");
                // 添加换行符
                tempStream.append(System.getProperty("line.separator"));
            }
            // 将该行写入内存
            tempStream.write(line);
            // 添加换行符
            tempStream.append(System.getProperty("line.separator"));
            beforeBeforeLine = beforeLine;
            beforeLine = line;
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
