package cn.sspku.assets.mapper;

import cn.sspku.assets.entity.AdminAssetsRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.sspku.assets.entity.AdminAssets;
import com.github.yulichang.base.MPJBaseMapper;

/**
 * 行政资产库存数据处理层
 * @author 开发者
 */
public interface AdminAssetsMapper extends MPJBaseMapper<AdminAssets> {

    AdminAssets getByAssetName(String name);

}