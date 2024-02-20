package ssg.com.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.a.dao.MemberDao;
import ssg.com.a.dto.MemberDto;

@Service
@Transactional
public class MemberService {
	
	@Autowired
	MemberDao dao;
	
	public List<MemberDto> allmember() {
		return dao.allMember();
	}
	
	public boolean idcheck(String id) {
		int count = dao.idcheck(id);
		return count>0?true:false;
	}
	
	public MemberDto login(MemberDto dto) {
		return dao.login(dto);
	}
	
	public boolean regi(MemberDto dto) {
		int count = dao.regi(dto);
		return count>0?true:false;
	}

}
