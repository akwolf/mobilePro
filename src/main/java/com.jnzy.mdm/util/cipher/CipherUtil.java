package com.jnzy.mdm.util.cipher;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.Key;

/**
 * 使用AES对文件进行加密和解密
 */
public class CipherUtil {

    /**
     * /**
     * 使用AES对文件进行加密和解密
     */
    private static String type = "AES";

    /**
     * 把文件srcFile加密后存储为destFile
     *
     * @param srcFile    加密前的文件
     * @param destFile   加密后的文件
     * @param privateKey 密钥
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static void encrypt(String srcFile, String destFile, String privateKey) throws GeneralSecurityException, IOException {
        Key key = getKey(privateKey);
        Cipher cipher = Cipher.getInstance(type + "/CBC/PKCS5Padding");
        //cipher.init(Cipher.ENCRYPT_MODE, key);
        cipher.init(cipher.ENCRYPT_MODE, key, getIvParameterSpec(privateKey));
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(mkdirFiles(destFile));
            crypt(fis, fos, cipher);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 把文件srcFile解密后存储为destFile
     *
     * @param srcFile    解密前的文件
     * @param destFile   解密后的文件
     * @param privateKey 密钥
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static void decrypt(String srcFile, String destFile, String privateKey) throws GeneralSecurityException, IOException {
        Key key = getKey(privateKey);
        Cipher cipher = Cipher.getInstance(type + "/CBC/PKCS5Padding");
        //cipher.init(Cipher.DECRYPT_MODE, key);
        cipher.init(cipher.DECRYPT_MODE, key, getIvParameterSpec(privateKey));
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(mkdirFiles(destFile));

            crypt(fis, fos, cipher);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 根据filePath创建相应的目录
     *
     * @param filePath 要创建的文件路经
     * @return file        文件
     * @throws IOException
     */
    private static File mkdirFiles(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

        return file;
    }

    /**
     * 生成指定字符串的密钥
     *
     * @param secret 要生成密钥的字符串
     * @return secretKey    生成后的密钥
     * @throws GeneralSecurityException
     */
    private static Key getKey(String secret) throws GeneralSecurityException {
        //http://wenku.baidu.com/link?url=9bGUmgPXPkrB4sTSxFHEOj4aNIN8wKOmJHh0uuHX8HjU_qmcmbbskOi0XbjKu2zg9uoqB_bboPoVrXPyfJXTk6VhZeRziS_RVRyaBGGCRFO
        /**
         KeyGenerator kgen = KeyGenerator.getInstance(type);
         kgen.init(128, new SecureRandom(secret.getBytes()));
         SecretKey secretKey = kgen.generateKey();
         */
        /**
         KeyGenerator keyGen = KeyGenerator.getInstance("AES");
         //SecureRandom random=SecureRandom.getInstance("SHA1PRNG");//需要自己手动设置
         SecureRandom random = SecureRandom.getInstance("SHA1", "CRYPTO");//
         random.setSeed(secret.getBytes());
         keyGen.init(128, random);
         SecretKey skey = keyGen.generateKey();
         byte[] raw = skey.getEncoded();
         SecretKeySpec secretKey = new SecretKeySpec(raw, type);
         */

        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "AES");
        return secretKey;
    }

    private static IvParameterSpec getIvParameterSpec(String secret) {
        IvParameterSpec zeroIv = new IvParameterSpec(secret.getBytes());
        return zeroIv;
    }

    /**
     * 加密解密流
     *
     * @param in     加密解密前的流
     * @param out    加密解密后的流
     * @param cipher 加密解密
     * @throws IOException
     * @throws GeneralSecurityException
     */
    private static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {
        int blockSize = cipher.getBlockSize() * 1000;
        int outputSize = cipher.getOutputSize(blockSize);

        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            } else {
                more = false;
            }
        }
        if (inLength > 0)
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        else
            outBytes = cipher.doFinal();
        out.write(outBytes);

    }

    //aes密文转换为base64密文
    public static void base64TOaes(String srcFile, String destFile) {
        try {
            File file = new File(srcFile);
            FileInputStream ins = new FileInputStream(file);
            FileOutputStream outputStream = new FileOutputStream(mkdirFiles(destFile));
            int count = 0;
            while (count == 0) {
                count = Integer.parseInt("" + file.length());//in.available();
            }
            byte[] bytes = new byte[count];

            int readCount = 0; // 已经成功读取的字节的个数
            while (readCount <= count) {
                if (readCount == count) break;
                readCount += ins.read(bytes, readCount, count - readCount);
            }

            //转换成字符串
            ins.close();
            String readContent = new String(bytes, 0, readCount, "utf-8"); // convert to string using bytes
            outputStream.write(Base64Util.decode(readContent));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //解密base64
    public static void base64ToAesByte(byte[] bytes,String destFile) throws Exception{
        FileOutputStream outputStream = new FileOutputStream(mkdirFiles(destFile));
        outputStream.write(bytes);
        outputStream.close();
    }
    //base64转换为aes密文
    public static void aesTOBase64(String srcFile, String destFile) {
        try {
            File file = new File(srcFile);
            FileInputStream ins = new FileInputStream(file);
            FileOutputStream outputStream = new FileOutputStream(mkdirFiles(destFile));
            int count = 0;
            while (count == 0) {
                count = Integer.parseInt("" + file.length());//in.available();
            }
            byte[] bytes = new byte[count];
            int readCount = 0; // 已经成功读取的字节的个数
            while (readCount <= count) {
                if (readCount == count) break;
                readCount += ins.read(bytes, readCount, count - readCount);
            }
            ins.close();
            outputStream.write(Base64Util.encode(bytes).getBytes("utf-8"));
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        String srcPath = "D:\\data\\upload\\file\\sms\\sms.txt";//源文件
        String base64Path = "D:\\data\\upload\\file\\sms\\aes869540025588422-13211111111.xml";//64编码文件
        String destPath = "D:\\data\\upload\\file\\sms\\appBase64.txt";//64编码文件AES加密后文件
        String path = "D:\\111\\base64-download.txt";//下载后的base64文件
        String jiepath = "D:\\111\\jiemi.txt";
        //加密成AES
//        CipherUtil.encrypt(srcPath, destPath, "0123456789123456");
////        AES to base64
//        CipherUtil.aesTOBase64(srcPath,destPath);

//        解密
        CipherUtil.base64TOaes(destPath,base64Path);
        CipherUtil.decrypt(base64Path, srcPath, "0123456789123456");


//         //先加密成AES
//         CipherUtil.encrypt(srcPath, destPath, "0123456789123456");
//         //根据AES密文生成base64文件
//         File srcFile=new File(destPath);//源文件
//         File base64File=new File(base64Path);//64编码文件
//         FileInputStream inputStream = new FileInputStream(srcFile);
//         FileOutputStream outputStream = new FileOutputStream(base64File);
//         int count = 0;
//         while (count == 0) {
//         count = Integer.parseInt(""+srcFile.length());//in.available();
//         }
//         byte[] bytes = new byte[count];
//         int readCount = 0; // 已经成功读取的字节的个数
//         while (readCount <= count) {
//         if(readCount == count)break;
//         readCount += inputStream.read(bytes, readCount, count - readCount);
//         }
//         outputStream.write(Base64Util.encode(bytes).getBytes("utf-8"));
//         inputStream.close();
//         outputStream.close();
//         //生成AES加密文件

         //通过http下载base64加密文件

        //生成AES解密文件
//        CipherUtil.decrypt(path, jiepath, "0123456789123456");
    }
}
