package com.whayer.wx.pay2.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.springframework.core.io.Resource;

import com.whayer.wx.base.UnitTestBase;
import com.whayer.wx.common.bean.SpringFactory;

public class QRCodeKitTest extends UnitTestBase{

	public QRCodeKitTest() {
		super("classpath:IOC.xml");
	}
	
	@Test
	public void test() throws IOException {
		QRCodeKit qRCodeKit = super.getBean("qRCodeKit");
		SpringFactory springFactory = super.getBean("springFactory");
		System.out.println(qRCodeKit);
		
		String data = "http://www.baidu.com";
		Resource resource = springFactory.getResource("classpath:image/logo.jpg");
        File logoFile = resource.getFile();
        BufferedImage image = QRCodeKit.createQRCodeWithLogo(data, logoFile);
        ImageIO.write(image, "gif", new File("wx.gif"));
	}

}
