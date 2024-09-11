package com.market.bc.util;

import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.io.pem.PemObject;

import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaKeyUtils {

    public static final String PEM_PUBLICKEY = "PUBLIC KEY";

    public static final String PEM_PRIVATEKEY = "PRIVATE KEY";

    /**
     * generateRSAKeyPair
     *
     * @param keySize
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keySize) {
        KeyPairGenerator generator = null;
        SecureRandom random = new SecureRandom();
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            generator = KeyPairGenerator.getInstance("RSA", "BC");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        generator.initialize(keySize, random);

        KeyPair keyPair = generator.generateKeyPair();

        return keyPair;
    }

    /**
     * convertToPemKey
     *
     * @param publicKey
     * @param privateKey
     * @return
     */
    public static String convertToPemKey(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        if (publicKey == null && privateKey == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();

        try {
            PEMWriter pemWriter = new PEMWriter(stringWriter);

            if (publicKey != null) {

                pemWriter.writeObject(new PemObject(PEM_PUBLICKEY,
                        publicKey.getEncoded()));
            }
            else {//此处产生的privatekey 的格式是 PKCS#8 的格式
                pemWriter.writeObject(new PemObject(PEM_PRIVATEKEY,
                        privateKey.getEncoded()));
            }
            pemWriter.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    public static byte[] sign(String data, byte[] privateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(privateKey2);
        signature.update(data.getBytes());
        return signature.sign();

    }
    //后台测试签名的时候 要和前台保持一致，所以需要将结果转换
    public static String bytes2String(byte[] bytes) {
        StringBuilder string = new StringBuilder();
        for (byte b : bytes) {
            String hexString = Integer.toHexString(0x00FF & b);
            string.append(hexString.length() == 1 ? "0" + hexString : hexString);
        }
        return string.toString();
    }

    public static boolean verify(String data,
                                 byte[] publicKey,
                                 byte[] signatureResult) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);

            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(publicKey2);
            signature.update(data.getBytes());

            return signature.verify(signatureResult);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    //前台的签名结果是将byte 中的一些 负数转换成了正数，    //但是后台验证的方法需要的又必须是转换之前的
    public static byte[] hexStringToByteArray(String data) {
        int k = 0;
        byte[] results = new byte[data.length() / 2];
        for (int i = 0; i + 1 < data.length(); i += 2, k++) {
            results[k] = (byte) (Character.digit(data.charAt(i), 16) << 4);
            results[k] += (byte) (Character.digit(data.charAt(i + 1), 16));
        }
        return results;
    }

    public  static  void witeKeyStringToFile(RSAPrivateKey privateKey,RSAPublicKey publicKey,String filePath){
        try {
            // 得到公钥字符串
            String publicKeyString = com.market.bc.util.Base64.encode(publicKey.getEncoded());
            System.out.println("publicKey:"+publicKeyString);
            // 得到私钥字符串
            String privateKeyString = com.market.bc.util.Base64.encode(privateKey.getEncoded());
            System.out.println("privateKey:"+privateKeyString);

            // 将密钥对写入到文件
            FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
            FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
            BufferedWriter pubbw = new BufferedWriter(pubfw);
            BufferedWriter pribw = new BufferedWriter(prifw);
            pubbw.write(publicKeyString);
            pribw.write(privateKeyString);
            pubbw.flush();
            pubbw.close();
            pubfw.close();
            pribw.flush();
            pribw.close();
            prifw.close();
        } catch (Exception e) {
            System.out.println("write key to file failed!!");
            e.printStackTrace();
        }
    }

    public static String loadKeyStringByFile(String path) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path
            ));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    public static String getPrivateKeyStrFromPem(String filepath) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String s = br.readLine();
        String str = "";
        s = br.readLine();
        while (s.charAt(0) != '-') {
            str += s + "\r";
            s = br.readLine();
        }
        return str;
    }
    public static String getPublicKeyStrFromPem(String filepath) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String s = br.readLine();
        String str = "";
        s = br.readLine();
        while (s.charAt(0) != '-') {
            str += s + "\r";
            s = br.readLine();
        }
        return str;
    }

    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
            throws Exception {
        try {
            byte[] buffer = com.market.bc.util.Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
            throws Exception {
        try {
            byte[] buffer = com.market.bc.util.Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }




    public static RSAPrivateKey getRSAPrivateKeyFromPem (String filePath) throws IOException {
        RSAPrivateKey rsaPrivateKey=null;
        PrivateKey privateKey=null;
        // 创建PEM解析器
        PEMParser pemParser = new PEMParser(new FileReader(filePath));
        try {
//            // 注册BouncyCastle提供者
//            Security.addProvider(new BouncyCastleProvider());



            // 解析PEM对象
            Object object = pemParser.readObject();

            // 确保解析的对象是PEMKeyPair
            if (object instanceof PEMKeyPair) {
                PEMKeyPair pemKeyPair = (PEMKeyPair) object;
                JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

                // 转换成Java PrivateKey
                KeyPair keyPair = converter.getKeyPair(pemKeyPair);
                privateKey = keyPair.getPrivate();

                // 使用私钥...
                System.out.println("Private Key read successfully");
                try {
//                    byte[] buffer = com.market.bc.util.Base64.decode(privateKeyStr);
                    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    return  (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
                } catch (NoSuchAlgorithmException e) {
                    throw new Exception("无此算法");
                } catch (InvalidKeySpecException e) {
                    throw new Exception("私钥非法");
                } catch (NullPointerException e) {
                    throw new Exception("私钥数据为空");
                }

            } else {
                System.out.println("Not a valid PEM private key format");
            }



//            return rsaPrivateKey;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭解析器
            pemParser.close();
            return rsaPrivateKey;
        }

    }





//    public static void main(String[] args) throws Exception {
//        String filepath = ClassLoader.getSystemResource("").getPath();
//        System.out.println("Class path: " + filepath);
//
//        String str ="{timestamp:1725609056399,tokenId:1831514246849105920,fileId:1831514246849105920_001}";
//
//        boolean ifCreateNewKey=false;
//        if(ifCreateNewKey) {
//            KeyPair k = generateRSAKeyPair(1024);
//            // 得到私钥
//            RSAPrivateKey privateKeyObject = (RSAPrivateKey) k.getPrivate();
//            // 得到公钥
//            RSAPublicKey publicKeyObject = (RSAPublicKey) k.getPublic();
//
//            witeKeyStringToFile(privateKeyObject, publicKeyObject, filepath);
//
//
//            String publicKeyPem = convertToPemKey((RSAPublicKey) k.getPublic(), null);
//            String privateKeyPem = convertToPemKey(null, (RSAPrivateKey) k.getPrivate());
//
//            System.out.println("publicKeyPEM__\n" + publicKeyPem);
//            System.out.println("privateKeyPEM__\n" + privateKeyPem);
//        }
//
//        String publicKeyStrLoad=loadKeyStringByFile(filepath + "/publicKey.keystore");
//        String privateKeyStrLoad=loadKeyStringByFile(filepath + "/privateKey.keystore");
//        System.out.println("publicKeyStrLoad__\n" + publicKeyStrLoad);
//        System.out.println("privateKeyStrLoad__\n" + privateKeyStrLoad);
//
//        RSAPrivateKey priKey =loadPrivateKeyByStr(loadKeyStringByFile(filepath+ "/privateKey.keystore"));
//        RSAPublicKey  pubKey= loadPublicKeyByStr(loadKeyStringByFile(filepath+ "/publicKey.keystore"));
//        // 得到公钥字符串
//        String publicKeyString = com.market.bc.util.Base64.encode(pubKey.getEncoded());
//        System.out.println("publicKey:"+publicKeyString);
//        // 得到私钥字符串
//        String privateKeyString = com.market.bc.util.Base64.encode(priKey.getEncoded());
//        System.out.println("privateKey:"+privateKeyString);
//
//
//
//
//        try {
//            byte[] signautreResult = sign(str, priKey.getEncoded());
//            String signatureStr = bytes2String(signautreResult);
//            System.out.println("signStr1: "+signatureStr);
//            System.out.println("signStr2: "+signautreResult.toString());
//            System.out.println("signStr3: "+com.market.bc.util.Base64.encode(signautreResult));
//            byte[] signatureResult2 = hexStringToByteArray(signatureStr);
//
//
//            boolean b = verify(str, pubKey.getEncoded(), signatureResult2);
//            System.out.print("iii   " + b);
//
////            str="a";
//            boolean c = verify(str, pubKey.getEncoded(), signautreResult);
//            System.out.print("\niii   " + c);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}