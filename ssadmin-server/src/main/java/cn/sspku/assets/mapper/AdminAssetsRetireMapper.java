package cn.sspku.assets.mapper;

import cn.sspku.assets.entity.AdminAssetsRepair;
import cn.sspku.assets.entity.AdminAssetsRetire;
import cn.sspku.assets.entity.AdminAssetsReturn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;

/**
 * 固定资产报修数据处理层
 * @author 开发者
 */
public interface AdminAssetsRetireMapper extends MPJBaseMapper<AdminAssetsRetire> {
    AdminAssetsRetire getByAssetId(String assetId);
}