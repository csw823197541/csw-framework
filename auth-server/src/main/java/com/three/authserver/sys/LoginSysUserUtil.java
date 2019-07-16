package com.three.authserver.sys;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by csw on 2019/07/16.
 * Description:
 */
public class LoginSysUserUtil {

    public static SysUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SysUser sysUser = (SysUser) authentication.getPrincipal();
        return sysUser;
    }
}
