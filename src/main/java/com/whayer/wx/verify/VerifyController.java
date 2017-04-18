package com.whayer.wx.verify;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whayer.wx.common.wechart.VerifyUtil;

/**
 * 验证微信token
 * @author duyu
 * @since 28-02-17
 */

@Controller
public class VerifyController {
	
	private final static Logger log = LoggerFactory.getLogger(VerifyController.class);
	
	@RequestMapping("/verify")
	@ResponseBody
	public void verify(String signature, String nonce, String echostr, String timestamp,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.debug("VerifyController.verify()");
		
		PrintWriter pw = response.getWriter();
		if(VerifyUtil.verifySignature(signature, timestamp, nonce)){
			
			pw.print(echostr);
		}
	}
}
