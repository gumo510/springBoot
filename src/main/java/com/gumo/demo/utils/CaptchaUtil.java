/**
 * @author gumo
 * 文件名：CaptchaUtil
 * 描述：验证码工具类
 */
package com.gumo.demo.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaUtil {
    private static char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 生成图形验证码
     *
     * @return
     */
    public static Object[] getGraphCaptcha() {

        int width = 110;
        int height = 36;
        int lines = 8;
        int red, green, blue;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        //设置背景色 灰十九

        g.setColor(new Color(232, 232, 232));
        //画背景
        g.fillRect(0, 0, width, height);
        //填充指定的矩形。使用图形上下文的当前颜色填充该矩形

        g.setFont(new Font("黑体", Font.BOLD, 25));
        StringBuffer sb = new StringBuffer();


        Random r = new Random();
        //字体
        for (int i = 0; i < 4; i++) {

            String strRand = String.valueOf(codeSequence[r.nextInt(codeSequence.length)]);
            sb.append(strRand);
            g.setColor(new Color(0, 0, 0));
            g.drawString(strRand, i * 25, 20 + r.nextInt(10));
        }

        //干扰线
        for (int i = 0; i < lines; i++) {
            // 设置随机开始和结束坐标
            //x坐标开始
            int xs = 20 + r.nextInt(width);
            //y坐标开始
            int ys = 20 + r.nextInt(height);
            //x坐标结束
            int xe = xs + r.nextInt(width);
            //y坐标结束
            int ye = ys + r.nextInt(height);

            // 产生随机的颜色值，让输出的每个干扰线的颜色值都将不同。
            red = r.nextInt(255);
            green = r.nextInt(255);
            blue = r.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }
        //类似于流中的close()带动flush()---把数据刷到img对象当中

        g.dispose();

        return new Object[]{sb.toString(), image};
    }
}