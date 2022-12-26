/**
 * @author pengzhong
 * @since 2022/12/26
 */
public class Test {

    public static void main(String[] args) {
        // * @since 2022/12/14
        String str = "// * @since 2022/12/14";
        str = str.replaceAll("@since 2022/12/14", "@since 2022/12/26");
        System.out.println(str);
    }

}
