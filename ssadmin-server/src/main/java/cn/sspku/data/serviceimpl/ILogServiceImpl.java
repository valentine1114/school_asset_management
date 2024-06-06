package cn.sspku.data.serviceimpl;

import cn.sspku.data.dao.mapper.LogMapper;
import cn.sspku.data.entity.Log;
import cn.sspku.data.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统日志 服务层实现
 * @author
 */
@Service
public class ILogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

}
