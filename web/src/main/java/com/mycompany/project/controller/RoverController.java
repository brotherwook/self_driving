package com.mycompany.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.project.model.Rover;
import com.mycompany.project.service.RoverService;

@Controller
@RequestMapping("/rover")
public class RoverController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoverController.class);

	@Autowired
	private RoverService roverService;

	@RequestMapping("/redirectToMain.do")
	public String redirectToMain() {
		LOGGER.info("실행");
		return "redirect:/home/main.do";
	}

	@RequestMapping("/roverList.do")
	public String roverList(Model model, HttpSession session) {
		LOGGER.info("실행");
		// 다음 화면에 출력할 등록된 로버들의 목록 가져오기
		List<Rover> list = roverService.getRoverList();
		String curUser = (String)session.getAttribute("sessionMid");
		for(Rover x : list) {
			if(x.getRuser().equals(curUser)) {
				roverService.returnControl(x);
			}
		}
		// model 객체를 이용해서 request범위에 list객체 저장 후 jsp에서 사용한다.
		model.addAttribute("roverList", list);

		return "rover/roverList";
	}

	@RequestMapping("/resisterRoverForm.do")
	public String resisterRoverForm() {
		LOGGER.info("실행");
		return "rover/resisterRoverForm";
	}

	@RequestMapping("/resisterRover.do")
	public String resisterRover(Rover rover) {
		LOGGER.info("실행");
		roverService.resisterRover(rover);
		return "redirect:/rover/roverList.do";
	}

	@RequestMapping("/roverHud.do")
	public String roverHud(Model model, String rname, HttpSession session) {
		LOGGER.info("실행");
		//LOGGER.info(rname);
		String ruser = (String)session.getAttribute("sessionMid");
		roverService.updateRuser(rname, ruser);
		Rover rover = roverService.getRover(rname);
		rover.setRuser(ruser);
		model.addAttribute("rover", rover);
		return "rover/roverHud";
	}

	@RequestMapping("/deleteRover.do")
	public String deleteRover(String rname) {
		LOGGER.info("실행");
		roverService.deleteRover(rname);
		return "redirect:/rover/roverList.do";
	}

	@RequestMapping("/aiMode.do")
	public String aiMode(Model model, Rover rover) {
		LOGGER.info("실행");
		model.addAttribute("rover",rover);
		return "rover/aiMode";
	}

	@RequestMapping("/manualMode.do")
	public String manualMode(Model model, Rover rover) {
		LOGGER.info("실행");
		model.addAttribute("rover",rover);
		return "rover/manualMode";
	}

	@RequestMapping("/navigationMode.do")
	public String navigationMode(Model model, Rover rover) {
		LOGGER.info("실행");
		return "rover/navigationMode";
	}


}
