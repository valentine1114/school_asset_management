package cn.sspku.assets.controller;

import cn.hutool.core.date.DateUtil;
import cn.sspku.assets.dto.AdminAssetsRequestDto;
import cn.sspku.assets.entity.AdminAsset;
import cn.sspku.assets.entity.AdminAssets;
import cn.sspku.assets.entity.AdminAssetsRegister;
import cn.sspku.assets.entity.AdminAssetsRequest;
import cn.sspku.assets.service.IAdminAssetService;
import cn.sspku.assets.service.IAdminAssetsRequestService;
import cn.sspku.assets.service.IAdminAssetsService;
import cn.sspku.basics.baseVo.PageVo;
import cn.sspku.basics.baseVo.Result;
import cn.sspku.basics.log.LogType;
import cn.sspku.basics.log.SystemLog;
import cn.sspku.basics.utils.PageUtil;
import cn.sspku.basics.utils.ResultUtil;
import cn.sspku.basics.utils.SecurityUtil;
import cn.sspku.data.entity.Department;
import cn.sspku.data.entity.User;
import cn.sspku.data.service.IDepartmentService;
import cn.sspku.data.service.IUserService;
import cn.sspku.data.utils.sspkuNullUtils;
import cn.sspku.data.vo.AntvVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 开发者
 */
@RestController
@Api(tags = "行政资产库存管理接口")
@RequestMapping("/sspku/adminAssetsRequest")
@Transactional
public class AdminAssetsRequestController {

    @Autowired
    private IAdminAssetsRequestService iAdminAssetsRequestService;

    @Autowired
    private IAdminAssetsService iAdminAssetsService;
    @Autowired
    private IAdminAssetService iAdminAssetService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDepartmentService iDepartmentService;

    @RequestMapping(value = "/getByPageApply", method = RequestMethod.GET)
    @ApiOperation(value = "查询资产可申请库存")
    public Result<IPage<AdminAssets>> getByPageApply(@ModelAttribute AdminAsset asset, PageVo page){
        QueryWrapper<AdminAsset> qw = new QueryWrapper<>();
        // 检查 name 字段是否非空并设置查询条件
        if(!sspkuNullUtils.isNull(asset.getName())) {
            qw.like("name",asset.getName());
        }

        return new ResultUtil<IPage<AdminAsset>>().setData(iAdminAssetService.page(PageUtil.initMpPage(page),qw));

    }

