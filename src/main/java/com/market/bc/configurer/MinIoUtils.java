package com.market.bc.configurer;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.HashMultimap;
import com.market.bc.util.CustomMinioClient;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListPartsResponse;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Part;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Component
@Configuration
@EnableConfigurationProperties(MinioProp.class)
public class MinIoUtils {
    private final MinioProp minioProp;
    private CustomMinioClient customMinioClient;
    private MinioClient minioClient;

    public MinIoUtils(MinioProp minioProp) {
        this.minioProp = minioProp;
    }

    @PostConstruct
    public void init() {
        minioClient = MinioClient.builder()
                .endpoint(minioProp.getEndpoint())
                .credentials(minioProp.getAccesskey(), minioProp.getSecretkey())
                .build();
        customMinioClient = new CustomMinioClient(minioClient);
    }

    /**
     * 单文件签名上传
     *
     * @param objectName 文件全路径名称
     * @return /
     */
    public String getUploadObjectUrl(String objectName) {
        // 上传文件时携带content-type头即可
        /*if (StrUtil.isBlank(contentType)) {
            contentType = "application/octet-stream";
        }
        HashMultimap<String, String> headers = HashMultimap.create();
        headers.put("Content-Type", contentType);*/
        try {
            return customMinioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(minioProp.getBucket())
                            .object(objectName)
                            .expiry(1, TimeUnit.DAYS)
                            //.extraHeaders(headers)
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 初始化分片上传
     *
     * @param objectName  文件全路径名称
     * @param partCount   分片数量
     * @param contentType 类型，如果类型使用默认流会导致无法预览
     * @return /
     */
    public Map<String, Object> initMultiPartUpload(String objectName, int partCount, String contentType) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (StrUtil.isBlank(contentType)) {
                contentType = "application/octet-stream";
            }
            HashMultimap<String, String> headers = HashMultimap.create();
            headers.put("Content-Type", contentType);
            String uploadId = customMinioClient.initMultiPartUpload(minioProp.getBucket(), null, objectName, headers, null);

            result.put("uploadId", uploadId);
            List<String> partList = new ArrayList<>();

            Map<String, String> reqParams = new HashMap<>();
            //reqParams.put("response-content-type", "application/json");
            reqParams.put("uploadId", uploadId);
            for (int i = 1; i <= partCount; i++) {
                reqParams.put("partNumber", String.valueOf(i));
                String uploadUrl = customMinioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.PUT)
                                .bucket(minioProp.getBucket())
                                .object(objectName)
                                .expiry(1, TimeUnit.DAYS)
                                .extraQueryParams(reqParams)
                                .build());
                partList.add(uploadUrl);
            }
            result.put("uploadUrls", partList);
            result.put("fileName", objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    /**
     * 分片上传完后合并
     *
     * @param objectName 文件全路径名称
     * @param uploadId   返回的uploadId
     * @return /
     */
    public boolean mergeMultipartUpload(String objectName, String uploadId) {
        try {
            //TODO::最大1000分片
            Part[] parts = new Part[1000];
            ListPartsResponse partResult = customMinioClient.listMultipart(minioProp.getBucket(), null, objectName, 1000, 0, uploadId, null, null);
            int partNumber = 1;
            for (Part part : partResult.result().partList()) {
                parts[partNumber - 1] = new Part(partNumber, part.etag());
                partNumber++;
            }
            customMinioClient.mergeMultipartUpload(minioProp.getBucket(), null, objectName, uploadId, parts, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public InputStream downLoad(String bucketname,String objectname ) {
        InputStream stream = null;

        // get object given the bucket and object name
        try {
            stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketname)
                            .object(objectname)
                            .build());
            // Read data from stream
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return stream;
    }

    /**
     * 通过HttpServletResponse将文件流返回给前端
     *
     * @param response HttpServletResponse在Controller层直接接收即可
     * @param is 需要返回的文件流
     * @param fileName 文件名
     * @return
     */
    private static ResponseEntity<InputStreamResource> encapsulateResponseEntities(HttpServletResponse response, InputStream is, String fileName) throws IOException {
        //设置文件格式，我这里是excel，根绝实际应用场景改即可
        response.setContentType("application/msexcel");
        //设置文件名，设置字符集是避免文件名中有中文时出现乱码
        fileName = new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        response.addHeader("Content-Disposition", "filename=" + fileName);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(is.read());
        outputStream.close();
        return null;
    }

}
