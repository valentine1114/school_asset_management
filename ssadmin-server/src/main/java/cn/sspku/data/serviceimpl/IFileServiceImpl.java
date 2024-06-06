package cn.sspku.data.serviceimpl;

import cn.sspku.data.dao.mapper.FileMapper;
import cn.sspku.data.entity.File;
import cn.sspku.data.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统文件 服务层实现
 * @author 
 */
@Service
public class IFileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

}
