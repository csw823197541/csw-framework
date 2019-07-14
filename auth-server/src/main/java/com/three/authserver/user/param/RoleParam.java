package com.three.authserver.user.param;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
public class RoleParam {

    private Long id;

    @Length(min = 2, max = 20, message = "角色名称长度需要在2-20个字之间")
    private String roleName;

    private String remark;

}