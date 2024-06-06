package cn.sspku.data.utils;

import cn.sspku.data.vo.OssSetting;
import cn.sspku.data.vo.OssSettingVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author
 */
public interface FileManage {

    @ApiOperation(value = "删除文件")
    void deleteFile(String key);

    @ApiOperation(value = "重命名文件")
    String renameFile(String fromKey, String toKey);

    @ApiOperation(value = "获取配置")
    OssSettingVo getOssSetting();

    @ApiOperation(value = "拷贝文件")
    String copyFile(String fromKey, String toKey);

    @ApiOperation(value = "文件流上传")
    String inputStreamUpload(InputStream inputStream, String key, MultipartFile file);
}
