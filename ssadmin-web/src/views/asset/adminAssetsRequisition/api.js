import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

// 分页获取数据
export const getAdminAssetsList = (params) => {
    return getRequest('/adminAssets/getByPage', params)
}

export const getAdminAssetsListApply = (params) => {
    return getRequest('/adminAssetsRequest/getByPageApply', params)
}
export const outWageApproval = (params) => {
    return postRequest('/adminAssetsRequest/outWageApproval', params)
}
export const outWageDisapproval = (params) => {
    return postRequest('/adminAssetsRequest/outWageDisapproval', params)
}
export const returnData = (params) => {
    return postRequest('/adminAssetsRequest/returnData', params)
}
export const outWage = (params) => {
    return postRequest('/adminAssetsRequest/outWage', params)
}
export const submitApplyData = (params) => {
    return postRequest('/adminAssetsRequest/submitApplyData', params)
}

export const getAdminAssetsListAuditApply = (params) => {
    return getRequest('/adminAssetsRequest/getByPageAuditApply', params)
}


export const getAdminAssetsListReturn = (params) => {
    return getRequest('/adminAssets/getByPageMylist', params)
}
export const getAdminAssetsListAuditReturn = (params) => {
    return getRequest('/adminAssets/getByPageAuditReturn', params)
}
export const returnWage = (params) => {
    return postRequest('/adminAssets/returnWage', params)
}
// 添加
export const addAdminAssets = (params) => {
    return postRequest('/adminAssets/insertOrUpdate', params)
}
// 编辑
export const editAdminAssets = (params) => {
    return postRequest('/adminAssets/insertOrUpdate', params)
}
// 删除
export const deleteAdminAssets = (params) => {
    return postRequest('/adminAssets/delByIds', params)
}
export const getRosterUserByPage = (params) => {
    return getRequest('/user/getUserList', params)
}

export const returnWarehouse = (params) => {
    return postRequest('/adminAssets/return', params)
}
export const returnApply = (params) => {
    return postRequest('/adminAssets/returnApply', params)
}
export const returnReturn = (params) => {
    return postRequest('/adminAssets/returnReturn', params)
}

export const addRepair = (params) => {
    return getRequest('/adminAssetsRepair/add', params)
}


export const auditApplyData = (params) => {
    return postRequest('/adminAssets/auditApplyData', params)
}
export const submitReturnData = (params) => {
    return postRequest('/adminAssets/submitReturnData', params)
}
export const auditReturnData = (params) => {
    return postRequest('/adminAssets/auditReturnData', params)
}


// 分页获取数据
export const getAdminAssetsBuyList = (params) => {
    return getRequest('/adminAssetsBuy/getByPage', params)
}
// 添加
export const addAdminAssetsBuy = (params) => {
    return postRequest('/adminAssetsBuy/insert', params)
}
// 编辑
export const editAdminAssetsBuy = (params) => {
    return postRequest('/adminAssetsBuy/update', params)
}
// 删除
export const deleteAdminAssetsBuy = (params) => {
    return postRequest('/adminAssetsBuy/delByIds', params)
}



export const auditData = (params) => {
    return postRequest('/adminAssetsBuy/auditData', params)
}
export const inWare = (params) => {
    return postRequest('/adminAssetsBuy/inWare', params)
}
export const getAdminAssetList = (params) => {
    return getRequest('/adminAsset/getByPage', params)
}
// 获取所有仓库档案
export const getAllWareList = (params) => {
    return getRequest('/adminAssetWare/getAll', params)
}