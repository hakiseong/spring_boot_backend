package ssg.com.a.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import ssg.com.a.dto.MemberDto;
import ssg.com.a.service.MemberService;

@RestController
public class MemberController {

	@Autowired
	MemberService service;
	
	@GetMapping("allmember")
	public List<MemberDto> allmember() {
		System.out.println("MemberController allmember " + new Date());
		
		return service.allmember();
	}
	
	@GetMapping("idcheck")
	public boolean idcheck(String id) {
		System.out.println("MemberController idcheck " + new Date());
		
		return service.idcheck(id);
	}
	
	@PostMapping("login")
	public String login(MemberDto dto, HttpServletRequest request) {
		System.out.println("MemberController login " + new Date());
		
		MemberDto user = service.login(dto);
		
		if(user != null) {
			request.getSession().invalidate(); //Session 삭제
			System.out.println("세션이 삭제됨:" + new Date());
	        
			request.getSession().setAttribute("login", user);
			System.out.println("세션이 생성됨:" + new Date());
			request.getSession().setMaxInactiveInterval(60*60*24);
			return "로그인에 성공하였습니다!";
		}else {
			return "로그인에 실패하였습니다!";
		}
	}
	
	@PostMapping("regi")
	public String regiAf(MemberDto dto) {
		System.out.println("LoginController regiAf " + new Date());
		System.out.println(dto.toString());
		
		boolean isS = service.regi(dto);
		
		if(isS) {
			return "회원가입이 완료되었습니다!";
		}else {
			return "회원가입에 실패하셨습니다!";
		}		
	}
}
