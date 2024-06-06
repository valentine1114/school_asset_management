package cn.sspku.assets.service;

import cn.sspku.assets.entity.AdminAssetsRepair;
import cn.sspku.assets.entity.AdminAssetsRetire;
import cn.sspku.assets.entity.AdminAssetsReturn;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 固定资产报修 服务层接口
 * @author 开发者
 */
public interface IAdminAssetsRetireService extends IService<AdminAssetsRetire> {
    AdminAssetsRetire getByAssetId(String assetId);
}