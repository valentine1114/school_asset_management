package cn.sspku.data.entity;

import cn.sspku.basics.baseClass.sspkuBaseEntity;
import cn.sspku.basics.parameter.CommonConstant;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 
 */
@Data
@Accessors(chain = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "a_department_header")
@TableName("a_department_header")
@ApiModel(value = "部门负责人")
public class DepartmentHeader extends sspkuBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "部门ID")
    private String departmentId;

    @ApiModelProperty(value = "领导类型")
    private Integer type = 0;
}