package ${package}.param;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by ${author} on ${date}.
 * Description:
 */
@Builder
@Data
public class ${className}Param {

    private Long id;

<#if columns??>
    <#list columns as column>
        <#if column.isNullable == false>
    @NotBlank(message = "${column.columnComment}不可以为空")
        </#if>
    private ${column.columnType} ${column.columnName};<#if column.columnComment != ''> // ${column.columnComment}</#if>

    </#list>
</#if>

    private String remark;

}