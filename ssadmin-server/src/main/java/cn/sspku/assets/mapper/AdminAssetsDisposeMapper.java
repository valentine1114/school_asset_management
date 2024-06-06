package cn.sspku.assets.mapper;

import cn.sspku.assets.entity.AdminAssetsDispose;
import cn.sspku.assets.entity.AdminAssetsRetire;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;

/**
 * 固定资产报修数据处理层
 * @author 开发者
 */
public interface AdminAssetsDisposeMapper extends MPJBaseMapper<AdminAssetsDispose> {
    AdminAssetsDispose getByAssetId(String assetId);
}