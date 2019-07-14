package com.three.authserver.user.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by csw on 2019/03/27.
 * Description:
 */
@Data
public class UserParam implements Serializable {

    private Long id;

    @NotEmpty(message = "用户名不能为空")
    private String username;

    private String nickname;

    private String sex;

    private String phone;

    private String email;

    private String remark;

    private String roleIds;

}
