package cn.sspku.assets.mapper;

import cn.sspku.assets.entity.AdminAssets;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.sspku.assets.entity.AdminAsset;
import com.github.yulichang.base.MPJBaseMapper;

/**
 * 行政资产类型数据处理层
 * @author 开发者
 */
public interface AdminAssetMapper extends MPJBaseMapper<AdminAsset> {
    AdminAsset getByAssetName(String name);
}