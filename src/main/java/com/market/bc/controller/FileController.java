package com.market.bc.controller;

import com.market.bc.configurer.MinIoUtils;
import com.market.bc.fisco.FiscoBcosClient;
import com.market.bc.fisco.FiscoProp;
import com.market.bc.service.UploadService;
import entity.Result;
import entity.StatusCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;


@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/nft/file")
public class FileController {

    private final UploadService uploadService;
    @Autowired
    MinIoUtils minIoUtils;

    @Autowired
    FiscoBcosClient contract;

    @Autowired
    FiscoProp blockchainProp;

    @RequestMapping(value = "get/{tokenId}{fileId}/{timestamp}",method = RequestMethod.POST)
    public void getFile(HttpServletResponse response,@RequestBody final String cipher,@PathVariable String tokenId,@PathVariable String fileId,@PathVariable long timestamp) throws Exception {
        String bucket = blockchainProp.getBucket();
        String filename = fileId;
        InputStream io=null;
        if (cipher == null) {
            Result result =contract.isFilePublic(tokenId,fileId);
            if (result.isFlag() == true) {
                // get the public file
                try {
                    io = minIoUtils.downLoad(bucket, filename);
//                     //这个是会弹出下载
//                     byte[] data = IOUtils.toByteArray(io);
//                     response.reset();
//                     response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
//                     response.addHeader("Content-Length", "" + data.length);
//                     response.setContentType("application/octet-stream; charset=UTF-8");
//                     IOUtils.write(data, response.getOutputStream());
                    //下面是直接返回数据
                    OutputStream out = response.getOutputStream();
                    byte buffer[] = new byte[1024];
                    int length = 0;
                    while ((length = io.read(buffer)) >= 0) {
                        out.write(buffer, 0, length);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                    if (io != null) {
                        io.close();
                    }
                }
            }

        } else {
            Result result = contract.isSignValid(tokenId,fileId,timestamp, cipher);; // 验证pubkey签名, 参考这个
            if (result.getCode() == StatusCode.OK) {
                // get the private file
                try {
                     io = minIoUtils.downLoad(bucket, filename);
//                     //这个是会弹出下载
//                     byte[] data = IOUtils.toByteArray(io);
//                     response.reset();
//                     response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
//                     response.addHeader("Content-Length", "" + data.length);
//                     response.setContentType("application/octet-stream; charset=UTF-8");
//                     IOUtils.write(data, response.getOutputStream());
                     //下面是直接返回数据
                    OutputStream out = response.getOutputStream();
                    byte buffer[] = new byte[1024];
                    int length = 0;
                    while ((length = io.read(buffer)) >= 0) {
                        out.write(buffer, 0, length);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                    if (io != null) {
                        io.close();
                    }
                }
            }
        }
    }

    @RequestMapping(value = "down/{tokenId}/{fileId}/{timestamp}",method = RequestMethod.POST)
    public void downFile(HttpServletResponse response,@RequestBody final String cipher,@PathVariable String tokenId,@PathVariable String fileId,@PathVariable long timestamp) throws Exception {
        String bucket = blockchainProp.getBucket();
        String filename = fileId;
        InputStream io=null;
        if (cipher == null) {
            Result result =contract.isFilePublic(tokenId,tokenId+"_001");
            if (result.isFlag() == true) {
                // get the public file
                try {
                    io = minIoUtils.downLoad(bucket, filename);
                     //这个是会弹出下载
                     byte[] data = IOUtils.toByteArray(io);
                     response.reset();
                     response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
                     response.addHeader("Content-Length", "" + data.length);
                     response.setContentType("application/octet-stream; charset=UTF-8");
                     IOUtils.write(data, response.getOutputStream());

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                    if (io != null) {
                        io.close();
                    }
                }
            }

        } else {
            Result result = contract.isSignValid(tokenId,fileId,timestamp, cipher);  //验证签名
            if (result.getCode() == StatusCode.OK) {
                // get the private file
                try {
                    io = minIoUtils.downLoad(bucket, filename);
                     //这个是会弹出下载
                     byte[] data = IOUtils.toByteArray(io);
                     response.reset();
                     response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");
                     response.addHeader("Content-Length", "" + data.length);
                     response.setContentType("application/octet-stream; charset=UTF-8");
                     IOUtils.write(data, response.getOutputStream());


                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    response.getOutputStream().flush();
                    response.getOutputStream().close();
                    if (io != null) {
                        io.close();
                    }
                }
            }
        }
    }

    @RequestMapping(value = "/downloadFileTest",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void downloadFile(HttpServletResponse response) {
        String filePath = "E://test_1646751742669_2022-03-08_386.png";
        File file = new File(filePath);
        InputStream in = null;
        if (file.exists()) {
            try {
                OutputStream out = response.getOutputStream();
                in = new FileInputStream(file);
                byte buffer[] = new byte[1024];
                int length = 0;
                while ((length = in.read(buffer)) >= 0) {
                    out.write(buffer, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


