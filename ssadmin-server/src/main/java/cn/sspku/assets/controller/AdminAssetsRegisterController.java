package cn.sspku.assets.controller;

import cn.sspku.assets.entity.AdminAssetsRegister;
import cn.sspku.assets.service.IAdminAssetsRegisterService;
import cn.sspku.basics.baseVo.PageVo;
import cn.sspku.basics.baseVo.Result;
import cn.sspku.basics.utils.PageUtil;
import cn.sspku.basics.utils.ResultUtil;
import cn.sspku.data.utils.sspkuNullUtils;
import cn.sspku.assets.entity.AdminAsset;
import cn.sspku.assets.service.IAdminAssetService;

import cn.sspku.assets.service.IAdminAssetsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 开发者
 */
@Slf4j
@RestController
@Api(tags = "行政资产采购")
@RequestMapping("/sspku/adminAssetsRegister")
@Transactional
public class AdminAssetsRegisterController {

    @Autowired
    private IAdminAssetsRegisterService iAdminAssetsRegisterService;

    @Autowired
    private IAdminAssetsService iAdminAssetsService;

    @Autowired
    private IAdminAssetService iAdminAssetService;

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询资产登记")
    public Result<IPage<AdminAssetsRegister>> getByPage(@ModelAttribute AdminAssetsRegister register, @ModelAttribute PageVo page){
        QueryWrapper<AdminAssetsRegister> qw = new QueryWrapper<>();
        if(!sspkuNullUtils.isNull(register.getName())) {
            qw.like("name",register.getName());
        }
//        if(register.getSubmitFlag() != 0) {
//            qw.eq("submit_flag",register.getSubmitFlag());
//        }
        return new ResultUtil<IPage<AdminAssetsRegister>>().setData(iAdminAssetsRegisterService.page(PageUtil.initMpPage(page),qw));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增资产采购")
    public Result<AdminAssetsRegister> insert(AdminAssetsRegister adminAssetsRegister){
        String assetId = adminAssetsRegister.getAssetId();
        AdminAsset adminAsset = iAdminAssetService.getById(assetId);
        if(adminAsset != null) {
            adminAssetsRegister.setName(adminAsset.getName());

        }
        if(iAdminAssetsRegisterService.saveOrUpdate(adminAssetsRegister)){
            return new ResultUtil<AdminAssetsRegister>().setData(adminAssetsRegister);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑资产采购")
    public Result<AdminAssetsRegister> update(AdminAssetsRegister adminAssetsRegister){
        String assetId = adminAssetsRegister.getAssetId();
        AdminAsset adminAsset = iAdminAssetService.getById(assetId);
        if(adminAsset != null) {
            adminAssetsRegister.setName(adminAsset.getName());

        }
        if(iAdminAssetsRegisterService.saveOrUpdate(adminAssetsRegister)){
            return new ResultUtil<AdminAssetsRegister>().setData(adminAssetsRegister);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除资产采购")
    public Result<Object> delAllByIds(@RequestParam Long[] ids){
        for(Long id : ids){
            iAdminAssetsRegisterService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/submitData", method = RequestMethod.POST)
    @ApiOperation(value = "提交资产登记申请")
    public Result<Object> submitData(@RequestParam String[] ids){

        for(String id : ids){
            AdminAsset asset= iAdminAssetService.getById(id);
            //打印id
            System.out.println("id:"+id);
            AdminAssetsRegister register=null;
            if(asset != null) {
                asset.setRegisterApprovalStatus(1);


                //循环assetsBuy,如果存在assetId==id，找到对应的资产，将审批状态改为1
                if(iAdminAssetsRegisterService.getByAssetId(id) != null){
                    register = iAdminAssetsRegisterService.getByAssetId(id);
                    }
                else {
                     register = new AdminAssetsRegister();
                register.setAssetId(id);
                register.setWarehouse(asset.getWarehouse());
                register.setName(asset.getName());}
                register.setAuditFlag(1);
            }

                iAdminAssetService.saveOrUpdate(asset);
                iAdminAssetsRegisterService.saveOrUpdate(register);
        }
        return ResultUtil.success();
    }
    @RequestMapping(value = "/returnData", method = RequestMethod.POST)
    @ApiOperation(value = "撤回资产登记申请")
    public Result<Object> returnData(@RequestParam String[] ids){
        for(String id : ids){
            AdminAssetsRegister buy = iAdminAssetsRegisterService.getByAssetId(id);
            AdminAsset asset= iAdminAssetService.getById(id);
            if(buy.getAuditFlag() == 1) {

                buy.setAuditFlag(0);
                asset.setRegisterApprovalStatus(0);
                iAdminAssetsRegisterService.saveOrUpdate(buy);
                iAdminAssetService.saveOrUpdate(asset);
            }
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/auditData", method = RequestMethod.POST)
    @ApiOperation(value = "审核资产采购")
    public Result<Object> auditData(@RequestParam String[] ids,@RequestParam int status){
        for(String id : ids){
            AdminAssetsRegister buy = iAdminAssetsRegisterService.getById(id);
            AdminAsset asset= iAdminAssetService.getById(buy.getAssetId());
        if(buy.getAuditFlag() == 1 || buy.getAuditFlag() == 3) {
            if (status==2) {
                asset.setStatus(1);
            }
                buy.setAuditFlag(status);
                iAdminAssetsRegisterService.saveOrUpdate(buy);
                asset.setRegisterApprovalStatus(status);

                iAdminAssetService.saveOrUpdate(asset);
            }
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/inWare", method = RequestMethod.POST)
    @ApiOperation(value = "入库资产采购")
    public Result<Object> inWare(@RequestParam String id,@RequestParam String date,@RequestParam String warehouse){
        AdminAssetsRegister buy = iAdminAssetsRegisterService.getById(id);
        AdminAsset asset= iAdminAssetService.getById(buy.getAssetId());

        asset.setStatus(1);
//        asset.setRegisterApprovalStatus(0);
        buy.setWarehouse(warehouse);
//        if(buy != null) {
//            int number = (int)iAdminAssetsService.count();
//            for(int i = 1 ; i <= quantity; i ++) {
//                AdminAssets adminAssets = new AdminAssets();
//                adminAssets.setBuyDate(date);
//                adminAssets.setMoney(BigDecimal.valueOf(money));
//                adminAssets.setBuyNumber(id);
//                adminAssets.setDemand(buy.getRemark());
//
//                adminAssets.setRemark("");
//
//                adminAssets.setName(buy.getName() + " " + i);
//                adminAssets.setWarehouse(warehouse);
//                adminAssets.setGiveDepart("");
//                adminAssets.setGiveName("");
//                adminAssets.setGiveId("");
//                adminAssets.setNo(number + i);
//                adminAssets.setDestroyName("");
//                adminAssets.setDestroyName("");
//                iAdminAssetsService.saveOrUpdate(adminAssets);
//                buy.setWareFlag(1);
//            }

            iAdminAssetsRegisterService.saveOrUpdate(buy);
            iAdminAssetService.saveOrUpdate(asset);
//        }
        return ResultUtil.success();
    }
}
