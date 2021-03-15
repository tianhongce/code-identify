package com.me;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static String testDoOCR_ImageByte(String path) {
        byte[] imageByte = image2byte(path);
        logger.info("doOCR on a jpg image");
        try {
            InputStream sbs = new ByteArrayInputStream(imageByte);
            BufferedImage img = ImageIO.read(sbs);
            ITesseract instance = new Tesseract();
            //设置语言库所在的文件夹位置，最好是绝对的，不然加载不到就直接报错了
            instance.setDatapath("src\\main\\resources\\tessdata");
            //设置使用的语言库类型：chi_sim 中文简体
            instance.setLanguage("eng");
            String result = instance.doOCR(img);
            logger.info("扫描的文本："+result);
            return result;
        } catch (Exception e) {
            logger.error("扫描图片文本错误:{}", e);
        }
        return "";
    }

    //图片到byte数组
    public static byte[] image2byte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }
    public static void main(String[] args) {
        System.out.println("hello");
        String s = testDoOCR_ImageByte("D:\\testimages\\5.jpg");
        System.out.println("结果:"+s);
    }
}
