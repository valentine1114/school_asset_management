package cn.sspku.data.serviceimpl;

import cn.sspku.data.dao.mapper.UserRoleMapper;
import cn.sspku.data.entity.UserRole;
import cn.sspku.data.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户-角色关系 服务层实现
 * @author
 */
@Service
public class IUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
