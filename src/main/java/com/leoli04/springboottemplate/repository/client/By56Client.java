package com.leoli04.springboottemplate.repository.client;

import com.alibaba.fastjson2.JSON;
import com.leoli04.springboottemplate.common.config.property.By56Property;
import com.leoli04.springboottemplate.common.util.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Description: 百运服务
 * https://open.by56.com/apicus/#/
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 18:29
 * @Version: 1.0
 */
@Repository
@Slf4j
public class By56Client {

    @Autowired
    private By56Property by56Property;

    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取商品运费
     * @param questParams
     * @return
     * @throws IOException
     */
    public Map<String,Object> getCommodityEXP(Map<String,String> questParams) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        Map<String,String> params = new TreeMap<>();
        //公共参数
        params.put("bykey", by56Property.getKey());
        params.put("method","By56CustomerAPI.byExpOrder.ExpOrder.GetCommodityEXP");
        params.put("timestamp",dateFormat.format(new Date()));
        params.put("calls","byapi");
        params.put("format","json");
        params.put("sign_method","md5");
        //接口参数
        params.put("StartCityKey",questParams.get("startCity"));
        params.put("CountryKey",questParams.get("destinationCountry"));
        params.put("Weight",questParams.get("weight"));
        params.put("Volume",questParams.get("volume"));
        params.put("SpecialItems",questParams.get("specialItems"));
        params.put("PackgeType",questParams.get("packgeType"));
        //生成签名
        String secret = by56Property.getSecret().toUpperCase();
        String signStr = this.signTopRequest(params,secret);
        params.put("sign",signStr);
        //请求接口
        log.info("运费参数：params={}", JSON.toJSONString(params));
        String result = OkHttpUtils.builder()
                .url(by56Property.getHost())
                .addParams(params)
                .post(Boolean.TRUE)
                .sync();
        log.info("运费结果：result={}",JSON.toJSONString(result));
        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
        return resultMap;
    }

    /**
     * 参数签名
     * @param params 参数
     * @param secret 秘钥
     * @return
     * @throws IOException
     */
    private String signTopRequest(Map<String, String> params, String secret) throws IOException {
        // 第一步：检查参数是否已经排序
        StringBuilder query = new StringBuilder();
        query.append(secret);
        query.append(createLinkString(params));
        query.append(secret);
        System.out.println("签名值："+query.toString());
        // 第二步：使用MD5加密
        byte[] bytes;
        bytes = encryptMD5(query.toString());
        // 第三步：把二进制转化为大写的十六进制
        return byte2hex(bytes);
    }

    private byte[] encryptMD5(String data) throws IOException {
        return encryptMD5(data.getBytes("utf8"));
    }

    /**
     * 对字节流进行MD5摘要。
     */
    private byte[] encryptMD5(byte[] data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data);
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    /**
     * 把字节流转换为十六进制表示方式。
     */
    private String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    /**
     * 排序组装签名明文
     * @param params
     * @return
     */
    public StringBuilder createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });

        StringBuilder prestr = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            prestr.append(key);
            prestr.append(value);
        }
        return prestr;
    }
}
