package ssg.com.a.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Utill.PdsUtill;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import ssg.com.a.MediaTypeUtils;
import ssg.com.a.dto.BbsDto;
import ssg.com.a.dto.PdsDto;
import ssg.com.a.dto.PdsParam;
import ssg.com.a.service.PdsService;

@RestController
public class PdsController {
	
	@Autowired
	PdsService service;
	
	@GetMapping("pdslist")
	public Map<String, Object> pdslist(PdsParam param){
		System.out.println("PdsController bbslist " + new Date());
		
		// 글목록
		List<PdsDto> list = service.pdslist(param);
		
		// 글의 총갯수
		int count = service.getallpds(param);
		int pagePds = count / 10;
		if((count % 10) > 0) {
			pagePds = pagePds + 1;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pdslist", list);
		map.put("pagePds", pagePds);
		
		return map;		
	}
	
	@PostMapping("fileupload")
	public String pdsupload(PdsDto pds, 
							@RequestParam(value = "uploadfile",required = false)
							MultipartFile uploadfile,
							HttpServletRequest request) {
		System.out.println("PdsController pdsupload" + new Date());
		
		// 파일업로드 경로
				String path = request.getServletContext().getRealPath("/upload");
				
			//	String path = "d:\tmp";
				
				String filename = uploadfile.getOriginalFilename();
				
				// 파일명을 변경!
				String newfilename = PdsUtill.getNewFileName(filename);
				
				String filepath = path + "/" + filename;
				System.out.println(filepath);
				
				
				
				File file = new File(filepath);
				
				try {
					BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
								
					os.write(uploadfile.getBytes());	// 실제 업로드 되는 부분
					os.close();
					
				} catch (FileNotFoundException e) {			
					e.printStackTrace();
				} catch (IOException e) {			
					e.printStackTrace();
				}	
				
				// db접속전 filename, newfilename
				pds.setFilename(filename);
				pds.setNewfilename(newfilename);
				System.out.println(pds.toString());
				
				boolean b = service.pdsupload(pds);
				if(!b) {
					return "NO";
				}
				return "YES";
	}
	
	@GetMapping("pdsdetail")
	public PdsDto pdsdetail(int seq) {
		System.out.println("PdsController pdsdetail" + new Date());
		
		service.readcount(seq);
	
		return service.pdsdetail(seq);
	}
	
	@Autowired
	ServletContext sevletContext;
	
	@GetMapping("filedownload")
	public ResponseEntity<InputStreamResource> filedownload(String filename, 
																HttpServletRequest request, int seq) throws FileNotFoundException{
		System.out.println("HelloController filedownload " + new Date());
		
		// 파일업로드 경로
		String path = request.getServletContext().getRealPath("/upload");
		
		// 파일 타입을 조사
		MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(sevletContext, filename);
		
		File file = new File(path + "/" + filename);
		
		InputStreamResource is = new InputStreamResource(new FileInputStream(file));
		
		// download counter 증가
		service.downcount(seq);

		return ResponseEntity
				.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName()) // 원본파일명
				.contentType(mediaType)
				.contentLength(file.length())
				.body(is);	
	}
}
