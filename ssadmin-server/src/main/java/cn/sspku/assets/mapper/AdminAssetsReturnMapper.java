package cn.sspku.assets.mapper;

import cn.sspku.assets.entity.AdminAssetsRequest;
import cn.sspku.assets.entity.AdminAssetsReturn;
import com.github.yulichang.base.MPJBaseMapper;

/**
 * 固定资产报修数据处理层
 * @author 开发者
 */

public interface AdminAssetsReturnMapper extends MPJBaseMapper<AdminAssetsReturn> {
    AdminAssetsReturn getByAssetId(String assetId);

}