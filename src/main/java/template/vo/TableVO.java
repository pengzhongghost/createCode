package template.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengzhong
 * @since 2022/12/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableVO {

    private String remarks;

    private TableConfiguration tableConfiguration;

    @Data
    public static class TableConfiguration {
        private String tableName;
    }

}
