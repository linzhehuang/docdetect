package io.docdetect.docdetect.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping("/ping")
	public String test() {
		return "OK";
	}
	
	@RequestMapping("/session")
	public String session(@RequestParam("user") String user, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			session.setAttribute("user", user);
		} else {
			StringBuilder ret = new StringBuilder();
			ret.append(user).append("'s session is ").append(session.toString());
			return ret.toString();
		}
		return "NULL";
	}
}
