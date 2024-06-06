package cn.sspku.assets.controller;

import cn.hutool.core.date.DateUtil;
import cn.sspku.assets.entity.*;
import cn.sspku.assets.service.IAdminAssetService;
import cn.sspku.assets.service.IAdminAssetsRetireService;
import cn.sspku.assets.service.IAdminAssetsRetireService;
import cn.sspku.assets.service.IAdminAssetsService;
import cn.sspku.basics.baseVo.PageVo;
import cn.sspku.basics.baseVo.Result;
import cn.sspku.basics.utils.PageUtil;
import cn.sspku.basics.utils.ResultUtil;
import cn.sspku.basics.utils.SecurityUtil;
import cn.sspku.data.entity.User;
import cn.sspku.data.utils.sspkuNullUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 开发者
 */
@Slf4j
@RestController
@Api(tags = "固定资产报废管理接口")
@RequestMapping("/sspku/adminAssetsRetire")
@Transactional
public class AdminAssetsRetireController {

    @Autowired
    private IAdminAssetsRetireService iAdminAssetsRetireService;


    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IAdminAssetService iAdminAssetService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ApiOperation(value = "新增固定资产报修")
    public Result<Object> add(@RequestParam String assetId){
        AdminAsset aa = iAdminAssetService.getById(assetId);
        if(aa == null) {
            return ResultUtil.error("资产不存在");
        }
        aa.setStatus(5);
        iAdminAssetService.saveOrUpdate(aa); // 保存资产的状态更改
        AdminAssetsRetire aar = new AdminAssetsRetire();
        aar.setAssetId(aa.getId());
        aar.setAssetName(aa.getName());
        aar.setAssetType(aa.getGiveDepart());
        aar.setAssetWare(aa.getWarehouse());
        aar.setRetireFlag(1);

        User currUser = securityUtil.getCurrUser();
        aar.setRetireId(currUser.getId());
        aar.setRetireName(currUser.getNickname());
        iAdminAssetsRetireService.saveOrUpdate(aar);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条固定资产报修")
    public Result<AdminAssetsRetire> get(@RequestParam String id){
        return new ResultUtil<AdminAssetsRetire>().setData(iAdminAssetsRetireService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部固定资产报修个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iAdminAssetsRetireService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部固定资产报修")
    public Result<List<AdminAssetsRetire>> getAll(){
        return new ResultUtil<List<AdminAssetsRetire>>().setData(iAdminAssetsRetireService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询固定资产报废")
    public Result<IPage<AdminAssetsRetire>> getByPage(@ModelAttribute AdminAssetsRetire adminAssetsRetire ,@ModelAttribute PageVo page){
        QueryWrapper<AdminAssetsRetire> qw = new QueryWrapper<>();
        if(!sspkuNullUtils.isNull(adminAssetsRetire.getAssetName())) {
            qw.like("asset_name",adminAssetsRetire.getAssetName());
        }
        if(!sspkuNullUtils.isNull(adminAssetsRetire.getRetireName())) {
            qw.like("Retire_name",adminAssetsRetire.getRetireName());
        }

        // 只查询 RetireFlag 为 1 的条目
        //qw.eq("Retire_flag", 1);
        //qw.eq("Retire_id",securityUtil.getCurrUser().getId());
        IPage<AdminAssetsRetire> data = iAdminAssetsRetireService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<AdminAssetsRetire>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改固定资产报修")
    public Result<AdminAssetsRetire> saveOrUpdate(AdminAssetsRetire adminAssetsRetire){
        if(iAdminAssetsRetireService.saveOrUpdate(adminAssetsRetire)){
            return new ResultUtil<AdminAssetsRetire>().setData(adminAssetsRetire);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增固定资产报修")
    public Result<AdminAssetsRetire> insert(AdminAssetsRetire adminAssetsRetire){
        iAdminAssetsRetireService.saveOrUpdate(adminAssetsRetire);
        return new ResultUtil<AdminAssetsRetire>().setData(adminAssetsRetire);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑固定资产报修")
    public Result<AdminAssetsRetire> update(AdminAssetsRetire adminAssetsRetire){
        AdminAsset aa = iAdminAssetService.getById(adminAssetsRetire.getAssetId());
        aa.setStatus(1);
        iAdminAssetService.saveOrUpdate(aa); // 保存资产的状态更改
        iAdminAssetsRetireService.saveOrUpdate(adminAssetsRetire);
        return new ResultUtil<AdminAssetsRetire>().setData(adminAssetsRetire);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除固定资产报修")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iAdminAssetsRetireService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/submitRetireData", method = RequestMethod.POST)
    @ApiOperation(value = "申请资产报废")
    public Result<Object> submitRetireData(@RequestParam String[] ids){
        User user = securityUtil.getCurrUser();
        AdminAssetsRetire retire =null;
        for(String id : ids) {
            AdminAsset aa = iAdminAssetService.getById(id);
            //   if(assets.getApplyFlag() == 0) {
//            aa.setStatus(4);
            aa.setRetireApprovalStatus(1);
            iAdminAssetService.saveOrUpdate(aa);

            System.out.println("打印"+iAdminAssetsRetireService.getByAssetId(id));
            if(iAdminAssetsRetireService.getByAssetId(id) != null){
                retire = iAdminAssetsRetireService.getByAssetId(id);
            }
            else {
                retire = new AdminAssetsRetire();
            }
            retire.setAssetId(aa.getId());
            retire.setAssetName(aa.getName());
            retire.setAssetType(aa.getGiveDepart());
            retire.setAssetWare(aa.getWarehouse());
            retire.setRetireFlag(1);
            User currUser = securityUtil.getCurrUser();
            retire.setRetireId(currUser.getId());
            retire.setRetireName(currUser.getNickname());
            iAdminAssetsRetireService.saveOrUpdate(retire);
        }

        return ResultUtil.success();
    }

    @RequestMapping(value = "/retireApproval", method = RequestMethod.POST)
    @ApiOperation(value = "资产报废审核通过")
    public Result<Object> retireApprove(@RequestParam String[] ids){
        User user = securityUtil.getCurrUser();
        for(String id : ids){
            AdminAssetsRetire assets = iAdminAssetsRetireService.getById(id);
            AdminAsset aa = iAdminAssetService.getById(assets.getAssetId());
            if(assets != null) {
                aa.setRetireApprovalStatus(2);
                aa.setStatus(5);
                assets.setRetireFlag(2);


                iAdminAssetsRetireService.saveOrUpdate(assets);
                iAdminAssetService.saveOrUpdate(aa);
            }
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/retireDisapproval", method = RequestMethod.POST)
    @ApiOperation(value = "资产报废审核退回")
    public Result<Object> retireDisapprove(@RequestParam String[] ids){
        User user = securityUtil.getCurrUser();
        for(String id : ids){

            AdminAssetsRetire assets = iAdminAssetsRetireService.getById(id);
            if(assets != null) {
                assets.setRetireFlag(3);
                iAdminAssetsRetireService.saveOrUpdate(assets);
                AdminAsset aa = iAdminAssetService.getById(assets.getAssetId());
                aa.setRetireApprovalStatus(3);
                iAdminAssetService.saveOrUpdate(aa);
            }
        }
        return ResultUtil.success();
    }
}
