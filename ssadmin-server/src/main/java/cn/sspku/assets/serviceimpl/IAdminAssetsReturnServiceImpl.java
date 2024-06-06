package cn.sspku.assets.serviceimpl;

import cn.sspku.assets.entity.AdminAssetsRequest;
import cn.sspku.assets.entity.AdminAssetsReturn;
import cn.sspku.assets.mapper.AdminAssetsRequestMapper;
import cn.sspku.assets.mapper.AdminAssetsReturnMapper;
import cn.sspku.assets.service.IAdminAssetsRequestService;
import cn.sspku.assets.service.IAdminAssetsReturnService;
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
public class IAdminAssetsReturnServiceImpl extends MPJBaseServiceImpl<AdminAssetsReturnMapper, AdminAssetsReturn> implements IAdminAssetsReturnService {

    @Autowired
    private AdminAssetsReturnMapper adminAssetsReturnMapper;

    public AdminAssetsReturn getByAssetId(String assetId) {
        return adminAssetsReturnMapper.getByAssetId(assetId);
    }

}