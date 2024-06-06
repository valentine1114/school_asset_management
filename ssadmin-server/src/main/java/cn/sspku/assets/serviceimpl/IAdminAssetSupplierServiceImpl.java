package cn.sspku.assets.serviceimpl;

import cn.sspku.assets.mapper.AdminAssetSupplierMapper;
import cn.sspku.assets.entity.AdminAssetSupplier;
import cn.sspku.assets.service.IAdminAssetSupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 行政资产供应商接口实现
 * @author 开发者
 */
@Slf4j
@Service
@Transactional
public class IAdminAssetSupplierServiceImpl extends MPJBaseServiceImpl<AdminAssetSupplierMapper, AdminAssetSupplier> implements IAdminAssetSupplierService {

    @Autowired
    private AdminAssetSupplierMapper adminAssetSupplierMapper;
}