package cn.sspku.data.controller;

import cn.sspku.basics.log.LogType;
import cn.sspku.basics.log.SystemLog;
import cn.sspku.basics.utils.PageUtil;
import cn.sspku.basics.utils.ResultUtil;
import cn.sspku.basics.baseVo.PageVo;
import cn.sspku.basics.baseVo.Result;
import cn.sspku.data.entity.Log;
import cn.sspku.data.service.ILogService;
import cn.sspku.data.utils.sspkuNullUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * 系统日志
 * @author
 */
@RestController
@Api(tags = "日志管理接口")
@RequestMapping("/sspku/log")
@Transactional
public class LogController{

    @Autowired
    private ILogService iLogService;

    @SystemLog(about = "查询日志", type = LogType.DATA_CENTER,doType = "LOG-01")
    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询日志")
    public Result<Object> getAllByPage(@ModelAttribute Log log, @ModelAttribute PageVo page){
        QueryWrapper<Log> qw = new QueryWrapper<>();
        if(!sspkuNullUtils.isNull(log.getName())) {
            qw.like("name",log.getName());
        }
        if(log.getLogType() != null) {
            qw.eq("log_type",log.getLogType());
        }
        if(!sspkuNullUtils.isNull(log.getUsername())) {
            qw.like("username",log.getUsername());
        }
        if(!sspkuNullUtils.isNull(log.getIp())) {
            qw.like("ip",log.getIp());
        }
        if(!sspkuNullUtils.isNull(log.getStartDate())) {
            qw.ge("create_time",log.getStartDate());
            qw.le("create_time",log.getEndDate());
        }
        return ResultUtil.data(iLogService.page(PageUtil.initMpPage(page),qw));
    }
}
