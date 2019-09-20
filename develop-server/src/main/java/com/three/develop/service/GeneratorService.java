package com.three.develop.service;

import com.three.commonclient.exception.BusinessException;
import com.three.develop.param.ColumnInfo;
import com.three.develop.param.GenConfig;
import com.three.develop.utils.GenUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by csw on 2019/09/19.
 * Description:
 */
@Service
public class GeneratorService {

    public void generate(List<ColumnInfo> columnInfoList, GenConfig genConfig, String tableName) {
        try {
            GenUtil.generateCode(columnInfoList, genConfig, tableName);
        } catch (Exception e) {
            throw new BusinessException("代码生成过程中发生异常");
        }
    }
}
