package com.market.bc.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.market.bc.configurer.MinIoUtils;
import com.market.bc.configurer.MinioProp;
import com.market.bc.service.UploadService;
import entity.Result;
import entity.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final MinIoUtils minIoUtils;

    @Autowired
    private MinioProp minioProp;

    @Override
    public Map<String, Object> initMultiPartUpload(String path, String filename, Integer partCount, String contentType) {
        path = path.replaceAll("/+", "/");
        if (path.indexOf("/") == 0) {
            path = path.substring(1);
        }

        String filePath;

        if (path == null || path.isEmpty() || "".equals(path)) {
            filePath = filename;
        } else {
            filePath = path + "/" + filename;
        }

        System.out.println("path: " + path);
        System.out.println("filePath: " + filePath);

        Map<String, Object> result;

        // TODO::单文件上传可拆分
        if (partCount == 1) {
            String uploadObjectUrl = minIoUtils.getUploadObjectUrl(filePath);
            //TODO bucket change
            String url = minioProp.getEndpoint() + "/" + minioProp.getBucket() + "/" + filePath;
            result = ImmutableMap.of("uploadUrls", ImmutableList.of(uploadObjectUrl), "url", url);
        } else {
            result = minIoUtils.initMultiPartUpload(filePath, partCount, contentType);
        }

        return result;
    }

    @Override
    public Result mergeMultipartUpload(String objectName, String uploadId) {
        boolean result = minIoUtils.mergeMultipartUpload(objectName, uploadId);
        if (result) {
            String url = minioProp.getEndpoint() + "/" + minioProp.getBucket() + "/" + objectName;
            return new Result(true, StatusCode.OK, "合并文件成功", ImmutableMap.of("url", url));
        }
        return new Result(true, StatusCode.ERROR, "合并文件失败");
    }
}
