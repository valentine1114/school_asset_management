import { getRequest, postRequest, postRequests, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';



//按assetId查询资产购置信息
export const getAdminAssetByAssetId = (params) => {
    return getRequest('/adminAsset/getById', params)
}

// 文件上传
// export const uploadExcelFile = (params, config) => {
//     // 注意：这里的params通常是一个FormData对象，用于传输文件数据
//     return postRequest('/adminAsset/uploadExcel', params, config);
// }

export const uploadExcelFile = (formData) => {
    // delete headers.post['Content-Type']
    // Note: Remove the Content-Type header to let Axios set it with the correct boundary
    return postRequests('/adminAsset/uploadExcel', formData);
}

// export const uploadExcelFile = (params) => {
//     // Set headers for multipart/form-data
//     let config = {
//         headers: {
//             'Content-Type': 'multipart/form-data'
//         }
//     };
//     return postRequest('/adminAsset/uploadExcel', params, config);
// }

// 分页获取数据
export const getAdminAssetsRegisterList = (params) => {
    return getRequest('/adminAssetsRegister/getByPage', params)
}
// 添加
export const addAdminAssetsRegister = (params) => {
    return postRequest('/adminAssetsRegister/insert', params)
}
// 编辑
export const editAdminAssetsRegister = (params) => {
    return postRequest('/adminAssetsRegister/update', params)
}
// 删除
export const deleteAdminAssetsRegister = (params) => {
    return postRequest('/adminAssetsRegister/delByIds', params)
}
export const submitData = (params) => {
    return postRequest('/adminAssetsRegister/submitData', params)
}
export const returnData = (params) => {
    return postRequest('/adminAssetsRegister/returnData', params)
}

export const auditData = (params) => {
    return postRequest('/adminAssetsRegister/auditData', params)
}
export const inWare = (params) => {
    return postRequest('/adminAssetsRegister/inWare', params)
}
export const getAdminAssetList = (params) => {
    return getRequest('/adminAsset/getByPage', params)
}
// 获取所有仓库档案
export const getAllWareList = (params) => {
    return getRequest('/adminAssetWare/getAll', params)
}
export const generateQRCode = (params) => {
    return postRequest('/adminAsset/generateQRCode', params)
}

// 添加
export const addAdminAsset = (params) => {
    return postRequest('/adminAsset/insert', params)
}

// 编辑
export const editAdminAsset = (params) => {
    return postRequest('/adminAsset/update', params)
}
// 删除
export const deleteAdminAsset = (params) => {
    return postRequest('/adminAsset/delByIds', params)
}

// 获取所有计量单位档案
export const getAllUnitList = (params) => {
    return getRequest('/adminAssetUnit/getAll', params)
}
// 获取供应商档案
export const getAllSupplierList = (params) => {
    return getRequest('/adminAssetSupplier/getByPage', params)
}