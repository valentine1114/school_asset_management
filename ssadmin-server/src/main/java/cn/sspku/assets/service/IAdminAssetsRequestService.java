package cn.sspku.assets.service;

import cn.sspku.assets.entity.AdminAssetsRequest;
import cn.sspku.assets.entity.AdminAssetsRetire;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 固定资产报修 服务层接口
 * @author 开发者
 */
public interface IAdminAssetsRequestService extends IService<AdminAssetsRequest> {
    AdminAssetsRequest getByAssetId(String assetId);
}