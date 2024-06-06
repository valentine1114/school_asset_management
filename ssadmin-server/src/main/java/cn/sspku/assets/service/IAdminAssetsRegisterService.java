package cn.sspku.assets.service;

import cn.sspku.assets.entity.AdminAssetsRegister;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 行政资产采购接口
 * @author 开发者
 */
public interface IAdminAssetsRegisterService extends IService<AdminAssetsRegister> {
    AdminAssetsRegister getByAssetId(String assetId);
}