package cn.sspku.data.serviceimpl;

import cn.sspku.data.dao.mapper.RoleMapper;
import cn.sspku.data.entity.Role;
import cn.sspku.data.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色 服务层实现
 * @author 
 */
@Service
public class IRoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
