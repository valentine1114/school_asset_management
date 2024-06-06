package cn.sspku.assets.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class AdminAssetsRequestDto {

    @ApiModelProperty(value = "姓名")
    @NotNull(message = "姓名不能为空")
    @Size(max = 20, message = "姓名长度不能超过20")
    private String nickname;

    @ApiModelProperty(value = "部门标题")
    private String title;

    @ApiModelProperty(value = "领用人ID")
    private String userId;


//    @ApiModelProperty(value = "审批状态")
//    private int requestStatus;

    @ApiModelProperty(value = "资产ID")
    private String assetId;

    @ApiModelProperty(value = "审批人ID")
    private String approverId;

    @ApiModelProperty(value = "领用人部门ID")
    private String departmentId;

    @ApiModelProperty(value = "审批日期")
    private String approvalDate;
    @ApiModelProperty(value = "资产名称")
    private String name;

//    @ApiModelProperty(value = "提交状态")
//    private int submitStatus;

    public int getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(int auditFlag) {
        this.auditFlag = auditFlag;
    }

    @ApiModelProperty(value = "审批状态")
    private int auditFlag;

    @ApiModelProperty(value = "资产状态")
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
