package cn.sspku.assets.serviceimpl;

import cn.sspku.assets.mapper.AdminAssetWareMapper;
import cn.sspku.assets.entity.AdminAssetWare;
import cn.sspku.assets.service.IAdminAssetWareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 行政资产仓库档案接口实现
 * @author 开发者
 */
@Slf4j
@Service
@Transactional
public class IAdminAssetWareServiceImpl extends MPJBaseServiceImpl<AdminAssetWareMapper, AdminAssetWare> implements IAdminAssetWareService {

    @Autowired
    private AdminAssetWareMapper adminAssetWareMapper;
}