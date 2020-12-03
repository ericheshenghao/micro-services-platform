package cn.central.controller;


import cn.central.common.Page.PageRequest;
import cn.central.common.Page.PageResult;
import cn.central.common.model.Result;
import cn.central.controller.dto.DbConfig;
import cn.central.entity.gen.GenConfig;
import cn.central.service.SysCodeGenService;
import cn.central.utils.DbUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/pri/codeGen")
@Api(tags = {"代码生成器接口"})
public class SysCodeGenController {
    private   Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    SysCodeGenService sysCodeGenService;

    @PostMapping("findPage")
    public Result findPage(@RequestBody PageRequest pageRequest){
        logger.info(sysCodeGenService.findPage(pageRequest).getRecords().toString());
        PageResult page = sysCodeGenService.findPage(pageRequest);
        return  Result.succeed(page);
    }

    @Autowired
    DbUtil dbUtil;


    @PostMapping("generate")
    public void generate(@RequestBody GenConfig genConfig, HttpServletResponse response) throws IOException {
        byte[] data = sysCodeGenService.generatorCode(genConfig);

        response.reset();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", genConfig.getTableName()));
        response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
    }

    @GetMapping("dblist")
    public Result queryDbList(){
        logger.info(sysCodeGenService.queryDbList().toString());
      return Result.succeed(sysCodeGenService.queryDbList());
    }

    @PostMapping("changeSource")
    public Result changeSource(@RequestBody DbConfig dbConfig){

        dbUtil.setDbSource(dbConfig);
        PageRequest pageRequest = new PageRequest();
        PageResult page = sysCodeGenService.findPage(pageRequest);
        return Result.succeed(page);
    }


}
