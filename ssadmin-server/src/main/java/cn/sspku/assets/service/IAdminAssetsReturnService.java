package cn.sspku.assets.service;

import cn.sspku.assets.entity.AdminAssetsRequest;
import cn.sspku.assets.entity.AdminAssetsReturn;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 固定资产报修 服务层接口
 * @author 开发者
 */
public interface IAdminAssetsReturnService extends IService<AdminAssetsReturn> {
    AdminAssetsReturn getByAssetId(String assetId);
}