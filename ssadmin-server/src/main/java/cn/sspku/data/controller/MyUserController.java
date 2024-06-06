package cn.sspku.data.controller;

import cn.sspku.basics.log.LogType;
import cn.sspku.basics.log.SystemLog;
import cn.sspku.basics.utils.PageUtil;
import cn.sspku.basics.utils.ResultUtil;
import cn.sspku.basics.baseVo.PageVo;
import cn.sspku.basics.baseVo.Result;
import cn.sspku.data.entity.User;
import cn.sspku.data.service.IUserService;
import cn.sspku.data.utils.sspkuNullUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 
 */
@Slf4j
@RestController
@Api(tags = "mybatis用户接口")
@RequestMapping("/sspku/myUser")
@Transactional
public class MyUserController {

    @Autowired
    private IUserService iUserService;

    @SystemLog(about = "查询用户", type = LogType.DATA_CENTER,doType = "USER-01")
    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询用户")
    public Result<IPage<User>> getByPage(@ModelAttribute User user,@ModelAttribute PageVo page){
        QueryWrapper<User> qw = new QueryWrapper<>();
        if(user.getDepartmentId() != null && !sspkuNullUtils.isNull(user.getDepartmentId())) {
            qw.like("department_id",user.getDepartmentId());
        }
        if(user.getNickname() != null && !sspkuNullUtils.isNull(user.getNickname())) {
            qw.like("nickname",user.getNickname());
        }
        IPage<User> data = iUserService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<User>>().setData(data);
    }
}
