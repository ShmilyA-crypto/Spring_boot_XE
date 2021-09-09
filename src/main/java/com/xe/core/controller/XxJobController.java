package com.xe.core.controller;


import com.xe.core.service.DbUserService;
import com.xe.core.util.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DEXTER
 * @since 2021-08-19
 */
@RestController
@Api(tags = "XxJobs")
@RequestMapping("/XxJobs")
public class XxJobController extends BaseController {

    @Autowired
    DbUserService dbUserService;

    @ApiOperation(value = "XxJob测试", httpMethod = "GET", response = ResultInfo.class, notes = "测试")
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String")
    })
    public ResultInfo start(){
        return renderSuccess("测试");
    }
}

