package me.zhengjie.utils;

import com.alibaba.fastjson.JSONObject;

import me.zhengjie.utils.dingUtils.ClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: Ape-opl
 * @description:
 * @author: ming.cao
 * @create: 2021-01-15 14:45
 **/
@Component
public class GetTokenUtils {
    @Autowired
    private RedisUtils redisUtils;

    public String getCrmToken(){
        String authorization="";
        String tempAuth= (String) redisUtils.get("crmTokenValue");
        if (StringUtils.isNotEmpty(tempAuth)){
            authorization = tempAuth;
        }else{
            //获取token
            String url1 = "https://api.xiaoshouyi.com/oauth2/token";
            String json = "{\"grant_type\":\"password\",\"client_id\":\"b9c523afeda4a627199763c2247f31df\"," +
                    "\"client_secret\":\"620987f5dfc557224da9e4ad55157e4a\",\"redirect_uri\":\"https://api.xiaoshouyi.com\"," +
                    "\"username\":\"Nina.ge@apetech.com\",\"password\":\"123456cZkRE5YI\"}";
            //获取token
            String token = ClientUtil.doPostJson(url1, json);
            JSONObject jsonObject1 = JSONObject.parseObject(token);
            authorization = "Bearer " + jsonObject1.getString("access_token");
            Long issuedAt = Long.parseLong(jsonObject1.getString("issued_at"));
            Long issuedTime =3600*24*28L;
            redisUtils.set("crmTokenValue",authorization,issuedTime);
        }
        return authorization;
    }

    public String getSimpleToken(){
        //获取token
        String url1 = "https://api.xiaoshouyi.com/oauth2/token";
        String json = "{\"grant_type\":\"password\",\"client_id\":\"b9c523afeda4a627199763c2247f31df\"," +
                "\"client_secret\":\"620987f5dfc557224da9e4ad55157e4a\",\"redirect_uri\":\"https://api.xiaoshouyi.com\"," +
                "\"username\":\"Nina.ge@apetech.com\",\"password\":\"123456cZkRE5YI\"}";
        //获取token
        String token = ClientUtil.doPostJson(url1, json);
        JSONObject jsonObject1 = JSONObject.parseObject(token);
        String authorization = "Bearer " + jsonObject1.getString("access_token");
        Long issuedTime =3600*24*28L;
        redisUtils.set("crmTokenValue",authorization,issuedTime);
        return authorization;
    }

}
