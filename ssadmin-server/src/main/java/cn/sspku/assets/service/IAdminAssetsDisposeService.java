package cn.sspku.assets.service;

import cn.sspku.assets.entity.AdminAssetsDispose;
import cn.sspku.assets.entity.AdminAssetsRegister;
import cn.sspku.assets.entity.AdminAssetsRetire;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 固定资产报修 服务层接口
 * @author 开发者
 */
public interface IAdminAssetsDisposeService extends IService<AdminAssetsDispose> {
    AdminAssetsDispose getByAssetId(String assetId);
}