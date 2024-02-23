package ssg.com.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.a.dto.BbsComment;
import ssg.com.a.dto.BbsDto;
import ssg.com.a.dto.BbsParam;

@Mapper
@Repository
public interface BbsDao {

	List<BbsDto> bbslist(BbsParam param);
	int getallbbs(BbsParam param);
	
	int bbswrite(BbsDto bbs);
	
	BbsDto bbsdetail(int seq);
	int readcount(int seq);
	
	void answerupdate(BbsDto dto);
	int answerinsert(BbsDto dto);
	
	int commentWrite(BbsComment com);
	List<BbsComment> commentList(int seq);
	
	int bbsupdate(BbsDto dto);
	
	int bbsdelete(int seq);
}
