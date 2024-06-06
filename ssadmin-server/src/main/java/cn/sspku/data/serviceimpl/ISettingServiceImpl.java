package cn.sspku.data.serviceimpl;

import cn.sspku.data.dao.mapper.SettingMapper;
import cn.sspku.data.entity.Setting;
import cn.sspku.data.service.ISettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 设置 服务层实现
 * @author 
 */
@Service
public class ISettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements ISettingService {

}
