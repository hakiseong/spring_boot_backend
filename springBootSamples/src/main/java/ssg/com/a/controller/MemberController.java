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
	public MemberDto login(MemberDto mem) {
		System.out.println("MemberController login " + new Date());
		
		return service.login(mem);
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