    @RequestMapping(value = "/getByPageAuditApply", method = RequestMethod.GET)
    @ApiOperation(value = "查询资产审核申请库存")
    public Result<IPage<AdminAssetsRequestDto>> getByPageAuditApply(@ModelAttribute AdminAsset asset, PageVo page ){
        QueryWrapper<AdminAssetsRequest> qw = new QueryWrapper<>();
        // 检查 name 字段是否非空并设置查询条件
        if(!sspkuNullUtils.isNull(asset.getName())) {
            AdminAsset aa = iAdminAssetService.getByAssetName(asset.getName());
            qw.like("name",aa.getName());
        }

        IPage<AdminAssetsRequest> requestPage = iAdminAssetsRequestService.page(PageUtil.initMpPage(page), qw);

        // 提取 asset_id
        Set<String> assetIds = requestPage.getRecords().stream()
                .map(AdminAssetsRequest::getAssetId)
                .collect(Collectors.toSet());

        // 用 asset_id 查询 AdminAssets
        List<AdminAsset> assetsList = iAdminAssetService.listByIds(assetIds);

        // 提取 user_id 和 department_id
        Set<String> userIds = requestPage.getRecords().stream()
                .map(AdminAssetsRequest::getUserId)
                .collect(Collectors.toSet());
        Set<String> departmentIds = requestPage.getRecords().stream()
                .map(AdminAssetsRequest::getDepartmentId)
                .collect(Collectors.toSet());

        // 用 userIds 查询 User
        List<User> usersList = iUserService.listByIds(userIds);
        Map<String, String> userNicknames = usersList.stream()
                .collect(Collectors.toMap(User::getId, User::getNickname));

        // 用 departmentIds 查询 Department
        List<Department> departmentsList = iDepartmentService.listByIds(departmentIds);
        Map<String, String> departmentTitles = departmentsList.stream()
                .collect(Collectors.toMap(Department::getId, Department::getTitle));

        // 将 AdminAssets 按 asset_id 分组
        Map<String, AdminAsset> assetsMap = assetsList.stream()
                .collect(Collectors.toMap(AdminAsset::getId, Function.identity()));

        // 构建 DTO 并设置 AdminAssets 的 name
        IPage<AdminAssetsRequestDto> dtoPage = requestPage.convert(request -> {
            AdminAssetsRequestDto dto = new AdminAssetsRequestDto();
            BeanUtils.copyProperties(request, dto);
            if (assetsMap.containsKey(request.getAssetId())) {
                dto.setName(assetsMap.get(request.getAssetId()).getName());
                dto.setStatus(assetsMap.get(request.getAssetId()).getStatus());
            }
            // 设置用户昵称
            if (userNicknames.containsKey(request.getUserId())) {
                dto.setNickname(userNicknames.get(request.getUserId()));
            }
            // 设置部门标题
            if (departmentTitles.containsKey(request.getDepartmentId())) {
                dto.setTitle(departmentTitles.get(request.getDepartmentId()));
            }

            return dto;

        });

        return new ResultUtil<IPage<AdminAssetsRequestDto>>().setData(dtoPage);
    }
    @RequestMapping(value = "/submitApplyData", method = RequestMethod.POST)
    @ApiOperation(value = "申请资产领用")
    public Result<Object> submitApplyData(@RequestParam String[] ids){
        User user = securityUtil.getCurrUser();
        AdminAssetsRequest aar=null;
        for(String id : ids){

            AdminAsset asset = iAdminAssetService.getById(id);
            if(asset == null) {
                return ResultUtil.error("资产不存在");
            }
            if(iAdminAssetsRequestService.getByAssetId(id) != null){
                aar = iAdminAssetsRequestService.getByAssetId(id);
            }
            else {
              aar   = new AdminAssetsRequest();
            }
            asset.setRequestApprovalStatus(1);
            aar.setAssetId(asset.getId());
            aar.setUserId(user.getId());
            aar.setAuditFlag(1);
            aar.setDepartmentId(user.getDepartmentId());
            System.out.println(aar);
            iAdminAssetService.saveOrUpdate(asset);
            iAdminAssetsRequestService.saveOrUpdate(aar);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/returnData", method = RequestMethod.POST)
    @ApiOperation(value = "撤回资产登记申请")
    public Result<Object> returnData(@RequestParam String[] ids){
        for(String id : ids){
            AdminAssetsRequest request = iAdminAssetsRequestService.getByAssetId(id);
            AdminAsset asset= iAdminAssetService.getById(id);
            if(request.getAuditFlag() == 1) {

                request.setAuditFlag(0);
                asset.setRequestApprovalStatus(0);
                iAdminAssetsRequestService.saveOrUpdate(request);
                iAdminAssetService.saveOrUpdate(asset);
            }
        }
        return ResultUtil.success();
    }
    @RequestMapping(value = "/outWageApproval", method = RequestMethod.POST)
    @ApiOperation(value = "资产领用审批通过")
    public Result<Object> outWageApproval(@RequestParam String[] ids) {
        User user = securityUtil.getCurrUser();
        for (String id : ids) {
            AdminAsset asset = iAdminAssetService.getById(id);
            if (asset != null) {

                    //asset.setStatus(1);
                    AdminAssetsRequest aar = iAdminAssetsRequestService.getByAssetId(id);
                    aar.setAuditFlag(2);
                    aar.setApproverId(user.getId());
                    aar.setApprovalDate(DateUtil.now());
                    iAdminAssetsRequestService.saveOrUpdate(aar);
                    iAdminAssetService.saveOrUpdate(asset);

            }

        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/outWage", method = RequestMethod.POST)
    @ApiOperation(value = "资产出库")
    public Result<Object> outWage(@RequestParam String[] ids) {
        User user = securityUtil.getCurrUser();
        for (String id : ids) {
            AdminAsset asset = iAdminAssetService.getById(id);


            if (asset != null) {

                if (asset.getStatus() == 1) {
                    System.out.println("asset.get是"+asset.getStatus());
                    asset.setStatus(2);
                    AdminAssetsRequest aar = iAdminAssetsRequestService.getByAssetId(id);

                    asset.setUser(iUserService.getById(aar.getUserId()).getNickname());
                    asset.setUserId(aar.getUserId());
                    asset.setGiveDepart(iDepartmentService.getById(aar.getDepartmentId()).getTitle());
                    asset.setGiveDepartId(aar.getDepartmentId());
                    //iAdminAssetsRequestService.saveOrUpdate(aar);
                    iAdminAssetService.saveOrUpdate(asset);
                }
            }

        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/outWageDisapproval", method = RequestMethod.POST)
    @ApiOperation(value = "资产领用审核退回")
    public Result<Object> outWageDisapproval(@RequestParam String[] ids){
        User user = securityUtil.getCurrUser();
        for(String id : ids) {
            AdminAsset asset = iAdminAssetService.getById(id);
            if (asset != null) {

                   // asset.setStatus(1);
                    asset.setRequestApprovalStatus(3);
                    AdminAssetsRequest aar = iAdminAssetsRequestService.getByAssetId(id);
                    aar.setAuditFlag(3);
                    aar.setApproverId(user.getId());
                    aar.setApprovalDate(DateUtil.now());
                    iAdminAssetsRequestService.saveOrUpdate(aar);
                    iAdminAssetService.saveOrUpdate(asset);

            }
        }
        return ResultUtil.success();
    }


}
