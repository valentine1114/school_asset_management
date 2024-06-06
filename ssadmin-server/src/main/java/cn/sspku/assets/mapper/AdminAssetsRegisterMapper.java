package cn.sspku.assets.mapper;

import cn.sspku.assets.entity.AdminAssetsRegister;
import com.github.yulichang.base.MPJBaseMapper;

/**
 * 行政资产采购数据处理层
 * @author 开发者
 */
public interface AdminAssetsRegisterMapper extends MPJBaseMapper<AdminAssetsRegister> {
    AdminAssetsRegister getByAssetId(String assetId);
}