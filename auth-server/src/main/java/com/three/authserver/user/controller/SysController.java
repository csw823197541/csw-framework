package com.three.authserver.user.controller;

import com.three.authserver.sys.LoginSysUserUtil;
import com.three.authserver.user.service.UserService;
import com.three.authserver.user.vo.MenuVo;
import com.three.common.vo.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by csw on 2019/04/05.
 * Description:
 */
@Api(value = "系统管理", tags = "系统管理")
@RestController
@RequestMapping("/sys")
public class SysController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取个人信息")
    @GetMapping("/userInfo")
    public JsonResult userInfo() {
        return JsonResult.ok().put("user", LoginSysUserUtil.getLoginUser());
    }

    @ApiOperation(value = "获取左侧菜单信息")
    @GetMapping("/menuInfo")
    public JsonResult menuInfo() {
        List<MenuVo> menuVoList = userService.getMenuInfo();
        return JsonResult.ok().put("data", menuVoList);
    }
}
