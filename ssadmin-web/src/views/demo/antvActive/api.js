import { getRequest, postRequest, putRequest, postBodyRequest, getNoAuthRequest, postNoAuthRequest } from '@/libs/axios';

export const getAntvVoListByDepartment = (params) => {
    return getRequest('/adminAssets/getAntvVoListByDepartment', params)
}
export const getAntvVoListByWarehouse = (params) => {
    return getRequest('/adminAssets/getAntvVoListByWarehouse', params)
}
// api.js
// api.js
export async function getAssetDataByWarehouse() {
    // 模拟从API获取数据
    return {
        success: true,
        result: [
            { title: "第一仓库", value: 500 },
            { title: "第二仓库", value: 300 },
            { title: "第三仓库", value: 200 },
        ],
    };
}

export async function getAssetDataByDepartment() {
    // 模拟从API获取数据
    return {
        success: true,
        result: [
            { title: "财务部", value: 400 },
            { title: "综合办公室", value: 250 },
            { title: "教务部", value: 350 },
        ],
    };
}

export async function getAssetDataByResearchProject() {
    // 模拟从API获取数据
    return {
        success: true,
        result: [
            { title: "项目A资产", value: 700 },
            { title: "项目B资产", value: 500 },
            { title: "项目C资产", value: 300 },
        ],
    };
}

