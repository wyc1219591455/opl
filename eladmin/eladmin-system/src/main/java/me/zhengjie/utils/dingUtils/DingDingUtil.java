package me.zhengjie.utils.dingUtils;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.taobao.api.ApiException;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author: yuchao.wang
 * @Date: 2020/11/20
 * @Description: 钉钉工具类
 */
public class DingDingUtil {
    private static String CORPID = "dingocqkv5jyldsyb0f3"; // 企业Id
    private static String CORPSECRET = "IVei3vHz4WLGiGFjgW1fr6Nud4XMCo351EYEI1gJYn4IxoP4iKCrV_ip45unVO7V"; // 企业应用的凭证密钥
    public static Long AGENTID = 843368209L; // 自动分配微应用的ID

    /**
     * @Description:获得token
     * @Method:getToken
     */
    public static String getToken(String corpid, String corpsecret){
        String accessToken=null;
        String result = ClientUtil.sendGet("https://oapi.dingtalk.com/gettoken?appkey="+ corpid + "&appsecret=" + corpsecret);
        JSONObject json = JSONObject.parseObject(result);
        if (json != null && "0".equals(json.get("errcode").toString())) {
            accessToken= json.get("access_token").toString();
        }
        return accessToken;
    }

    /**
     * @Description:发送消息
     * @Method:sendDDMessage
    //     * @param agentId 微应用的ID
    //     * @param userId 接收者的用户userid列表
    //     * @param  msgcontent 消息内容
     */
    public static JSONObject sendDDMessage(String userId,String messages,String savePath) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request req = new OapiMessageCorpconversationAsyncsendV2Request();
        req.setAgentId(AGENTID);
        req.setUseridList(userId);
        req.setToAllUser(false);// 是否发送给企业全部用户
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent(df.format(new Date())+'\n'+messages+savePath);
        req.setMsg(msg);
        try {
            String token = getToken(CORPID,CORPSECRET);
            OapiMessageCorpconversationAsyncsendV2Response response = client.execute(req, token);
            JSONObject json = JSONObject.parseObject(String.valueOf(response.getBody()));
            if (json != null) {
                return json;
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     //     * @Description:根据电话获取用户信息
     //     * @Method:getUserIdByMobile
     //     * @param mobile
     //     接口不支持传输多个mobile进行查询*/
    public static String getUserIdByMobile(String mobile){
        try {
            String accessToken = getToken(CORPID,CORPSECRET);
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get_by_mobile");
            OapiUserGetByMobileRequest request = new OapiUserGetByMobileRequest();
            request.setMobile(mobile);
            OapiUserGetByMobileResponse execute = client.execute(request, accessToken);
            JSONObject mobileList = JSONObject.parseObject(String.valueOf(execute.getBody()));
            if (mobileList != null ) {
                String userId = mobileList.getString("userid");
                return userId;
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

}
