package com.bill99.yn.webmgmt.web.customer;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.bill99.yn.webmgmt.entity.TransactionDetail;
import com.bill99.yn.webmgmt.entity.TransactionSummary;
import com.bill99.yn.webmgmt.service.account.ShiroDbRealm.ShiroUser;
import com.bill99.yn.webmgmt.service.customer.CustomerService;
import com.bill99.yn.webmgmt.service.transaction.TransactionDetailService;
import com.bill99.yn.webmgmt.service.transaction.TransactionSummaryService;
import com.google.common.collect.Maps;

/**
 * Transaction管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /user/customer/{customer_id}/transactionDetail
 * List page : GET /user/customer/{customer_id}/transactionSummary
 */
@Controller
@RequestMapping(value = "/user/customer")
public class TransactionController {

	private static final String PAGE_SIZE = "5";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private TransactionDetailService transactionDetailService;
	@Autowired
	private TransactionSummaryService transactionSummaryService;
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "transactionDetail", method = RequestMethod.GET)
	public String transactionDetailList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, 
			@RequestParam(defaultValue = "2000-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDateTime, 
			@RequestParam(defaultValue = "2050-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDateTime, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<TransactionDetail> transactionDetails = transactionDetailService.findAllByUserId(getCurrentUserId(), startDateTime, endDateTime, new PageRequest(pageNumber - 1, pageSize));

		model.addAttribute("transactionDetails", transactionDetails);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("startDateTime", startDateTime);
		model.addAttribute("endDateTime", endDateTime);
//		model.addAttribute("customer", customerService.get(customer_id));
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "customer/transactionDetailList";
	}

	@RequestMapping(value = "{customer_id}/transactionDetail", method = RequestMethod.GET)
	public String transactionDetailList(@PathVariable Long customer_id, @RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, 
			@RequestParam(defaultValue = "2000-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDateTime, 
			@RequestParam(defaultValue = "2050-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDateTime, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<TransactionDetail> transactionDetails = transactionDetailService.findAllByCustomerId(customer_id, startDateTime, endDateTime, new PageRequest(pageNumber - 1, pageSize));

		model.addAttribute("transactionDetails", transactionDetails);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("startDateTime", startDateTime);
		model.addAttribute("endDateTime", endDateTime);
		model.addAttribute("customer", customerService.get(customer_id));
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "customer/transactionDetailList";
	}

	@RequestMapping(value = "transactionSummary", method = RequestMethod.GET)
	public String transactionSummaryList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, 
			@RequestParam(defaultValue = "2000-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDateTime, 
			@RequestParam(defaultValue = "2050-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDateTime, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<TransactionSummary> transactionSummarys = transactionSummaryService.findAllByUserId(getCurrentUserId(), startDateTime, endDateTime, new PageRequest(pageNumber - 1, pageSize));

		model.addAttribute("transactionSummarys", transactionSummarys);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("startDateTime", startDateTime);
		model.addAttribute("endDateTime", endDateTime);
//		model.addAttribute("customer", customerService.get(customer_id));
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "customer/transactionSummaryList";
	}

	@RequestMapping(value = "{customer_id}/transactionSummary", method = RequestMethod.GET)
	public String transactionSummaryList(@PathVariable Long customer_id, @RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, 
			@RequestParam(defaultValue = "2000-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startDateTime, 
			@RequestParam(defaultValue = "2050-01-01 00:00:00") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endDateTime, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<TransactionSummary> transactionSummarys = transactionSummaryService.findAllByCustomerId(customer_id, startDateTime, endDateTime, new PageRequest(pageNumber - 1, pageSize));

		model.addAttribute("transactionSummarys", transactionSummarys);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("startDateTime", startDateTime);
		model.addAttribute("endDateTime", endDateTime);
		model.addAttribute("customer", customerService.get(customer_id));
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "customer/transactionSummaryList";
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
