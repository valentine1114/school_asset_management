package cn.sspku.data.serviceimpl;

import cn.sspku.data.dao.mapper.RolePermissionMapper;
import cn.sspku.data.entity.RolePermission;
import cn.sspku.data.service.IRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色-菜单关系 服务层实现
 * @author
 */
@Service
public class IRolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

}
