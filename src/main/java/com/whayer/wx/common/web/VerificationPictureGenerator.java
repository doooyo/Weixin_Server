package com.whayer.wx.common.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VerificationPictureGenerator {
  private static final Logger log        = LoggerFactory.getLogger(VerificationPictureGenerator.class);
  private static Random       random     = new Random();
  private static String       randString = "123456789ABCDEFGHMNPQRTWXY";                                // 随机产生的字符串

  private static int          width      = 100;                                                         // 图片宽
  private static int          height     = 40;                                                          // 图片高
  private static int          lineSize   = 20;                                                          // 干扰线数量
  private static int          stringNum  = 4;                                                           // 随机产生字符数量

  public static Map<String, BufferedImage> generate(int poolSize) {
    log.info("Generating a verification image pool (size : " + poolSize + ")");
    Map<String, BufferedImage> verificationPool = new HashMap<String, BufferedImage>(poolSize);
    String key;
    for (int i = 0; i < poolSize; i++) {
      key = roll();
      verificationPool.put(key, generateImage(key));
    }
    log.info("Generated verification image pool (" + poolSize + ").");
    return verificationPool;
  }

  private static String roll() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < stringNum; i++) {
      sb.append(randString.charAt(random.nextInt(randString.length())));
    }
    return sb.toString();
  }

  /**
   * 生成随机图片
   */
  private static BufferedImage generateImage(String key) {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
    Graphics2D g = (Graphics2D) image.getGraphics();
    g.fillRect(0, 0, width, height);
    g.setColor(getRandColor());
    // 绘制干扰线
    for (int i = 0; i <= lineSize; i++) {
      if (i == lineSize / 2) {
        g.setColor(getRandColor());
      }
      drowLine(g);
    }
    int x = 5;
    double f;
    g.setFont(getFont());
    for (char c : key.toCharArray()) {
      g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
      g.translate(random.nextInt(3), random.nextInt(3));
      f = Math.toRadians(random.nextInt(45) * (random.nextDouble() - 0.5d));
      g.rotate(f, x, 28);
      g.drawString(String.valueOf(c), x, 28);
      g.rotate(-f, x, 28);
      x += 22;
    }
    g.dispose();
    return image;
  }

  /*
   * 绘制干扰线
   */
  private static void drowLine(Graphics g) {
    int x = random.nextInt(width);
    int y = random.nextInt(height);
    int xl = random.nextInt(50);
    int yl = random.nextInt(50);
    g.drawLine(x, y, x + xl, y + yl);
  }

  /*
   * 获得字体
   */
  private static Font getFont() {
    for (int i = 0; i < 10; i++) {
      Font f = new Font(Font.DIALOG, Font.BOLD, 28);
      if (f.canDisplayUpTo(randString) == -1) {
        return f;
      }
    }
    return new Font("Arial Black", Font.BOLD, 24);
  }

  /*
   * 获得颜色
   */
  private static Color getRandColor() {
    int r = random.nextInt(100) + 100;
    int g = random.nextInt(100) + 100;
    int b = random.nextInt(100) + 100;
    return new Color(r, g, b);
  }

}
