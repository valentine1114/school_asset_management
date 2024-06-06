package cn.sspku.assets.service;

import cn.sspku.assets.entity.AdminAssets;
import cn.sspku.assets.entity.AdminAssetsRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 行政资产库存接口
 * @author 开发者
 */
public interface IAdminAssetsService extends IService<AdminAssets> {

    AdminAssets getByAssetName(String name);
}