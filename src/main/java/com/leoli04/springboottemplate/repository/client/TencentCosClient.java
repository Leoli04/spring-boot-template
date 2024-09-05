package com.leoli04.springboottemplate.repository.client;

import com.leoli04.springboottemplate.common.config.TencentCosConfig;
import com.leoli04.springboottemplate.common.util.DateUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @Description: 腾讯cos client
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 21:42
 * @Version: 1.0
 */
@Repository
@Slf4j
public class TencentCosClient {
    @Autowired
    private TencentCosConfig tencentCosConfig;

    @Autowired
    private COSClient cosClient;

    private static final String IMAGE_PATH = "image/";
    private static final String AFFIX_PATH = "affix/";
    private static final String EXCEL_PATH = "excel/";



    /**
     * 拼接cos域名
     * @param key
     * @return
     */
    public String getCosUrl(String key){
        if(StringUtils.isBlank(key)){
            log.warn("key is blank");
            return key;
        }
        if(key.startsWith("http") || key.startsWith("https")){
            return key;
        }
        return tencentCosConfig.getIcDomain()+key;
    }

    /**
     * 上传文件到cos
     * @param file
     * @return
     */
    public String uploadAffixByMultipartFile(MultipartFile file){

        if(ObjectUtils.isEmpty(file) || file.getSize()<=0){
            log.warn("未选择文件，不上传cos。");
            return null;
        }
        String originalFilename = file.getOriginalFilename();

        try {
            InputStream inputStream = file.getInputStream();
            String bucketName = tencentCosConfig.getBucketName();
            String key = AFFIX_PATH + DateUtil.format(new Date(),"yyyyMMdd")+"_" +originalFilename;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream,null);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            log.info(putObjectResult.getRequestId());
            return key;
        }catch (Exception e){
            log.error("处理图片失败",e);
        }
        return null;
    }

    /**
     *
     * @param url 图片地址
     * @param key 新图片地址在cos中的地址
     * @return
     */
    public String uploadCosByUrl(String url,String key ){
        if(StringUtils.isAnyBlank(url,key)){
            log.warn("处理图片 忽略。 url={},key={}",url,key);
            return null;
        }
        String bucketName = tencentCosConfig.getBucketName();
        key = IMAGE_PATH + key;
        try (InputStream inputStream = new URL(url).openStream()) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream,null);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            log.info(putObjectResult.getRequestId());
            return key;
        } catch (Exception e){
            log.error("处理图片失败 url={}",url,e);
        }
        return null;
    }

    public String uploadExcel(Workbook workbook, String fileName) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());

        String bucketName = tencentCosConfig.getBucketName();
        String key = EXCEL_PATH + fileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, null);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        log.info("uploadExcel:" + putObjectResult.getRequestId());
        return key;
    }

}
