package cn.sspku.assets.serviceimpl;

import cn.sspku.assets.entity.AdminAssetsDispose;
import cn.sspku.assets.entity.AdminAssetsRequest;
import cn.sspku.assets.entity.AdminAssetsRetire;
import cn.sspku.assets.mapper.AdminAssetsDisposeMapper;
import cn.sspku.assets.mapper.AdminAssetsRetireMapper;
import cn.sspku.assets.service.IAdminAssetsDisposeService;
import cn.sspku.assets.service.IAdminAssetsRetireService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 固定资产报修 服务层接口实现
 * @author 开发者
 */
@Slf4j
@Service
@Transactional
public class IAdminAssetsDisposeServiceImpl extends MPJBaseServiceImpl<AdminAssetsDisposeMapper, AdminAssetsDispose> implements IAdminAssetsDisposeService {

    @Autowired
    private AdminAssetsDisposeMapper adminAssetsDisposeMapper;

    public AdminAssetsDispose getByAssetId(String assetId) {
        return adminAssetsDisposeMapper.getByAssetId(assetId);
    }
}