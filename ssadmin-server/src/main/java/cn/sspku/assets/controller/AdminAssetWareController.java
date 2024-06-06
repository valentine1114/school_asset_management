package cn.sspku.assets.controller;

import cn.hutool.core.date.DateUtil;
import cn.sspku.basics.baseVo.PageVo;
import cn.sspku.basics.baseVo.Result;
import cn.sspku.basics.utils.PageUtil;
import cn.sspku.basics.utils.ResultUtil;
import cn.sspku.assets.entity.AdminAssetWare;
import cn.sspku.assets.service.IAdminAssetWareService;
import cn.sspku.data.utils.sspkuNullUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 开发者
 */
@RestController
@Api(tags = "行政资产仓库档案")
@RequestMapping("/sspku/adminAssetWare")
@Transactional
public class AdminAssetWareController {

    @Autowired
    private IAdminAssetWareService iAdminAssetWareService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有资产仓库档案")
    public Result<List<AdminAssetWare>> getAll(){
        return new ResultUtil<List<AdminAssetWare>>().setData(iAdminAssetWareService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询资产仓库档案")
    public Result<IPage<AdminAssetWare>> getByPage(@ModelAttribute AdminAssetWare ware,@ModelAttribute PageVo page){
        QueryWrapper<AdminAssetWare> qw = new QueryWrapper<>();
        if(!sspkuNullUtils.isNull(ware.getName())) {
            qw.like("name",ware.getName());
        }
        if(!sspkuNullUtils.isNull(ware.getAdminName())) {
            qw.like("admin_name",ware.getAdminName());
        }
        return new ResultUtil<IPage<AdminAssetWare>>().setData(iAdminAssetWareService.page(PageUtil.initMpPage(page),qw));
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改资产仓库档案")
    public Result<AdminAssetWare> saveOrUpdate(AdminAssetWare adminAssetWare){

        if(iAdminAssetWareService.saveOrUpdate(adminAssetWare)){
            return new ResultUtil<AdminAssetWare>().setData(adminAssetWare);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除资产仓库档案")
    public Result<Object> delAllByIds(@RequestParam Long[] ids){

        for(Long id : ids){
            iAdminAssetWareService.removeById(id);
        }
        return ResultUtil.success();
    }
}
