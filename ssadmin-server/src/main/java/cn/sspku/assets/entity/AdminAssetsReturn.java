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

/**
 * @author 开发者
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_admin_assets_return")
@TableName("a_admin_assets_return")
@ApiModel(value = "固定资产退库")
public class AdminAssetsReturn extends sspkuBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "固定资产ID")
    private String assetId;

    @ApiModelProperty(value = "固定资产名称")
    private String assetName;

    @ApiModelProperty(value = "资产分类")
    private String assetType;

    @ApiModelProperty(value = "资产仓库")
    private String assetWare;

    @ApiModelProperty(value = "报废人ID")
    private String returnId;

    @ApiModelProperty(value = "报废人")
    private String returnName;

    @ApiModelProperty(value = "是否报废")
    private int returnFlag;

}