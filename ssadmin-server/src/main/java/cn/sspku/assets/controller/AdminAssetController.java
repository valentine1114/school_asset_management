package cn.sspku.assets.controller;

import cn.sspku.assets.entity.AdminAsset;
import cn.sspku.assets.entity.AdminAssetSupplier;
import cn.sspku.assets.entity.AdminAssetWare;
import cn.sspku.assets.service.IAdminAssetService;
import cn.sspku.assets.service.IAdminAssetSupplierService;
import cn.sspku.assets.service.IAdminAssetWareService;
import cn.sspku.basics.baseVo.PageVo;
import cn.sspku.basics.baseVo.Result;
import cn.sspku.basics.utils.PageUtil;
import cn.sspku.basics.utils.QRCodeUtil;
import cn.sspku.basics.utils.ResultUtil;
import cn.sspku.data.entity.File;
import cn.sspku.data.entity.Setting;
import cn.sspku.data.service.IFileService;
import cn.sspku.data.service.ISettingService;
import cn.sspku.data.utils.sspkuNullUtils;
import cn.sspku.data.vo.OssSettingVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;


import java.util.AbstractMap.SimpleEntry;
import org.springframework.beans.factory.annotation.Value;
/**
 * @author 开发者
 */
@RestController
@Api(tags = "行政资产档案")
@RequestMapping("/sspku/adminAsset")
@Transactional
public class AdminAssetController {

    @Autowired
    private IAdminAssetService iAdminAssetService;

    @Autowired
    private IAdminAssetWareService iAdminAssetWareService;

    @Autowired
    private IAdminAssetSupplierService iAdminAssetSupplierService;
    @Autowired
    private ISettingService iSettingService;
    @Autowired
    private IFileService iFileService;
    @Value("${custom.baseUrl}")
    private String baseUrl;


