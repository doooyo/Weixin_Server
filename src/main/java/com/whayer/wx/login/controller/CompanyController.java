package com.whayer.wx.login.controller;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.whayer.wx.common.X;
import com.whayer.wx.common.io.FileUtil;
import com.whayer.wx.common.mvc.BaseController;
import com.whayer.wx.common.mvc.Box;
import com.whayer.wx.common.mvc.ResponseCondition;
import com.whayer.wx.login.service.CompanyService;
import com.whayer.wx.login.vo.Company;
import com.whayer.wx.login.vo.Role;

@RequestMapping(value = "/company")
@Controller
public class CompanyController extends BaseController{
	
	private final static Logger log = LoggerFactory.getLogger(CompanyController.class);
	
	@Resource
	private CompanyService companyService;
	
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseCondition getList(HttpServletRequest request, HttpServletResponse response) {
		log.info("CompanyController.getList()");
		
		Box box = loadNewBox(request);
		
		String name = box.$p("name");
		
		PageInfo<Company> pi = companyService.getCompanyList(name, box.getPagination());
		
		return pagerResponse(pi);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition save(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("CompanyController.save()");
		
		Box box = loadNewBox(request);
		
		String name = box.$p("name");
		String code = box.$p("code");
		String beginTime = box.$p("beginTime");
		String endTime = box.$p("endTime");
		String _discount = box.$p("discount");
		
		if(isNullOrEmpty(name) || isNullOrEmpty(code)
				|| isNullOrEmpty(beginTime) || isNullOrEmpty(endTime)
				|| isNullOrEmpty(_discount)){
			return getResponse(X.FALSE);
		}
		
		float discount = X.string2float(_discount);
		
		Date activeBeginTime = X.string2date(beginTime, X.TIMED);
		Date activeEndTime = X.string2date(endTime, X.TIMED);
		
		ResponseCondition res = getResponse(X.FALSE);
		if(discount <= 0 || discount > 1){
			res.setErrorMsg("折扣必须大于0且小于1");
			return res;
		}
		
		
		if(activeBeginTime.compareTo(activeEndTime) > 0){
			res.setErrorMsg("活动结束日期不能小于活动开始日期");
			return res;
		}
		
		code = code.trim();
		name = name.trim();
		
		Company existCompany = companyService.findByCode(code);
		if(!isNullOrEmpty(existCompany)){
			res.setErrorMsg("集团用户已存在");
			return res;
		}
		
		/**
		 * Company 与 Role 共用同一个id, 以便更新删除操作
		 */
		String id = X.uuidPure();
		
		Company company = new Company();
		company.setId(id);
		company.setCode(code);
		company.setName(name);
		company.setActiveBeginTime(activeBeginTime);
		company.setActiveEndTime(activeEndTime);
		company.setDiscount(discount);
		
		if(!isNullOrEmpty(file) && file.getSize() > 0){
			String originFileName = file.getOriginalFilename();
			String extension = FileUtil.getExtension(originFileName);
			originFileName = FileUtil.getFileNameWithOutExtension(originFileName);
			//Pattern pattern = Pattern.compile(X.REGEX);
			//Matcher matcher = pattern.matcher(originFileName);
			//if (matcher.find()) {
			//	originFileName = matcher.replaceAll("_").trim();
			//}
			originFileName = X.uuidPure8Bit()/*originFileName*/ + X.DOT + extension;
			
			if (file.getSize() == 0 || file.isEmpty()) {
				log.error("文件不能为空");
				res.setErrorMsg("文件不能为空");
				return res;
			}
			// check if too large
			int maxSize = X.string2int(X.getConfig("file.upload.max.size"));
			if (file.getSize() > maxSize) {
				log.error("文件太大");
				res.setErrorMsg("文件太大");
				return res;
			}
			
			String uploadPath = X.getConfig("file.upload.dir");
			uploadPath += "/company";//header
			X.makeDir(uploadPath);
			File targetFile = new File(uploadPath, originFileName);
		    file.transferTo(targetFile);
		    
		    company.setLogo(originFileName);
		}
		
		Role role = new Role();
		role.setId(id);
		role.setName(name.trim());
		role.setCode(code);
		
		int result = companyService.save(company, role);
		if(result > 1)
			return getResponse(X.TRUE);
		else{
			res.setErrorMsg("保存集团用户失败");
			log.error("保存集团用户失败");
			return res;
		}
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition update(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("CompanyController.update()");
		
		Box box = loadNewBox(request);
		String id = box.$p("id");
		String name = box.$p("name");
		String code = box.$p("code");
		
		String beginTime = box.$p("beginTime");
		String endTime = box.$p("endTime");
		String _discount = box.$p("discount");
		
		if(isNullOrEmpty(id) || isNullOrEmpty(name) 
				|| isNullOrEmpty(code) || isNullOrEmpty(_discount)
				|| isNullOrEmpty(beginTime) || isNullOrEmpty(endTime)){
			return getResponse(X.FALSE);
		}
		
		float discount = X.string2float(_discount);
		
		Date activeBeginTime = X.string2date(beginTime, X.TIMED);
		Date activeEndTime = X.string2date(endTime, X.TIMED);
		
		ResponseCondition res = getResponse(X.FALSE);
		if(discount <= 0 || discount > 1){
			res.setErrorMsg("折扣必须大于0且小于1");
			return res;
		}
		
		if(activeBeginTime.compareTo(activeEndTime) > 0){
			res.setErrorMsg("活动结束日期不能小于活动开始日期");
			return res;
		}
		
		name = name.trim();
		code = code.trim();
		
		//TODO 目前只更新了Company,没有更新Role
		Company company = new Company();
		company.setId(id);
		company.setName(name);
		company.setCode(code);
		company.setDiscount(discount);
		company.setActiveBeginTime(activeBeginTime);
		company.setActiveEndTime(activeEndTime);
		
		if(!isNullOrEmpty(file) && file.getSize() > 0){
			Company originCompany = companyService.findById(id);
			String originFileName = file.getOriginalFilename();
			String extension = FileUtil.getExtension(originFileName);
			originFileName = FileUtil.getFileNameWithOutExtension(originFileName);

			originFileName = X.uuidPure8Bit()/*originFileName*/ + X.DOT + extension;
			
			if (file.getSize() == 0 || file.isEmpty()) {
				log.error("文件不能为空");
				res.setErrorMsg("文件不能为空");
				return res;
			}
			// check if too large
			int maxSize = X.string2int(X.getConfig("file.upload.max.size"));
			if (file.getSize() > maxSize) {
				log.error("文件太大");
				res.setErrorMsg("文件太大");
				return res;
			}
			
			String uploadPath = X.getConfig("file.upload.dir");
			uploadPath += "/company";//header
			X.makeDir(uploadPath);
			File targetFile = new File(uploadPath, originFileName);
		    file.transferTo(targetFile);
		    
		    if(!isNullOrEmpty(originCompany.getLogo())){
		    	//删除旧logo
			    File oldFile = new File(uploadPath, originCompany.getLogo());
			    oldFile.delete();
		    }
		    
		    company.setLogo(originFileName);
		}
		
		
		Role role = new Role();
		role.setId(id);
		role.setName(name);
		role.setCode(code);
		
		int result = companyService.update(company, role);
		if(result > 1)
			return getResponse(X.TRUE);
		else{
			res.setErrorMsg("更新集团用户失败");
			log.error("更新集团用户失败");
			return res;
		}
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseCondition delete(HttpServletRequest request, HttpServletResponse response) {
		log.info("CompanyController.delete()");
		
		Box box = loadNewBox(request);
		
		String id = box.$p("id");
		
		if(isNullOrEmpty(id)){
			return getResponse(X.FALSE);
		}
		
		//删除logo
		Company company = companyService.findById(id);
		if(!isNullOrEmpty(company)){
			String logo = company.getLogo();
			if(!isNullOrEmpty(logo)){
				String uploadPath = X.getConfig("file.upload.dir");
				uploadPath += "/company";
				File oldFile = new File(uploadPath, logo);
			    oldFile.delete();
			}
		}
		
		ResponseCondition res = getResponse(X.FALSE);
		
		int result = companyService.deleteById(id);
		
		if(result > 1){
			return getResponse(X.TRUE);
		}
		else{
			res.setErrorMsg("删除集团用户失败");
			log.error("删除集团用户失败");
			return res;
		}
	}
	
}
