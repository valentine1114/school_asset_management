package cn.sspku.data.serviceimpl;

import cn.sspku.data.dao.mapper.DepartmentMapper;
import cn.sspku.data.entity.Department;
import cn.sspku.data.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 部门 服务层实现
 * @author
 */
@Service
public class IDepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
