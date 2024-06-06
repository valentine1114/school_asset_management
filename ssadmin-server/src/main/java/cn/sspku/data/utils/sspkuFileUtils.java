package cn.sspku.data.utils;

import cn.sspku.basics.exception.sspkuException;
import cn.sspku.data.entity.Setting;
import cn.sspku.data.service.ISettingService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.sspku.data.vo.OssSettingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 本地文件工具类
 * @author 
 */
@Component
public class sspkuFileUtils implements FileManage {

    @Autowired
    private ISettingService iSettingService;



    public static void view(String url, HttpServletResponse response){
        File viewFile = new File(url);
        if (!viewFile.exists()) {
            throw new sspkuException("没有文件");
        }
        try (FileInputStream is = new FileInputStream(viewFile); BufferedInputStream bis = new BufferedInputStream(is)) {
            OutputStream out = response.getOutputStream();
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buf)) > 0) {
                out.write(buf, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new sspkuException("读取下载文件出错" + e);
        }
    }

    @Override
    public String inputStreamUpload(InputStream inputStream, String key, MultipartFile file) {

        String dirSys = System.getProperty("user.dir");
        String day = DateUtil.format(DateUtil.date(), "yyyyMMdd");

        String path = dirSys +File.separator+ "oa-file"+ File.separator +  day;


        File dir = new File(path);


        if(!dir.exists()){
            dir.mkdirs();
        }
        File f = new File(path + File.separator + key);
        if(f.exists()){
            throw new sspkuException("文件名称重复");
        }
        try {
            file.transferTo(f);
            System.out.println("路径+"+path + File.separator + key);
            return path + File.separator + key;

        } catch (IOException e) {
            throw new sspkuException("上传文件出错 " + e);
        }
    }

    @Override
    public void deleteFile(String url) {
        FileUtil.del(new File(url));
    }

    @Override
    public String copyFile(String url, String toKey) {
        File copyFile = new File(url);
        String newUrl = copyFile.getParentFile() + File.separator + toKey;
        FileUtil.copy(copyFile, new File(newUrl), true);
        return newUrl;
    }

    @Override
    public String renameFile(String url, String toKey) {
        File renameFile = new File(url);
        FileUtil.rename(renameFile, toKey, false, true);
        return renameFile.getParentFile() + File.separator + toKey;
    }

    @Override
    public OssSettingVo getOssSetting() {
        Setting s1 = iSettingService.getById("FILE_VIEW");
        Setting s2 = iSettingService.getById("FILE_HTTP");
        Setting s3 = iSettingService.getById("FILE_PATH");
        if(s1 == null || s2 == null || s3 == null) {
            return null;
        }
        return new OssSettingVo(s1.getValue(),s2.getValue(),s3.getValue());
    }
}
