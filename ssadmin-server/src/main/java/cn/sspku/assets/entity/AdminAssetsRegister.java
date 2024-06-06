package cn.sspku.assets.entity;

import cn.sspku.basics.baseClass.sspkuBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 开发者
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_admin_assets_register")
@TableName("a_admin_assets_register")
@ApiModel(value = "行政资产采购")
public class AdminAssetsRegister extends sspkuBaseEntity {

    private static final long serialVersionUID = 1L;

    //资产名称应根据产品说明书或合同明细清单等规范填写,应填写完整的中文名称，不可简写或缩写，如有外文名称可在备注栏填写。
    //同时，大型科研仪器应根据仪器的工作原理、主要配置、技术指标等关键信息进行命名。
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "存放仓库")
    private String warehouse;


    @ApiModelProperty(value = "资产ID")
    private String assetId;


    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "是否审核")
    private int auditFlag;

//    @ApiModelProperty(value = "是否提交")
//    private int submitFlag;
//
//    @ApiModelProperty(value = "是否入库")
//    private int wareFlag;
}