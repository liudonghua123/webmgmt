package com.bill99.yn.webmgmt.web.customer;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.bill99.yn.webmgmt.entity.Customer;
import com.bill99.yn.webmgmt.entity.Pos;
import com.bill99.yn.webmgmt.service.account.ShiroDbRealm.ShiroUser;
import com.bill99.yn.webmgmt.service.customer.CustomerService;
import com.bill99.yn.webmgmt.service.customer.PosService;
import com.google.common.collect.Maps;

/**
 * Customer管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /user/customer/{customer_id}/pos
 * Create page : GET /user/customer/{customer_id}/pos/create
 * Create action : POST /user/customer/{customer_id}/pos/create
 * Update page : GET /user/customer/{customer_id}/pos/update/{id}
 * Update action : POST /user/customer/{customer_id}/pos/update
 * Delete action : GET /user/customer/{customer_id}/pos/delete/{id}
 * 
 */
@Controller
@RequestMapping(value = "/user/customer/{customer_id}/pos")
public class PosController {

	private static final String PAGE_SIZE = "5";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private PosService posService;
	@Autowired
	private CustomerService customerService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@PathVariable Long customer_id, @RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		Page<Pos> poss = posService.findAll(customer_id, searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("poss", poss);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		model.addAttribute("customer", customerService.get(customer_id));
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "customer/posList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(@PathVariable Long customer_id, Model model) {
		model.addAttribute("pos", new Pos());
		model.addAttribute("action", "create");
		model.addAttribute("customer", new Customer(customer_id));
		return "customer/posForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@PathVariable Long customer_id, @Valid Pos newPos, RedirectAttributes redirectAttributes) {
		Customer customer = new Customer(customer_id);
		newPos.setCustomer(customer);

		posService.save(newPos);
		redirectAttributes.addFlashAttribute("message", "创建POS成功");
		return "redirect:/user/customer/" + customer_id + "/pos";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable Long customer_id, @PathVariable("id") Long id, Model model) {
		model.addAttribute("pos", posService.get(id));
		model.addAttribute("action", "update");
		model.addAttribute("customer", new Customer(customer_id));
		return "customer/posForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@PathVariable Long customer_id, @Valid @ModelAttribute("pos") Pos pos, RedirectAttributes redirectAttributes) {
		posService.save(pos);
		redirectAttributes.addFlashAttribute("message", "更新POS成功");
		return "redirect:/user/customer/" + customer_id + "/pos";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable Long customer_id, @PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		posService.delete(id);
		redirectAttributes.addFlashAttribute("message", "删除POS成功");
		return "redirect:/user/customer/" + customer_id + "/pos";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Pos对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getPos(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("pos", posService.get(id));
		}
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
