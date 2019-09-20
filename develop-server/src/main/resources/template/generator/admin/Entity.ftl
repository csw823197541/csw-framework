package ${package}.entity;

import com.three.common.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>

/**
* @author ${author}
* @date ${date}
*/
@Getter
@Setter
@Entity
@Table(name="${tableName}")
@EntityListeners(AuditingEntityListener.class)
public class ${className} implements Serializable {
<#if columns??>
    <#list columns as column>

    <#if column.columnComment != ''>
    // ${column.columnComment}
    </#if>
    <#if column.columnKey = 'PRI'>
    @Id
    <#if auto>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    </#if>
    @Column(<#if column.columnKey = 'UNI'>unique = true</#if><#if column.isNullable = 'NO' && column.columnKey != 'PRI'>, nullable = false</#if>)
    private ${column.columnType} ${column.changeColumnName};
    </#list>

    private String remark; // 描述/备注

    @Column(nullable = false, length = 2, columnDefinition = "int default 1")
    private Integer status = StatusEnum.OK.getCode(); // 记录状态，1：正常、2：锁定、3：删除

    @CreatedDate
    private Date createDate; // 创建时间

    @LastModifiedDate
    private Date updateDate; // 修改时间
</#if>


}