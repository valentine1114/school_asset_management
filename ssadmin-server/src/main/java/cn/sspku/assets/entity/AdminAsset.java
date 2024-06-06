package cn.sspku.assets.entity;

import cn.sspku.basics.baseClass.sspkuBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author 开发者
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_admin_asset")
@TableName("a_admin_asset")
@ApiModel(value = "行政资产档案")
public class AdminAsset extends sspkuBaseEntity {

    private static final long serialVersionUID = 1L;

    //资产名称应根据产品说明书或合同明细清单等规范填写,应填写完整的中文名称，不可简写或缩写，如有外文名称可在备注栏填写。
    //同时，大型科研仪器应根据仪器的工作原理、主要配置、技术指标等关键信息进行命名。
    @ApiModelProperty(value = "名称")
    private String name;

    //土地、 房屋及构筑物、
    // 通用设备、
    // 专用设备、
    // 文物和陈列品、
    // 图书档案、
    // 家具、 用具、 装具及动植物
    @ApiModelProperty(value = "类别")
    private String type;

    @ApiModelProperty(value = "固定资产编码")
    private String code;

    @ApiModelProperty(value = "序列号")
    private String serialNumber;

    //发票右上角处开票日期
    @ApiModelProperty(value = "购置日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    //一般选择“分散采购”，通过政府集中采购目录申请的则选择“政府批量集中采购”
    @ApiModelProperty(value = "采购组织形式")
    private String purchasingForm;


    @ApiModelProperty(value = "采购合同编码")
    private String purchaseContractCode;

    //所采用的折旧方法，如直线法、加速折旧法等。
    @ApiModelProperty(value = "折旧方法")
    private String depreciationMethod;

    //累计折旧：至某一日期为止，固定资产累计折旧的金额。
    @ApiModelProperty(value = "累计折旧")
    private BigDecimal accumulatedDepreciation;

    //净值：固定资产的账面价值，即购置成本减去累计折旧。
    @ApiModelProperty(value = "净值")
    private BigDecimal netValue;

    @ApiModelProperty(value = "存放仓库")
    private String warehouse;

    @ApiModelProperty(value = "不含税价")
    private BigDecimal price;

    @ApiModelProperty(value = "含税价")
    private BigDecimal taxPrice;

    @ApiModelProperty(value = "供应商Id")
    private String supplierId;

    @ApiModelProperty(value = "供应商")
    private String supplier;

    @ApiModelProperty(value = "使用部门ID")
    private String giveDepartId;

    @ApiModelProperty(value = "使用部门")
    private String giveDepart;

    @ApiModelProperty(value = "使用人ID")
    private String userId;

    @ApiModelProperty(value = "使用人")
    private String user;

    @ApiModelProperty(value = "二维码编号")
    private String qrCode;

    @ApiModelProperty(value = "物料图片")
    private String imageUrl;


    //（0，'待审批'）、（1，'审批中'）、（2，'已批准'），或（3，'未通过'）
    @ApiModelProperty(value = "审批状态")
    private int registerApprovalStatus;

    @ApiModelProperty(value = "领用审批状态")
    private int requestApprovalStatus;

    @ApiModelProperty(value = "退库审批状态")
    private int returnApprovalStatus;

    @ApiModelProperty(value = "报废审批状态")
    private int retireApprovalStatus;

    @ApiModelProperty(value = "处置审批状态")
    private int disposeApprovalStatus;

    @ApiModelProperty(value = "维修审批状态")
    private int repairApprovalStatus;

    //（0，'未入库'），(1, '闲置')，(2, '使用')，(3, '维修')，(4, '处置')，(5, '报废')
    @ApiModelProperty(value = "资产状态")
    private int status;

    @ApiModelProperty(value = "备注")
    private String remark;



}