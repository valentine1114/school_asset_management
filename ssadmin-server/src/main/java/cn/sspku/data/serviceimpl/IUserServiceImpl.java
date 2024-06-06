package cn.sspku.data.serviceimpl;

import cn.sspku.data.dao.mapper.UserMapper;
import cn.sspku.data.entity.User;
import cn.sspku.data.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户 服务层实现
 * @author
 */
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
