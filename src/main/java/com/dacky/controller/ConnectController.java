package com.dacky.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dacky.entity.Connect;
import com.dacky.service.ConnectService;

@Controller
public class ConnectController {
	@Autowired
	private ConnectService connectService;
	@GetMapping
	public String index(Model model)
	{
		List<Connect> connects= connectService.findAll();
		model.addAttribute("connects", connects);
		return "index";
	}
	@GetMapping("/delete/{id}")
	public String deleted(@PathVariable("id") Long id)
	{
		connectService.delete(id);
		return "redirect:" + "/";
	}
	@PostMapping
	public String addNew(Model model,HttpServletRequest request) {
		String driverStart = request.getParameter("driverStart");
		String urlStart = request.getParameter("urlStart");
		String usernameStart = request.getParameter("usernameStart");
		String passwordStart = request.getParameter("passwordStart");
		String tableTemp = request.getParameter("tableNameTemp");
		String driverEnd = request.getParameter("driverEnd");
		String urlEnd = request.getParameter("urlEnd");
		String usernameEnd = request.getParameter("usernameEnd");
		String passwordEnd = request.getParameter("passwordEnd");
		
		Connect connect = new Connect();
		connect.setDriverStart(driverStart);
		connect.setUrlStart(urlStart);
		connect.setUrlStart(urlStart);
		connect.setUsernameStart(usernameStart);
		connect.setPasswordStart(passwordStart);
		connect.setTableNameTemp(tableTemp);
		connect.setDriverEnd(driverEnd);
		connect.setUrlEnd(urlEnd);
		connect.setUrlEnd(urlEnd);
		connect.setUsernameEnd(usernameEnd);
		connect.setPasswordEnd(passwordEnd);
		connectService.save(connect);
		return "redirect:" + "/";
	}
}
