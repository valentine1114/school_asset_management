package cn.sspku.data.serviceimpl;

import cn.sspku.data.dao.mapper.DictMapper;
import cn.sspku.data.entity.Dict;
import cn.sspku.data.service.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 数据字典 服务层实现
 * @author
 */
@Service
public class IDictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

}
