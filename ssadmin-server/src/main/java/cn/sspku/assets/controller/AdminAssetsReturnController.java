package cn.sspku.assets.controller;

import cn.sspku.assets.entity.*;
import cn.sspku.assets.service.IAdminAssetService;
import cn.sspku.assets.service.IAdminAssetsRequestService;
import cn.sspku.assets.service.IAdminAssetsReturnService;
import cn.sspku.basics.baseVo.PageVo;
import cn.sspku.basics.baseVo.Result;
import cn.sspku.basics.log.LogType;
import cn.sspku.basics.log.SystemLog;
import cn.sspku.basics.utils.PageUtil;
import cn.sspku.basics.utils.ResultUtil;
import cn.sspku.basics.utils.SecurityUtil;
import cn.sspku.data.entity.User;
import cn.sspku.data.service.IDepartmentService;
import cn.sspku.data.service.IUserService;
import cn.sspku.data.utils.sspkuNullUtils;
import cn.sspku.data.vo.AntvVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 开发者
 */
@RestController
@Api(tags = "行政资产库存管理接口")
@RequestMapping("/sspku/adminAssetsReturn")
@Transactional
public class AdminAssetsReturnController {

    @Autowired
    private IAdminAssetService iAdminAssetService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDepartmentService iDepartmentService;
    @Autowired
    private IAdminAssetsRequestService iAdminAssetsRequestService;

    @Autowired
    private IAdminAssetsReturnService iAdminAssetsReturnService;


    @SystemLog(about = "查询图表数据部门分类", type = LogType.CHART,doType = "CHART-01")
    @RequestMapping(value = "/getAntvVoListByDepartment", method = RequestMethod.GET)
    @ApiOperation(value = "查询图表数据")
    public Result<List<AntvVo>> getAntvVoListByDepartment(){
        List<AntvVo> ansList = new ArrayList<>();
        List<AdminAsset> assetsList = iAdminAssetService.list();
        for (AdminAsset o : assetsList) {
            boolean flag = false;
            for (AntvVo vo : ansList) {
                if(Objects.equals(vo.getTitle(),o.getGiveDepart())) {
                    flag = true;
                    vo.setValue(vo.getValue().add(o.getPrice()));
                    break;
                }
            }
            if(!flag) {
                AntvVo vo = new AntvVo();
                vo.setTitle(o.getGiveDepart());
                vo.setType("固定资产金额");
                vo.setValue(o.getPrice());
                ansList.add(vo);
            }
        }
        return new ResultUtil<List<AntvVo>>().setData(ansList);
    }

