package com.three.user.entity;

import com.three.common.enums.AdminEnum;
import com.three.common.enums.ExcelType;
import com.three.common.enums.StatusEnum;
import com.three.common.excel.Excel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by csw on 2019/03/22.
 * Description:
 */

@Getter
@Setter
@Entity
@Table(name = "sys_user")
@EntityListeners(AuditingEntityListener.class)
@Excel("用户数据")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(value = "用户ID", type = ExcelType.EXPORT)
    private Long id;

    @Excel("用户名")
    @Column(nullable = false)
    private String username;

    @JsonIgnore
    private String password;

    private String salt;

    @Excel("昵称")
    private String nickname;

    private String picture;

    @Excel(value = "性别", dict = "USER_SEX")
    private String sex;

    @Excel("手机号码")
    private String phone;

    @Excel("电子邮箱")
    private String email;

    @Column(nullable = false, length = 2, columnDefinition = "int default 2")
    private Integer isAdmin = AdminEnum.NO.getCode();

    @Column(nullable = false, length = 2, columnDefinition = "int default 1")
    private Integer status = StatusEnum.OK.getCode();

    @Excel("备注")
    private String remark;

    @CreatedDate
    @Excel("创建时间")
    private Date createDate;

    @LastModifiedDate
    @Excel("更新时间")
    private Date updateDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>(0);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
