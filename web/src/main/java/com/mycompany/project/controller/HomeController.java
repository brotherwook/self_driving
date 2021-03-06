package com.mycompany.project.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.project.model.Rover;
import com.mycompany.project.service.RoverService;

@Controller
@RequestMapping("/home")
public class HomeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private RoverService roverService;
	
	@RequestMapping("/temp.do")
	public String temp() {
		LOGGER.info("실행");
		return "home/temp";
	}
	@RequestMapping("/main.do")
	public String main() {
		LOGGER.info("실행");
		return "home/main";
	}

	@RequestMapping("/redirectToMain.do")
	public String redirectToMain(HttpSession session) {
		LOGGER.info("실행");
		session.invalidate();
		return "redirect:main.do";
	}
	
	@RequestMapping("/hudTomain.do")
	public String main2(Rover rover) {
		LOGGER.info("실행");
		LOGGER.info("{}",rover.getRname());
		roverService.returnControl(rover);
		return "home/main";
	}


	@GetMapping("/controlView.do")
	public String controlView() {
		LOGGER.info("실행");
		return "home/controlView";
	}

	@RequestMapping("/signup.do")
	public String signup() {
		LOGGER.info("실행");
		return "member/sign_up";
	}

	@RequestMapping("/faceAuthentication.do")
	public String faceAuthentication() {
		LOGGER.info("실행");
		return "home/faceAuthentication";
	}

	@RequestMapping("/faceResist.do")
	public String faceResist() {
		LOGGER.info("실행");
		return "home/faceResist";
	}
}
