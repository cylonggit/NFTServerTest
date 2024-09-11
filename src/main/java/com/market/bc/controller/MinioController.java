package com.market.bc.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import com.market.bc.service.UploadService;
import entity.Result;
import entity.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;


@RestController
@RequiredArgsConstructor

@CrossOrigin
@RequestMapping("/upload")
public class MinioController {

    private final UploadService uploadService;


    @PostMapping("/multipart/init")
    public Result initMultiPartUpload(@RequestBody JSONObject requestParam) {
        System.out.println(requestParam.toString());
        String path = requestParam.getStr("path", "");
        String originalFilename = requestParam.getStr("filename", "test.obj");
        String bucketName = requestParam.getStr("bucket", "test");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = bucketName + "_" +
                System.currentTimeMillis() + "_" + format.format(new Date()) + "_" + new Random().nextInt(1000) +
                originalFilename.substring(originalFilename.lastIndexOf("."));
        String contentType = requestParam.getStr("contentType", "application/octet-stream");
        Integer partCount = requestParam.getInt("partCount", 1);

        //TODO::业务判断+秒传判断
        // md5-可进行秒传判断
        String md5 = requestParam.getStr("md5", "");
        Map<String, Object> result = uploadService.initMultiPartUpload(path, fileName, partCount, contentType);
        return new Result(true, StatusCode.OK, "success", result);
    }

    /**
     * 完成上传
     *
     * @param requestParam 用户参数
     * @return /
     */
    @PutMapping("/multipart/complete")
    public Result completeMultiPartUpload(
            @RequestBody JSONObject requestParam
    ) {
        // 文件名完整路径
        String objectName = requestParam.getStr("objectName");
        String uploadId = requestParam.getStr("uploadId");
        Assert.notNull(objectName, "objectName must not be null");
        Assert.notNull(uploadId, "uploadId must not be null");
        return uploadService.mergeMultipartUpload(objectName, uploadId);
    }
}
