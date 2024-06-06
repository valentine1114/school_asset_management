package cn.sspku.assets.serviceimpl;

import cn.sspku.assets.entity.AdminAssetsRequest;
import cn.sspku.assets.mapper.AdminAssetsMapper;
import cn.sspku.assets.entity.AdminAssets;
import cn.sspku.assets.service.IAdminAssetsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 行政资产库存接口实现
 * @author 开发者
 */
@Slf4j
@Service
@Transactional
public class IAdminAssetsServiceImpl extends MPJBaseServiceImpl<AdminAssetsMapper, AdminAssets> implements IAdminAssetsService {

    @Autowired
    private AdminAssetsMapper adminAssetsMapper;

    public AdminAssets getByAssetName(String name) {
        return adminAssetsMapper.getByAssetName(name);
    }
}