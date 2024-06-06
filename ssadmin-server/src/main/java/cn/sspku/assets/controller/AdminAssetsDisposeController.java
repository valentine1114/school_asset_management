package cn.sspku.assets.controller;

import cn.sspku.assets.entity.AdminAsset;
import cn.sspku.assets.entity.AdminAssets;
import cn.sspku.assets.entity.AdminAssetsDispose;
import cn.sspku.assets.entity.AdminAssetsRetire;
import cn.sspku.assets.service.IAdminAssetService;
import cn.sspku.assets.service.IAdminAssetsDisposeService;
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
@RequestMapping("/sspku/adminAssetsDispose")
@Transactional
public class AdminAssetsDisposeController {

    @Autowired
    private IAdminAssetsDisposeService iAdminAssetsDisposeService;


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
        aa.setStatus(4);
        iAdminAssetService.saveOrUpdate(aa); // 保存资产的状态更改
        AdminAssetsDispose aar = new AdminAssetsDispose();
        aar.setAssetId(aa.getId());
        aar.setAssetName(aa.getName());
        aar.setAssetType(aa.getGiveDepart());
        aar.setAssetWare(aa.getWarehouse());
        aar.setDisposeFlag(1);

        User currUser = securityUtil.getCurrUser();
        aar.setDisposeId(currUser.getId());
        aar.setDisposeName(currUser.getNickname());
        iAdminAssetsDisposeService.saveOrUpdate(aar);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条固定资产报修")
    public Result<AdminAssetsDispose> get(@RequestParam String id){
        return new ResultUtil<AdminAssetsDispose>().setData(iAdminAssetsDisposeService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部固定资产报修个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iAdminAssetsDisposeService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部固定资产报修")
    public Result<List<AdminAssetsDispose>> getAll(){
        return new ResultUtil<List<AdminAssetsDispose>>().setData(iAdminAssetsDisposeService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询固定资产报废")
    public Result<IPage<AdminAssetsDispose>> getByPage(@ModelAttribute AdminAssetsDispose adminAssetsDispose ,@ModelAttribute PageVo page){
        QueryWrapper<AdminAssetsDispose> qw = new QueryWrapper<>();
        if(!sspkuNullUtils.isNull(adminAssetsDispose.getAssetName())) {
            qw.like("asset_name",adminAssetsDispose.getAssetName());
        }
        if(!sspkuNullUtils.isNull(adminAssetsDispose.getDisposeName())) {
            qw.like("dispose_name",adminAssetsDispose.getDisposeName());
        }

        // 只查询 DisposeFlag 为 1 的条目
        //qw.eq("Dispose_flag", 1);
        //qw.eq("Dispose_id",securityUtil.getCurrUser().getId());
        IPage<AdminAssetsDispose> data = iAdminAssetsDisposeService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<AdminAssetsDispose>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改固定资产报修")
    public Result<AdminAssetsDispose> saveOrUpdate(AdminAssetsDispose adminAssetsDispose){
        if(iAdminAssetsDisposeService.saveOrUpdate(adminAssetsDispose)){
            return new ResultUtil<AdminAssetsDispose>().setData(adminAssetsDispose);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增固定资产报修")
    public Result<AdminAssetsDispose> insert(AdminAssetsDispose adminAssetsDispose){
        iAdminAssetsDisposeService.saveOrUpdate(adminAssetsDispose);
        return new ResultUtil<AdminAssetsDispose>().setData(adminAssetsDispose);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑固定资产报修")
    public Result<AdminAssetsDispose> update(AdminAssetsDispose adminAssetsDispose){
        AdminAsset aa = iAdminAssetService.getById(adminAssetsDispose.getAssetId());
        aa.setStatus(4);
        iAdminAssetService.saveOrUpdate(aa); // 保存资产的状态更改
        iAdminAssetsDisposeService.saveOrUpdate(adminAssetsDispose);
        return new ResultUtil<AdminAssetsDispose>().setData(adminAssetsDispose);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除固定资产报修")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iAdminAssetsDisposeService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/submitDisposeData", method = RequestMethod.POST)
    @ApiOperation(value = "申请资产报废")
    public Result<Object> submitDisposeData(@RequestParam String[] ids){
        User user = securityUtil.getCurrUser();
        AdminAssetsDispose dispose = null;
        for(String id : ids){
            AdminAsset aa = iAdminAssetService.getById(id);
            //   if(assets.getApplyFlag() == 0) {

            aa.setDisposeApprovalStatus(1);
            iAdminAssetService.saveOrUpdate(aa);
            if(iAdminAssetsDisposeService.getByAssetId(id) != null){
                dispose = iAdminAssetsDisposeService.getByAssetId(id);
            }
            else {
                dispose = new AdminAssetsDispose();
            }

            dispose.setAssetId(aa.getId());
           dispose.setAssetName(aa.getName());
            dispose.setAssetType(aa.getGiveDepart());
            dispose.setAssetWare(aa.getWarehouse());
            dispose.setDisposeFlag(1);


            User currUser = securityUtil.getCurrUser();
            dispose.setDisposeId(currUser.getId());
            dispose.setDisposeName(currUser.getNickname());
            iAdminAssetsDisposeService.saveOrUpdate(dispose);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/disposeApproval", method = RequestMethod.POST)
    @ApiOperation(value = "资产报废审核通过")
    public Result<Object> DisposeApprove(@RequestParam String[] ids){
        User user = securityUtil.getCurrUser();
        for(String id : ids){
            AdminAssetsDispose assets = iAdminAssetsDisposeService.getById(id);
            AdminAsset aa = iAdminAssetService.getById(assets.getAssetId());
            if(assets != null) {
                aa.setDisposeApprovalStatus(2);
                aa.setStatus(4);
                assets.setDisposeFlag(2);


                iAdminAssetsDisposeService.saveOrUpdate(assets);
                iAdminAssetService.saveOrUpdate(aa);
            }
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/disposeDisapproval", method = RequestMethod.POST)
    @ApiOperation(value = "资产报废审核退回")
    public Result<Object> DisposeDisapprove(@RequestParam String[] ids){
        User user = securityUtil.getCurrUser();
        for(String id : ids){

            AdminAssetsDispose assets = iAdminAssetsDisposeService.getById(id);
            if(assets != null) {
                assets.setDisposeFlag(3);
                iAdminAssetsDisposeService.saveOrUpdate(assets);
                AdminAsset aa = iAdminAssetService.getById(assets.getAssetId());
                aa.setDisposeApprovalStatus(3);
                iAdminAssetService.saveOrUpdate(aa);
            }
        }
        return ResultUtil.success();
    }
}
