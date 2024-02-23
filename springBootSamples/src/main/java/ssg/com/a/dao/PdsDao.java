package ssg.com.a.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ssg.com.a.dto.BbsDto;
import ssg.com.a.dto.BbsParam;
import ssg.com.a.dto.PdsDto;
import ssg.com.a.dto.PdsParam;

@Mapper
@Repository
public interface PdsDao {
	
	List<PdsDto> pdslist(PdsParam param);
	int getallpds(PdsParam param);
	
	int pdsupload(PdsDto dto);
	PdsDto getPds(int seq);
	void downcount(int seq);
	void readcount(int seq);
	PdsDto pdsdetail(int seq);

}
