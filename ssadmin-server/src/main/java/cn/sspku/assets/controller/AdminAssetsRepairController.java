package cn.sspku.assets.controller;

import cn.sspku.assets.entity.AdminAsset;
import cn.sspku.assets.entity.AdminAssets;
import cn.sspku.assets.service.IAdminAssetService;
import cn.sspku.assets.service.IAdminAssetsService;
import cn.sspku.basics.utils.PageUtil;
import cn.sspku.basics.utils.ResultUtil;
import cn.sspku.basics.baseVo.PageVo;
import cn.sspku.basics.baseVo.Result;
import cn.sspku.basics.utils.SecurityUtil;
import cn.sspku.data.entity.User;
import cn.sspku.data.utils.sspkuNullUtils;
import cn.sspku.assets.entity.AdminAssetsRepair;
import cn.sspku.assets.service.IAdminAssetsRepairService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 开发者
 */
@Slf4j
@RestController
@Api(tags = "固定资产报修管理接口")
@RequestMapping("/sspku/adminAssetsRepair")
@Transactional
public class AdminAssetsRepairController {

    @Autowired
    private IAdminAssetsRepairService iAdminAssetsRepairService;


    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IAdminAssetService iAdminAssetService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增固定资产报修")
    public Result<Object> add(@RequestParam String assetId){
        AdminAsset aa = iAdminAssetService.getById(assetId);
        if(aa == null) {
            return ResultUtil.error("资产不存在");
        }
        aa.setStatus(3);
        aa.setRepairApprovalStatus(1);
        iAdminAssetService.saveOrUpdate(aa); // 保存资产的状态更改
        AdminAssetsRepair aar = new AdminAssetsRepair();
        aar.setAssetId(aa.getId());
        aar.setAssetName(aa.getName());
        aar.setAssetType(aa.getGiveDepart());
        aar.setAssetWare(aa.getWarehouse());
        aar.setRepairFlag(1);

        User currUser = securityUtil.getCurrUser();
        aar.setRepairId(currUser.getId());
        aar.setRepairName(currUser.getNickname());
        iAdminAssetsRepairService.saveOrUpdate(aar);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条固定资产报修")
    public Result<AdminAssetsRepair> get(@RequestParam String id){
        return new ResultUtil<AdminAssetsRepair>().setData(iAdminAssetsRepairService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部固定资产报修个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iAdminAssetsRepairService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部固定资产报修")
    public Result<List<AdminAssetsRepair>> getAll(){
        return new ResultUtil<List<AdminAssetsRepair>>().setData(iAdminAssetsRepairService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询固定资产报修")
    public Result<IPage<AdminAssetsRepair>> getByPage(@ModelAttribute AdminAssetsRepair adminAssetsRepair ,@ModelAttribute PageVo page){
        QueryWrapper<AdminAssetsRepair> qw = new QueryWrapper<>();
        if(!sspkuNullUtils.isNull(adminAssetsRepair.getAssetName())) {
            qw.like("asset_name",adminAssetsRepair.getAssetName());
        }
//        if(!sspkuNullUtils.isNull(adminAssetsRepair.getRepairName())) {
//            qw.like("repair_name",adminAssetsRepair.getRepairName());
//        }
        // 只查询 repairFlag 为 1 的条目
        //qw.eq("repair_flag", 1);
        //qw.eq("repair_id",securityUtil.getCurrUser().getId());
        IPage<AdminAssetsRepair> data = iAdminAssetsRepairService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<AdminAssetsRepair>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改固定资产报修")
    public Result<AdminAssetsRepair> saveOrUpdate(AdminAssetsRepair adminAssetsRepair){
        if(iAdminAssetsRepairService.saveOrUpdate(adminAssetsRepair)){
            return new ResultUtil<AdminAssetsRepair>().setData(adminAssetsRepair);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增固定资产报修")
    public Result<AdminAssetsRepair> insert(AdminAssetsRepair adminAssetsRepair){
        iAdminAssetsRepairService.saveOrUpdate(adminAssetsRepair);
        return new ResultUtil<AdminAssetsRepair>().setData(adminAssetsRepair);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑固定资产报修")
    public Result<AdminAssetsRepair> update(AdminAssetsRepair adminAssetsRepair){
        AdminAsset aa = iAdminAssetService.getById(adminAssetsRepair.getAssetId());
        aa.setStatus(2);
        aa.setRepairApprovalStatus(0);
        adminAssetsRepair.setRepairFlag(2);
        iAdminAssetService.saveOrUpdate(aa); // 保存资产的状态更改
        iAdminAssetsRepairService.saveOrUpdate(adminAssetsRepair);
        return new ResultUtil<AdminAssetsRepair>().setData(adminAssetsRepair);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除固定资产报修")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iAdminAssetsRepairService.removeById(id);
        }
        return ResultUtil.success();
    }
}
