package com.bill99.yn.webmgmt.web.index;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

import com.bill99.yn.webmgmt.entity.Task;
import com.bill99.yn.webmgmt.entity.User;
import com.bill99.yn.webmgmt.service.account.ShiroDbRealm.ShiroUser;
import com.bill99.yn.webmgmt.service.task.TaskService;
import com.bill99.yn.webmgmt.util.Utils;

import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/")
public class IndexController {

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.hasRole(Utils.ADMIN_ROLE_NAME)) {
			return "redirect:/admin/user";
		}
		else if(currentUser.hasRole(Utils.USER_ROLE_NAME)) {
			return "redirect:/user/customer";
		}
		else {
			return "common/401";
		}
	}
}