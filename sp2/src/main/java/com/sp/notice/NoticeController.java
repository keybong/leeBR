package com.sp.notice;

import java.io.File;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sp.common.FileManager;
import com.sp.common.MyUtil;

@Controller("notice.noticeController")
public class NoticeController {
	@Autowired
	private NoticeService service;
	@Autowired
	private MyUtil util;
	@Autowired
	private FileManager fileManager;
	
	@RequestMapping(value="/notice/list")
	public String list(
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue="") String searchKey,
			@RequestParam(defaultValue="") String searchValue,
			Model model,
			HttpServletRequest req
			) throws Exception {
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			searchValue=URLDecoder.decode(searchValue,"UTF-8");
		}
		
		int total_page;
		int rows=10;
		int dataCount;

		
		
		
		Map<String, Object> map=new HashMap<>();
			map.put("searchKey", searchKey);
			map.put("searchValue", searchValue);
			dataCount=service.dataCount(map);
			total_page=util.pageCount(rows, dataCount);
			
		
		if(total_page<current_page) {
			current_page=total_page;
		}
		
		int start=(current_page-1)*rows+1;
		int end=current_page*rows;
		map.put("start", start);
		map.put("end", end);
		List<Notice> list=service.listNotice(map);
		
		List<Notice> listTop=null;
		if(current_page==1) {
			listTop=service.listNoticeTop();
		}
		
		int listNum=0, n=0;
		Iterator<Notice> it=list.iterator();
		while(it.hasNext()) {
			Notice dto=it.next();
			listNum=dataCount-(start+n-1);
			dto.setListNum(listNum);
			n++;
		}
		
		
		String cp=req.getContextPath();
		String query="";
		String list_url,article_url;
		if(searchValue.length()!=0) {
			query = "&searchKey="+searchKey+
					"&searchValue="+URLEncoder.encode(searchValue,"UTF-8");
		}
		list_url=cp+"/notice/list"+query;
		article_url = cp+"/notice/article?"+query+"&page="+current_page;
		if(query.length()!=0) {
			list_url+="?"+query;
			article_url+="&"+query;
		}
		
		String paging=util.paging(current_page, total_page, list_url);
		
		model.addAttribute("list", list);
		model.addAttribute("listTop", listTop);
		model.addAttribute("articleUrl", article_url);
		model.addAttribute("paging", paging);
		model.addAttribute("page", current_page);
		return "notice/list";
	}
	
	
	@RequestMapping(value="/notice/created", method=RequestMethod.GET)
	public String noticeForm(
			Model model
			) {
		model.addAttribute("mode", "created");
		return "notice/created";
	}
	@RequestMapping(value="/notice/created", method=RequestMethod.POST)
	public String noticeSubmit(
			Notice dto,
			HttpSession session
			) throws Exception {
		//루트의 실제 경로
		String root=session.getServletContext().getRealPath("/");
		//업로드할 경로
		String pathname=root+File.separator+"uploads"+File.separator+"notice";
		service.insertNotice(dto, pathname);
		return "redirect:/notice/list";
	}
	
	@RequestMapping(value="/notice/download")
	public void download(
			@RequestParam int num,
			HttpServletResponse resp,
			HttpSession session
			) {
		String root=session.getServletContext().getRealPath("/");
		String pathname=root+File.separator+"uploads"+File.separator+"notice";
		
		boolean b=false;
		
		Notice dto=service.readNotice(num);
		
		if(dto!=null) {
			b=fileManager.doFileDownload(
					dto.getSaveFilename(), 
					dto.getOriginalFilename(), pathname, resp);
		}
		
		if(! b) {
			try {
				resp.setContentType("text/html;charset=utf-8");
				PrintWriter out=resp.getWriter();
				out.print("<script>alert('파일다운로드가 실패했습니다.');history.back();</script>");
			} catch (Exception e) {
			}
		}
	}
	
	@RequestMapping(value="/notice/article" , method=RequestMethod.GET)
	public String article(
			@RequestParam int num,
			@RequestParam int page,
			@RequestParam(defaultValue="") String searchKey,
			@RequestParam(defaultValue="") String searchValue,
			Model model
			) throws Exception {
		if(searchValue.length()!=0) {
			searchValue=URLDecoder.decode(searchValue, "UTF-8");			
		}
		String query="page="+page;
		Notice dto=service.readNotice(num);
		if(dto==null) {
			return "notice/list?page="+page+"&searchKey="+searchKey+"&searchValue="+URLEncoder.encode(searchValue, "UTF-8");
		}
		
		Map<String, Object> map=new HashMap<>();
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);
		map.put("num", dto.getNum());
		Notice preReadDto=service.preReadNotice(map);
		Notice nextReadDto=service.nextReadNotice(map);
		
		
		model.addAttribute("dto", dto);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("searchKey",searchKey);
		model.addAttribute("preReadDto", preReadDto);
		model.addAttribute("nextReadDto",nextReadDto);
		return "notice/article";
	}
	
	@RequestMapping(value="/notice/update" , method=RequestMethod.GET)
	public String updateForm(
			@RequestParam int page,
			@RequestParam int num,
			@RequestParam(defaultValue="") String searchKey,
			@RequestParam(defaultValue="") String searchValue,
			Model model
			) throws Exception {
		if(searchValue.length()!=0) {
			searchValue=URLDecoder.decode(searchValue, "UTF-8");			
		}
		Notice dto=service.readNotice(num);
		searchValue=URLEncoder.encode(searchValue, "UTF-8");
		model.addAttribute("mode", "update");
		model.addAttribute("dto", dto);
		model.addAttribute("page", page);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchValue", searchValue);
		return "/notice/created";
	}
	@RequestMapping(value="/notice/update", method=RequestMethod.POST)
	public String updateSubmit(
			Notice dto,
			@RequestParam int page,
			@RequestParam(defaultValue="") String searchKey,
			@RequestParam(defaultValue="") String searchValue,
			HttpSession session,
			Model model
			) throws Exception {
		if(searchValue.length()!=0) {
			searchValue=URLDecoder.decode(searchValue, "UTF-8");			
		}
		//루트의 실제 경로
		String root=session.getServletContext().getRealPath("/");
		//업로드할 경로
		String pathname=root+File.separator+"uploads"+File.separator+"notice";
		service.updateNotice(dto, pathname);
		
		
		searchValue=URLEncoder.encode(searchValue, "UTF-8");

		model.addAttribute("page", page);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("num", dto.getNum());
		return "redirect:/notice/article";
	}

	@RequestMapping(value="/notice/delete")
	public String delete(
			@RequestParam int num,
			@RequestParam int page,
			@RequestParam(defaultValue="") String searchKey,
			@RequestParam(defaultValue="") String searchValue,
			@RequestParam(defaultValue="") String saveFilename,
			HttpSession session,
			Model model
			) throws Exception {
		if(searchValue.length()!=0) {
			searchValue=URLDecoder.decode(searchValue, "UTF-8");			
		}
		//루트의 실제 경로
		String root=session.getServletContext().getRealPath("/");
		//업로드할 경로
		String pathname=root+File.separator+"uploads"+File.separator+"notice";
		service.deleteNotice(num, pathname);
		
		
		searchValue=URLEncoder.encode(searchValue, "UTF-8");

		model.addAttribute("page", page);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchValue", searchValue);
		return "redirect:/notice/list";
	}
	
	@RequestMapping(value="/notice/deleteFile")
	public String deleteFile(
			@RequestParam int num,
			@RequestParam int page,
			@RequestParam(defaultValue="") String searchKey,
			@RequestParam(defaultValue="") String searchValue,
			Model model,
			HttpSession session
			) {
		//루트의 실제 경로
		String root=session.getServletContext().getRealPath("/");
		//업로드할 경로
		String pathname=root+File.separator+"uploads"+File.separator+"notice";
		Notice dto=service.readNotice(num);
		try {
			if(dto.getSaveFilename().length()!=0)
				fileManager.doFileDelete(dto.getSaveFilename(), pathname);
		} catch (Exception e) {
		}
		
		dto.setSaveFilename("");
		dto.setOriginalFilename("");
		dto.setFileSize(0);
		service.updateNotice(dto, pathname);
		
		
		model.addAttribute("page", page);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchValue", searchValue);
		return "redirect:/notice/list";
	}
	
}
