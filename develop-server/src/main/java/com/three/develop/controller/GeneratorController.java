package com.three.develop.controller;

import com.three.common.vo.JsonResult;
import com.three.develop.param.ColumnInfo;
import com.three.develop.param.GenConfig;
import com.three.develop.service.GeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by csw on 2019/09/19.
 * Description:
 */
@Api(value = "代码生成", tags = "代码生成")
@RestController
@RequestMapping("/develop/generators")
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    @ApiOperation(value = "生成代码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "columnInfoList", value = "属性字段信息", required = true, dataType = "List"),
            @ApiImplicitParam(name = "genConfig", value = "代码生成配置", required = true, dataType = "GenConfig"),
            @ApiImplicitParam(name = "tableName", value = "表名称", required = true, dataType = "String"),
    })
    @PostMapping("/generate")
    public JsonResult create(@RequestBody List<ColumnInfo> columnInfoList, @RequestBody GenConfig genConfig, @RequestParam String tableName) {
        generatorService.generate(columnInfoList, genConfig, tableName);
        return JsonResult.ok("生成成功");
    }
}
