package cn.sspku.assets.entity;

import cn.sspku.basics.baseClass.sspkuBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author 开发者
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_admin_assets_requests")
@TableName("a_admin_assets_requests")
@ApiModel(value = "领用申请")
public class AdminAssetsRequest extends sspkuBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "领用人ID")
    private String userId;


    @ApiModelProperty(value = "审批状态")
    private int auditFlag;

    @ApiModelProperty(value = "资产ID")
    private String assetId;

    @ApiModelProperty(value = "审批人ID")
    private String approverId;

    @ApiModelProperty(value = "审批部门ID")
    private String departmentId;

    @ApiModelProperty(value = "审批日期")
    private String approvalDate;


}