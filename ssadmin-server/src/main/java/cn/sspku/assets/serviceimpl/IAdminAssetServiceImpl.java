package cn.sspku.assets.serviceimpl;

import cn.sspku.assets.entity.AdminAssets;
import cn.sspku.assets.mapper.AdminAssetMapper;
import cn.sspku.assets.entity.AdminAsset;
import cn.sspku.assets.service.IAdminAssetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 行政资产类型接口实现
 * @author 开发者
 */
@Slf4j
@Service
@Transactional
public class IAdminAssetServiceImpl extends MPJBaseServiceImpl<AdminAssetMapper, AdminAsset> implements IAdminAssetService {

    @Autowired
    private AdminAssetMapper adminAssetMapper;
    public AdminAsset getByAssetName(String name) {
        return adminAssetMapper.getByAssetName(name);
    }
}