    private static final Map<String, String> COLUMN_MAPPING = Map.ofEntries(
            new SimpleEntry<>("名称", "name"),
            new SimpleEntry<>("类别", "type"),
            new SimpleEntry<>("固定资产编码", "code"),
            new SimpleEntry<>("购置日期", "date"),
            new SimpleEntry<>("序列号", "serialNumber"),
            new SimpleEntry<>("采购组织形式", "purchasingForm"),
            new SimpleEntry<>("采购合同编码", "purchaseContractCode"),
            new SimpleEntry<>("折旧方法", "depreciationMethod"),
            new SimpleEntry<>("累计折旧", "accumulatedDepreciation"),
            new SimpleEntry<>("净值", "netValue"),
            new SimpleEntry<>("存放仓库", "warehouse"),
            new SimpleEntry<>("不含税价", "price"),
            new SimpleEntry<>("含税价", "taxPrice"),
            new SimpleEntry<>("供应商", "supplier"),
            new SimpleEntry<>("状态", "status"),
            new SimpleEntry<>("备注", "remark")
    );


    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询资产种类")
    public Result<IPage<AdminAsset>> getByPage(@ModelAttribute AdminAsset asset, @ModelAttribute PageVo page) {
        QueryWrapper<AdminAsset> qw = new QueryWrapper<>();
        if(!sspkuNullUtils.isNull(asset.getType())) {
            qw.eq("type",asset.getType());
        }
        if(!sspkuNullUtils.isNull(asset.getName())) {
            qw.like("name",asset.getName());
        }
        IPage<AdminAsset> data = iAdminAssetService.page(PageUtil.initMpPage(page),qw);
        for (AdminAsset adminAsset : data.getRecords()) {
            String supplierId = adminAsset.getSupplierId();
            String warehouse = adminAsset.getWarehouse();
            if(supplierId != null) {
                AdminAssetSupplier aas = iAdminAssetSupplierService.getById(supplierId);
                if(aas != null) {
                    adminAsset.setSupplier(aas.getName());
                }
            }
            if(warehouse != null) {
                AdminAssetWare aaw = iAdminAssetWareService.getById(warehouse);
                if(aaw != null) {
                    adminAsset.setWarehouse(aaw.getName());
                }
            }
        }
        return new ResultUtil<IPage<AdminAsset>>().setData(data);
    }
//根据assetId查询资产档案
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查询资产档案")
    public Result<AdminAsset> getById(@RequestParam String id){
        AdminAsset adminAsset = iAdminAssetService.getById(id);

        return new ResultUtil<AdminAsset>().setData(adminAsset);
    }
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增资产档案")
    public Result<AdminAsset> insert(AdminAsset adminAsset){
        if(iAdminAssetService.saveOrUpdate(adminAsset)){
            return new ResultUtil<AdminAsset>().setData(adminAsset);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "修改资产档案")
    public Result<AdminAsset> update(AdminAsset adminAsset){

        if(iAdminAssetService.saveOrUpdate(adminAsset)){
            return new ResultUtil<AdminAsset>().setData(adminAsset);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除资产种类")
    public Result<Object> delAllByIds(@RequestParam String[] ids){
        for(String id : ids){
            iAdminAssetService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/generateQRCode", method = RequestMethod.POST)
    public Result<AdminAsset> createQRCode(@RequestParam String id) {
        AdminAsset adminAsset = iAdminAssetService.getById(id);
        String content = convertAssetInfoToString(adminAsset); // 转换adminAsset为二维码内容
        String relativePath = QRCodeUtil.createCodeSaveToFile(content, id);
        if (relativePath == null) {
            return ResultUtil.error("二维码生成失败");
        }
        String fullPath = baseUrl + relativePath;
        File f = new File();
        try {
            f.setUrl(fullPath);
            iFileService.saveOrUpdate(f);
            adminAsset.setQrCode(fullPath);
            iAdminAssetService.saveOrUpdate(adminAsset);
        } catch (Exception e) {
            return ResultUtil.error(e.toString());
        }
        return new ResultUtil<AdminAsset>().setData(adminAsset);
    }

    @PostMapping("/uploadExcel")
    @ApiOperation(value = "批量上传并解析Excel文件")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResultUtil.error("文件为空");
        }
        System.out.println("Received file: " + file.getOriginalFilename() + " size: " + file.getSize());
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            List<AdminAsset> assets = new ArrayList<>();
            Map<Integer, String> indexToColumnName = new HashMap<>();

            // First, map the column indexes to field names based on header row
            Row headerRow = sheet.getRow(0);
            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().trim();
                if (COLUMN_MAPPING.containsKey(header)) {
                    indexToColumnName.put(cell.getColumnIndex(), COLUMN_MAPPING.get(header));
                }
            }

            // Process data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                AdminAsset adminAsset = new AdminAsset();

                for (Integer index : indexToColumnName.keySet()) {
                    Cell cell = row.getCell(index);
                    String columnName = indexToColumnName.get(index);
                    if (columnName.equals("date") && cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                        LocalDate date = cell.getLocalDateTimeCellValue().toLocalDate();
                        BeanUtils.setProperty(adminAsset, columnName, date);
                    } else if (cell != null) {
                        String fieldValue = cell.toString();
                        BeanUtils.setProperty(adminAsset, columnName, fieldValue);
                    }
                }

                assets.add(adminAsset);
            }

            // Batch save or update the assets
            iAdminAssetService.saveOrUpdateBatch(assets);
            workbook.close();
            return ResultUtil.success("上传并解析成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("解析失败");
        }
    }





    private String convertAssetInfoToString(AdminAsset adminAsset) {
        // 实现将AssetInfo转换为字符串的逻辑
        return adminAsset.getName() + "," + adminAsset.getType() + "," + adminAsset.getCode()+","+adminAsset.getSupplier()+","+adminAsset.getWarehouse()+ "," +adminAsset.getUser()+ "," +adminAsset.getGiveDepart()+ "," +adminAsset.getStatus();
    }

    public OssSettingVo getOssSetting() {
        Setting s1 = iSettingService.getById("FILE_VIEW");
        Setting s2 = iSettingService.getById("FILE_HTTP");
        Setting s3 = iSettingService.getById("FILE_PATH");
        if(s1 == null || s1 == null || s1 == null) {
            return null;
        }
        return new OssSettingVo(s1.getValue(),s2.getValue(),s3.getValue());
    }


}
