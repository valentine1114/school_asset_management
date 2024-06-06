package cn.sspku.data.serviceimpl;

import cn.sspku.data.dao.mapper.DepartmentHeaderMapper;
import cn.sspku.data.entity.DepartmentHeader;
import cn.sspku.data.service.IDepartmentHeaderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 部门领导 服务层实现
 * @author 
 */
@Service
public class IDepartmentHeaderServiceImpl extends ServiceImpl<DepartmentHeaderMapper, DepartmentHeader> implements IDepartmentHeaderService {

}
