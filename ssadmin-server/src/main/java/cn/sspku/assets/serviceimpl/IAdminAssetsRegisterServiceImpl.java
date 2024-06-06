package cn.sspku.assets.serviceimpl;

import cn.sspku.assets.entity.AdminAssetsRegister;
import cn.sspku.assets.mapper.AdminAssetsRegisterMapper;


import cn.sspku.assets.service.IAdminAssetsRegisterService;
import cn.sspku.assets.service.IAdminAssetsService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 行政资产采购接口实现
 * @author 开发者
 */
@Slf4j
@Service
@Transactional
public class IAdminAssetsRegisterServiceImpl extends MPJBaseServiceImpl<AdminAssetsRegisterMapper, AdminAssetsRegister> implements IAdminAssetsRegisterService {

    @Autowired
    private AdminAssetsRegisterMapper adminAssetsRegisterMapper;



    public AdminAssetsRegister getByAssetId(String assetId) {
        return adminAssetsRegisterMapper.getByAssetId(assetId);
    }
}