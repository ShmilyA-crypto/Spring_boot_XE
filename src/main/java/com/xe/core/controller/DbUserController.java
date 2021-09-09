package com.xe.core.controller;


import com.xe.core.entity.DbUser;
import com.xe.core.service.DbUserService;
import com.xe.core.util.DataShareUtils;
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
 * @since 2021-06-30
 */
@RestController
@Api(tags = "测试")
@RequestMapping("/dbuser")
public class DbUserController extends BaseController {

    @Autowired
    private DbUserService dbUserService;

    @ApiOperation(value = "新增测试", httpMethod = "GET", response = ResultInfo.class, notes = "新增测试")
    @RequestMapping(value = "/insertTest.action", method = RequestMethod.GET)
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "id", value = "id", dataType = "int"),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String")
    })
    public ResultInfo insertTest(){
        DbUser dbUser = DataShareUtils.parameterToClass(request,DbUser.class);
        dbUserService.save(dbUser);
        return renderSuccess("新增成功");
    }
}

