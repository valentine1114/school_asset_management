package cn.sspku.assets.mapper;

import cn.sspku.assets.entity.AdminAssetsRequest;
import cn.sspku.assets.entity.AdminAssetsRetire;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;

/**
 * 固定资产报修数据处理层
 * @author 开发者
 */

public interface AdminAssetsRequestMapper extends MPJBaseMapper<AdminAssetsRequest> {
    AdminAssetsRequest getByAssetId(String assetId);

}