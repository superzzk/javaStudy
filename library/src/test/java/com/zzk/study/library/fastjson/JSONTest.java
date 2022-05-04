package com.zzk.study.library.fastjson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class JSONTest {

    /**
     * 通过传统方式自己拼接字符串JSON
     */
    public static void setJSON() {

        String str = "    { \"errorCode\": \"0\",\"errorMsg\": \"调用接口成功\",\"data\": [{\"userName\": \"余胜军\",\"position\": \"蚂蚁课堂创始人\",\"webAddres\": \"www.itmayiedu.com\"   },  {  \"userName\": \"周安旭\",  \"position\": \"蚂蚁课堂合伙人\",   \"webAddres\": \"www.itmayiedu.com\"  }    ]}";
        System.out.println(str);
    }

    /**
     * 通过fastJSON封装JSON
     */
    @Test
    public void setFastJSON() {
        JSONObject root = new JSONObject();
        root.put("errorCode", 0);
        root.put("errorMsg", "调用接口成功");
        JSONArray dataArr = new JSONArray();
        JSONObject userYushengjun = new JSONObject();
        userYushengjun.put("userName", "余胜军");
        userYushengjun.put("position", "蚂蚁课堂创始人");
        userYushengjun.put("webAddres", "www.itmayiedu.com");
        dataArr.add(userYushengjun);
        root.put("data", dataArr);
        System.out.println(JSONObject.toJSONString(root,true));
    }

    /**
     * 通过map转换成json
     */
    @Test
    public void setMapToJSON() {

        HashMap<String, Object> root = new HashMap<>();
        root.put("errorCode", 0);
        root.put("errorMsg", "调用接口成功");

        List<Map<String, String>> dataArr = new ArrayList<>();
        Map<String, String> userYushengjun = new HashMap<>();
        userYushengjun.put("userName", "余胜军");
        userYushengjun.put("position", "蚂蚁课堂创始人");
        userYushengjun.put("webAddres", "www.itmayiedu.com");
        Map<String, String> itmayiedu = new HashMap<>();
        itmayiedu.put("userName", "余胜军1");
        itmayiedu.put("position", "蚂蚁课堂创始人1");
        itmayiedu.put("webAddres", "www.itmayiedu.com");
        dataArr.add(itmayiedu);
        dataArr.add(userYushengjun);
        root.put("data", dataArr);
        System.out.println(JSON.toJSONString(root,true));
    }

    /**
     * 通过实体类转换JSON
     */
    static public void setBeanToJSON() {

        RootEntity rootEntity = new RootEntity();
        rootEntity.setErrorCode("0");
        rootEntity.setErrorMsg("调用接口成功");
        List<UserEntity> data = new ArrayList<UserEntity>();
        UserEntity userEntity = new UserEntity();
        userEntity.setPosition("蚂蚁课堂创始人");
        userEntity.setUserName("余胜军");
        userEntity.setWebAddres("itmayiedu.com");
        data.add(userEntity);
        rootEntity.setData(data);
        System.out.println(new JSONObject().toJSONString(rootEntity));
    }

    /**
     * 解析JSON
     */
    @Test
    public void analysisJson() {
        String jsonStr = "{ \"errorCode\": \"0\",\"errorMsg\": \"调用接口成功\",\"data\": [{\"userName\": \"余胜军\",\"position\": \"蚂蚁课堂创始人\",\"webAddres\": \"www.itmayiedu.com\"   },  {  \"userName\": \"周安旭\",  \"position\": \"蚂蚁课堂合伙人\",   \"webAddres\": \"www.itmayiedu.com\"  }    ]}";
        // 将json字符串转换成json
        JSONObject root = JSON.parseObject(jsonStr);
        String errorCode = root.getString("errorCode");
        String errorMsg = root.getString("errorMsg");
        System.out.println("errorCode:" + errorCode + ",errorMsg:" + errorMsg);
        JSONArray dataArr = root.getJSONArray("data");
        for (int i = 0; i < dataArr.size(); i++) {
            JSONObject dataBean = (JSONObject) dataArr.get(i);
            String position = dataBean.getString("position");
            String userName = dataBean.getString("userName");
            String webAddres = dataBean.getString("webAddres");
            System.out.println("position:" + position + ",userName:" + userName + ",webAddres:" + webAddres);
        }
    }

    /**
     * 通过JSON转实体类
     */
    @Test
    public void jsonToBean() {
        String jsonStr = "{ \"errorCode\": \"0\",\"errorMsg\": \"调用接口成功\",\"data\": [{\"userName\": \"余胜军\",\"position\": \"蚂蚁课堂创始人\",\"webAddres\": \"www.itmayiedu.com\"   },  {  \"userName\": \"周安旭\",  \"position\": \"蚂蚁课堂合伙人\",   \"webAddres\": \"www.itmayiedu.com\"  }    ]}";
        // 将json字符串转换成json
        RootEntity rootEntity = JSON.parseObject(jsonStr, RootEntity.class);
        System.out.println(rootEntity.toString());
    }


}