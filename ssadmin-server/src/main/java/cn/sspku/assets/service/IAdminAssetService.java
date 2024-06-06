package cn.sspku.assets.service;

import cn.sspku.assets.entity.AdminAsset;
import cn.sspku.assets.entity.AdminAssets;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 行政资产类型接口
 * @author 开发者
 */
public interface IAdminAssetService extends IService<AdminAsset> {
    AdminAsset getByAssetName(String name);
}