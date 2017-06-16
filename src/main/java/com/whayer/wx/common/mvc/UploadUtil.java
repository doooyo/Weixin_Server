package com.whayer.wx.common.mvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.whayer.wx.common.X;
import com.whayer.wx.common.io.FileUtil;

public class UploadUtil {

	public static String uploadV1(String relativePath, HttpServletRequest request) {
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		
		List<String> list = new ArrayList<String>();
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null && file.getSize() > 0) {
					String originFileName = file.getOriginalFilename();
					String extension = FileUtil.getExtension(originFileName);
					originFileName = FileUtil.getFileNameWithOutExtension(originFileName);
					Pattern pattern = Pattern.compile(X.REGEX);
					Matcher matcher = pattern.matcher(originFileName);
					if (matcher.find()) {
						originFileName = matcher.replaceAll("_").trim();
					}
					originFileName = X.uuidPure8Bit()/*originFileName*/ + X.DOT + extension;
					String uploadPath = X.getConfig("file.upload.dir");
					uploadPath += "/header";
					X.makeDir(uploadPath);
					File targetFile = new File(uploadPath, originFileName);
				    try {
						file.transferTo(targetFile);
						list.add(originFileName);
					} catch (IllegalStateException | IOException e) {
						e.printStackTrace();
					}
					
				}

			}

		}
		return StringUtils.join(list, "|");
	}
}
