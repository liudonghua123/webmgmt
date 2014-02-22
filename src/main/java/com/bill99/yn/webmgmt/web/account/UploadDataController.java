package com.bill99.yn.webmgmt.web.account;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.bill99.yn.webmgmt.entity.Log;
import com.bill99.yn.webmgmt.entity.LogAction;
import com.bill99.yn.webmgmt.entity.TransactionDetail;
import com.bill99.yn.webmgmt.entity.TransactionSummary;
import com.bill99.yn.webmgmt.service.account.LogService;
import com.bill99.yn.webmgmt.service.account.ShiroDbRealm.ShiroUser;
import com.bill99.yn.webmgmt.service.transaction.TransactionDetailService;
import com.bill99.yn.webmgmt.service.transaction.TransactionSummaryService;
import com.bill99.yn.webmgmt.util.ExcelUtil;
import com.bill99.yn.webmgmt.util.Utils;
import com.google.common.collect.Maps;

/**
 * Log管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /admin/data/
 * Delete action : GET /admin/data/log/delete/{id}
 * 
 */
@Controller
@RequestMapping(value = "/admin/data")
public class UploadDataController {

	private static final String PAGE_SIZE = "5";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private LogService logService;
	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private TransactionSummaryService transactionSummaryService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<Log> logs = logService.findAllByLogAction(pageNumber, pageSize, sortType, LogAction.UPLOAD_DATA, LogAction.PARSE_DATA, LogAction.STORE_DATA);

		model.addAttribute("logs", logs);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "account/uploadDataList";
	}
	
	@RequestMapping(value = "uploadData", method = RequestMethod.POST)
	public String uploadData(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if(!file.isEmpty()) {
			String uploadPath = saveUploadFile(file, request);
			logService.save(new Log(SecurityUtils.getSubject().getPrincipal().toString(), LogAction.UPLOAD_DATA, new Date(), "上传数据文件成功"));
			redirectAttributes.addFlashAttribute("message", "上传数据文件成功");
			//获取之前存储数据的日期
			List<String> storedDateString = logService.get(LogAction.STORE_DATA);
			//解析上传的数据
			ExcelUtil excelUtil = new ExcelUtil(uploadPath, storedDateString);
			List<TransactionDetail> transactionDetails = excelUtil.getTransactionDetails();
			List<TransactionSummary> transactionSummarys = excelUtil.getTransactionSummarys();
			logService.save(new Log(SecurityUtils.getSubject().getPrincipal().toString(), LogAction.PARSE_DATA, new Date(), "解析数据文件成功"));
			//保存解析的数据到数据库
			transactionDetailService.save(transactionDetails);
			transactionSummaryService.save(transactionSummarys);
			Set<String> parsedDateString = excelUtil.getParsedDates();
			for(String parsedDate : parsedDateString) {
				logService.save(new Log(SecurityUtils.getSubject().getPrincipal().toString(), LogAction.STORE_DATA, new Date(), parsedDate));
			}
		}
		else {
			redirectAttributes.addFlashAttribute("message", "上传数据文件失败");
			logService.save(new Log(SecurityUtils.getSubject().getPrincipal().toString(), LogAction.UPLOAD_DATA, new Date(), "上传数据文件失败"));
		}
		return "redirect:/admin/data";
	}


	@RequestMapping(value = "log/delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		logService.delete(id);
		redirectAttributes.addFlashAttribute("message", "删除LOG成功");
		return "redirect:/admin/data";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Log对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getLog(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("log", logService.get(id));
		}
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	
	private String saveUploadFile(MultipartFile file, HttpServletRequest request) {
		String filePath = "static/resources/"
				+ file.getOriginalFilename();
		File savedFile = new File(request.getSession().getServletContext().getRealPath("")
				+ "/" + filePath);
		try {
			file.transferTo(savedFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return savedFile.getPath();
	}
}
