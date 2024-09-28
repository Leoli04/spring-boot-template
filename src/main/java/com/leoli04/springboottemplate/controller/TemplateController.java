package com.leoli04.springboottemplate.controller;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.leoli04.springboottemplate.common.enums.DownloadTemplateEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Description: 模版控制器
 * @Author: LeoLi04
 * @CreateDate: 2024/9/27 9:48
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("admin/template")
public class TemplateController {

    @PostMapping("/download")
    public ResponseEntity<?> downloadTemplate(@RequestBody Map<String,String> templateTypeMap){
        String fileName = DownloadTemplateEnum.getTemplateNameByType(templateTypeMap.getOrDefault("templateType",null));
        try {
            // 创建文件资源
            ClassPathResource resource = new ClassPathResource("excelTemplate/"+fileName);

            // 检查文件是否存在
            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("文件不存在: " + fileName);
            }


            // 读取文件内容到字节数组
            byte[] fileContent = resource.getInputStream().readAllBytes();

            // 设置响应头
            HttpHeaders headers = new HttpHeaders();
            String encodedFileName = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8.toString());
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName);

            // 返回文件
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileContent);
        } catch (IOException e) {
            // 处理其他异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("发生错误: " + e.getMessage());
        }
    }

    @PostMapping("/download2")
    public void downloadTemplateStream(@RequestBody Map<String,String> templateTypeMap, HttpServletResponse response){
        String fileName = DownloadTemplateEnum.getTemplateNameByType(templateTypeMap.getOrDefault("templateType",null));
        if(StringUtils.isBlank(fileName)){
            log.warn("downloadTemplate fileName is blank.templateTypeMap={}", JSON.toJSONString(templateTypeMap));
            return;
        }
        // 这里URLEncoder.encode可以防止中文乱码 和easyexcel没有关系
        String newFileName = null;
        try {
            newFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            log.error("downloadTemplate URLEncoder.encode error.templateTypeMap={}", JSON.toJSONString(templateTypeMap));
        }
        // 当客户端请求的资源是一个可下载的资源（这里的“可下载”是指浏览器会弹出下载框或者下载界面）时，对这个可下载资源的描述（例如下载框中的文件名称）就是来源于该头域。
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + newFileName+".xlsx");
        // 服务器告诉浏览器它发送的数据属于什么文件类型，也就是响应数据的MIME类型
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 关闭缓存（HTTP/1.1）
        response.setHeader("Cache-Control", "no-store");
        // 关闭缓存（HTTP/1.0）
        response.setHeader("Pragma", "no-cache");
        // 缓存有效时间
        response.setDateHeader("Expires", 0);

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // 读取文件的输入流
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("excelTemplate/"+fileName);
            outputStream = response.getOutputStream();
            IoUtil.copy(inputStream,outputStream);
        } catch (Exception e) {
            log.error("downloadTemplate filePath is blank.templateTypeMap={}", JSON.toJSONString(templateTypeMap));
        }finally {
            IoUtil.close(inputStream);
            IoUtil.close(outputStream);
        }
    }
}
