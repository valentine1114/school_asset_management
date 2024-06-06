package cn.sspku.basics.security.utils;

import cn.sspku.data.utils.sspkuNullUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 企微消息发送工具类
 * @author 
 */
public class sspkuWxNoticeUtils {

    private static final String BASE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    private static final String USER_ID_ERR = "81013";

    @ApiModelProperty(value = "朗世企业ID")
    public static final String YH_CORPID = "wwf94bb44e76e308f8";

    /**
     * 发送文本消息
     * @param userId 微信ID
     * @param content 内容
     */
    public static String sendInputMessage(int company,String userId,String content,String token){
        if(content == null || sspkuNullUtils.isNull(content)) {
            return "NULL";
        }

        String json = JSON.toJSONString(new sspkuWeChatNoticeInput(userId,"text",YH_CORPID,new sspkuWeChatNoticeInputItem(content),0,1));
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        System.out.println(s);
        JSONObject ans1 = JSONObject.parseObject(s);
        String jsonStr2 = ans1.getString("errcode");
        if(jsonStr2.equals("0")){
            return ans1.getString("msgid");
        } else if(jsonStr2.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * 企微上传文件
     * @param path 文件路径
     * @param fileType 文件类型 image file
     * @return
     */
    public static String uploadWeChatFile(String path, String fileType,String token) {
        try {
            JSONObject jsonObject = WeChatUploadMeidaUtils.UploadMeida(fileType,path,token);
            System.out.println(jsonObject.toJSONString());
            String errcode = jsonObject.getString("errcode");
            if(errcode.equals("0")) {
                System.out.println("文件上传成功");
                System.out.println(jsonObject.getString("media_id"));
                return jsonObject.getString("media_id");
            }
            return "文件上传失败";
        } catch (Exception e) {
            return "文件上传失败";
        }
    }

    /**
     * 发送图片消息
     * @param userId 用户微信ID
     * @param mediaId 文件微信ID
     * @return
     */
    public static String sendImageMessage(int company,String userId,String mediaId,String token) {
        sspkuWeiChatNoticeImage image = new sspkuWeiChatNoticeImage(userId,"image",YH_CORPID,new sspkuWeChatNoticeImageItem(mediaId),0,1);
        String json = JSON.toJSONString(image);
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        JSONObject ans1 = JSONObject.parseObject(s);
        String errcode = ans1.getString("errcode");
        if(errcode.equals("0")){
            return ans1.getString("msgid");
        } else if(errcode.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * 发送视频消息
     * @param userId 用户微信ID
     * @param mediaId 文件微信ID
     * @return
     */
    public static String sendVideoMessage(int company,String userId,String mediaId,String title,String description,String token) {
        sspkuWeiChatNoticeVideo video = new sspkuWeiChatNoticeVideo(userId,"video",YH_CORPID,new sspkuWeChatNoticeVideoItem(mediaId,title,description),0,1);
        String json = JSON.toJSONString(video);
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        JSONObject ans1 = JSONObject.parseObject(s);
        String errcode = ans1.getString("errcode");
        if(errcode.equals("0")){
            return ans1.getString("msgid");
        } else if(errcode.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * 发送文件消息
     * @param userId 用户微信ID
     * @param mediaId 文件微信ID
     * @return
     */
    public static String sendFileMessage(int company,String userId,String mediaId,String token) {
        sspkuWeiChatNoticeFile file = new sspkuWeiChatNoticeFile(userId,"file",YH_CORPID,new sspkuWeChatNoticeFileItem(mediaId),0,1);
        String json = JSON.toJSONString(file);
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        JSONObject ans1 = JSONObject.parseObject(s);
        String errcode = ans1.getString("errcode");
        if(errcode.equals("0")){
            return ans1.getString("msgid");
        } else if(errcode.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * 发送文本卡片消息
     * @param userId 用户微信ID
     * @return
     */
    public static String sendTextCardMessage(int company,String userId,String title,String description,String url,String btntxt,String token) {
        sspkuWeiChatNoticeTextCard file = new sspkuWeiChatNoticeTextCard(userId,"textcard",YH_CORPID,new sspkuWeChatNoticeTextCardItem(title,description,url,btntxt),0,1);
        String json = JSON.toJSONString(file);
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        JSONObject ans1 = JSONObject.parseObject(s);
        String errcode = ans1.getString("errcode");
        if(errcode.equals("0")){
            return ans1.getString("msgid");
        } else if(errcode.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * 发送图文消息
     * @param userId 用户微信ID
     * @return
     */
    public static String sendTuWenMessage(String userId,String title,String description,String url,String picUrl,String token) {
        List<sspkuWeChatNoticeTuWenItemValue> tuWenList = new ArrayList<>();
        tuWenList.add(new sspkuWeChatNoticeTuWenItemValue(title, description, url, picUrl));
        sspkuWeChatNoticeTuWen file = new sspkuWeChatNoticeTuWen(userId,"news","1000002",new sspkuWeChatNoticeTuWenItem(tuWenList),0,1);
        String json = JSON.toJSONString(file);
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        JSONObject ans1 = JSONObject.parseObject(s);
        String errcode = ans1.getString("errcode");
        if(errcode.equals("0")){
            return ans1.getString("msgid");
        } else if(errcode.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * 发送Markdown消息
     * @param userId 微信ID
     * @param content 内容
     */
    public static String sendMarkdownMessage(int company,String userId,String content,String token){
        if(content == null || sspkuNullUtils.isNull(content)) {
            return "NULL";
        }
        String json = JSON.toJSONString(new sspkuWeChatNoticeMarkdown(userId,"markdown",YH_CORPID,new sspkuWeChatNoticeMarkdownItem(content),0,1));
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        System.out.println(s);
        JSONObject ans1 = JSONObject.parseObject(s);
        String jsonStr2 = ans1.getString("errcode");
        if(jsonStr2.equals("0")){
            return ans1.getString("msgid");
        } else if(jsonStr2.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * Markdown消息B类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeChatNoticeMarkdown {
        private String touser;
        private String msgtype;
        private String agentid;
        private sspkuWeChatNoticeMarkdownItem markdown;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * Markdown消息A类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeChatNoticeMarkdownItem {
        private String content;
    }

    /**
     * 图文消息B类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeChatNoticeTuWen {
        private String touser;
        private String msgtype;
        private String agentid;
        private sspkuWeChatNoticeTuWenItem news;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 图文消息A类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeChatNoticeTuWenItem {
        private List<sspkuWeChatNoticeTuWenItemValue> articles;
    }

    /**
     * 图文消息A类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeChatNoticeTuWenItemValue {
        private String title;
        private String description;
        private String url;
        private String picurl;
    }

    /**
     * 文本卡片消息B类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeiChatNoticeTextCard {
        private String touser;
        private String msgtype;
        private String agentid;
        private sspkuWeChatNoticeTextCardItem textcard;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 文本卡片消息A类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeChatNoticeTextCardItem {
        private String title;
        private String description;
        private String url;
        private String btntxt;
    }

    /**
     * 文件消息B类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeiChatNoticeFile {
        private String touser;
        private String msgtype;
        private String agentid;
        private sspkuWeChatNoticeFileItem file;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 文件消息A类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeChatNoticeFileItem {
        private String media_id;
    }

    /**
     * 视频消息B类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeiChatNoticeVideo {
        private String touser;
        private String msgtype;
        private String agentid;
        private sspkuWeChatNoticeVideoItem video;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 视频消息A类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeChatNoticeVideoItem {
        private String media_id;
        private String title;
        private String description;
    }

    /**
     * 图片消息B类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeiChatNoticeImage {
        private String touser;
        private String msgtype;
        private String agentid;
        private sspkuWeChatNoticeImageItem image;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 图片消息A类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeChatNoticeImageItem {
        private String media_id;
    }

    /**
     * 普通文本B类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeChatNoticeInput {
        private String touser;
        private String msgtype;
        private String agentid;
        private sspkuWeChatNoticeInputItem text;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 普通文本A类
     */
    @Data
    @AllArgsConstructor
    private static class sspkuWeChatNoticeInputItem {
        private String content;
    }
}