    @SystemLog(about = "查询图表数据仓库分类", type = LogType.CHART,doType = "CHART-01")
    @RequestMapping(value = "/getAntvVoListByWarehouse", method = RequestMethod.GET)
    @ApiOperation(value = "查询图表数据")
    public Result<List<AntvVo>> getAntvVoListByWarehouse(){
        List<AntvVo> ansList = new ArrayList<>();
        List<AdminAsset> assetsList = iAdminAssetService.list();
        for (AdminAsset o : assetsList) {
            boolean flag = false;
            for (AntvVo vo : ansList) {
                if(Objects.equals(vo.getTitle(),o.getWarehouse())) {
                    flag = true;
                    vo.setValue(vo.getValue().add(o.getPrice()));
                    break;
                }
            }
            if(!flag) {
                AntvVo vo = new AntvVo();
                vo.setTitle(o.getWarehouse());
                vo.setType("固定资产金额");
                vo.setValue(o.getPrice());
                ansList.add(vo);
            }
        }
        return new ResultUtil<List<AntvVo>>().setData(ansList);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询资产库存")
    public Result<IPage<AdminAsset>> getByPage(@ModelAttribute AdminAsset asset, PageVo page){
        QueryWrapper<AdminAsset> qw = new QueryWrapper<>();
        // 检查 name 字段是否非空并设置查询条件
        if(!sspkuNullUtils.isNull(asset.getName())) {
            qw.like("name",asset.getName());
        }
        if(!sspkuNullUtils.isNull(asset.getUser())) {
            qw.eq("user",asset.getUser());
        }
//        IPage<AdminAssets> data = iAdminAssetsService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<AdminAsset>>().setData(iAdminAssetService.page(PageUtil.initMpPage(page),qw));

//        return new ResultUtil<IPage<AdminAssets>>().setData(data);
    }
    @RequestMapping(value = "/getByPageSearch", method = RequestMethod.GET)
    @ApiOperation(value = "查询资产库存")
    public Result<IPage<AdminAsset>> getByPageSearch(@ModelAttribute AdminAsset asset, PageVo page, String tableType){
        QueryWrapper<AdminAsset> qw = new QueryWrapper<>();

        // 检查 name 字段是否非空并设置查询条件
        if (!sspkuNullUtils.isNull(asset.getName())) {
            qw.like("name", asset.getName());
        }
        // 检查 giveName 字段是否非空并设置查询条件
        if (!sspkuNullUtils.isNull(asset.getUser())) {
            qw.eq("user", asset.getUser());
        }
        if (!sspkuNullUtils.isNull(asset.getWarehouse())) {
            qw.eq("warehouse", asset.getWarehouse());
        }
        if (!sspkuNullUtils.isNull(asset.getGiveDepart())) {
            qw.eq("give_depart", asset.getGiveDepart());
        }
// 根据tableType来调整查询条件
        if (tableType != null) {
            switch (tableType) {
                case "inventory":
                    // 盘点表的查询条件
                    qw.ne("status",4);
                    qw.ne("status",5);
                    break;
                case "idle":
                    // 闲置表的查询条件
                    qw.eq("status", 1);
                    break;
                case "scrap":
                    // 报废表的查询条件
                    qw.eq("status", 5);
                    break;
                case "disposal":
                    // 处置表的查询条件
                    qw.eq("status", 4);
                    break;

                default:
                    // 默认情况或无效的tableType
                    break;
            }
        }


        return new ResultUtil<IPage<AdminAsset>>().setData(iAdminAssetService.page(PageUtil.initMpPage(page), qw));
    }



    @RequestMapping(value = "/getByPageMylist", method = RequestMethod.GET)
    @ApiOperation(value = "查询我的资产库存")
    public Result<IPage<AdminAsset>> getByPageReturn(@ModelAttribute AdminAsset asset, PageVo page){
        QueryWrapper<AdminAsset> qw = new QueryWrapper<>();
        // 检查 name 字段是否非空并设置查询条件
        if(!sspkuNullUtils.isNull(asset.getName())) {
            qw.like("name",asset.getName());
        }
        // 添加额外的过滤条件
       // qw.eq("out_flag", 1);
        User user = securityUtil.getCurrUser();
        qw.eq("user", user.getNickname());

        return new ResultUtil<IPage<AdminAsset>>().setData(iAdminAssetService.page(PageUtil.initMpPage(page),qw));

    }

    @RequestMapping(value = "/getByPageAuditReturn", method = RequestMethod.GET)
    @ApiOperation(value = "查询资产审核退库库存")
    public Result<IPage<AdminAssetsReturn>> getByPageAuditReturn(@ModelAttribute AdminAssetsReturn asset, PageVo page){
        QueryWrapper<AdminAssetsReturn> qw = new QueryWrapper<>();
        // 检查 name 字段是否非空并设置查询条件
        if(!sspkuNullUtils.isNull(asset.getAssetName())) {
            qw.like("asset_name",asset.getAssetName());
        }
        // 添加额外的过滤条件
       // qw.eq("return_flag", 1);
//        qw.eq("return_approval_status", 1);

//        IPage<AdminAssets> data = iAdminAssetsService.page(PageUtil.initMpPage(page));
        IPage<AdminAssetsReturn> data = iAdminAssetsReturnService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<AdminAssetsReturn>>().setData(data);

//        return new ResultUtil<IPage<AdminAssets>>().setData(data);
    }




    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改资产库存")
    public Result<AdminAsset> saveOrUpdate(AdminAsset adminAsset){
        if(iAdminAssetService.saveOrUpdate(adminAsset)){
            return new ResultUtil<AdminAsset>().setData(adminAsset);
        }
        return ResultUtil.error();
    }

//    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
//    @ApiOperation(value = "销毁资产")
//    public Result<Object> delAllByIds(@RequestParam String[] ids){
//        User user = securityUtil.getCurrUser();
//        for(String id : ids){
//            AdminAsset asset = iAdminAssetService.getById(id);
//            if(asset != null) {
//                asset.setStatus(1);
//                asset.setDestroyName(user.getNickname());
//                asset.setDestroyTime(DateUtil.now());
//                iAdminAssetsService.saveOrUpdate(assets);
//            }
//        }
//        return ResultUtil.success();
//    }


    @RequestMapping(value = "/returnReturn", method = RequestMethod.POST)
    @ApiOperation(value = "资产退库审核退回")
    public Result<Object> returnReturn(@RequestParam String[] ids){
        User user = securityUtil.getCurrUser();
        for(String id : ids){
            AdminAssetsReturn assets = iAdminAssetsReturnService.getById(id);
            if(assets != null) {
                assets.setReturnFlag(3);
                iAdminAssetsReturnService.saveOrUpdate(assets);
                AdminAsset aa = iAdminAssetService.getById(assets.getAssetId());
                aa.setReturnApprovalStatus(3);
                iAdminAssetService.saveOrUpdate(aa);
            }

        }
        return ResultUtil.success();
    }
    @RequestMapping(value = "/returnWage", method = RequestMethod.POST)
    @ApiOperation(value = "资产退库")
    public Result<Object> returnWage(@RequestParam String[] ids){
        User user = securityUtil.getCurrUser();
        for(String id : ids){
            AdminAssetsReturn assets = iAdminAssetsReturnService.getById(id);
            AdminAsset asset = iAdminAssetService.getById(assets.getAssetId());
            if(asset != null) {
                asset.setGiveDepart("");
                asset.setUserId("");
                asset.setUser("");
                asset.setStatus(1);

                asset.setReturnApprovalStatus(2);
assets.setReturnFlag(2);
                iAdminAssetService.saveOrUpdate(asset);
                iAdminAssetsReturnService.saveOrUpdate(assets);
            }
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/submitReturnData", method = RequestMethod.POST)
    @ApiOperation(value = "申请资产退库")
    public Result<Object> submitReturnData(@RequestParam String[] ids){
        User user = securityUtil.getCurrUser();
        AdminAssetsReturn a_return =null;


        for(String id : ids){
            AdminAsset asset = iAdminAssetService.getById(id);
          //  if(assets.getApplyFlag() == 0) {

                asset.setReturnApprovalStatus(1);
            if(iAdminAssetsReturnService.getByAssetId(id) != null){
                a_return = iAdminAssetsReturnService.getByAssetId(id);
            }
            else {
                a_return = new AdminAssetsReturn();}
                a_return.setAssetId(id);
                a_return.setAssetWare(asset.getWarehouse());
                a_return.setAssetType(asset.getType());
                a_return.setReturnId(user.getId());
a_return.setReturnName(user.getNickname());
a_return.setAssetName(asset.getName());
                a_return.setReturnName(user.getNickname());
            a_return.setReturnFlag(1);


                iAdminAssetService.saveOrUpdate(asset);
            iAdminAssetsReturnService.saveOrUpdate(a_return);
           // }
        }
        return ResultUtil.success();
    }

}