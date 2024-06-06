package cn.sspku.data.vo;

import lombok.Data;

@Data
public class UserByPermissionVo {
    private String userId;
    private String userName;
    private String roleStr;
    private String code;
    private String mobile;
}
