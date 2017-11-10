package com.sp.score;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sp.common.MyUtil;

@Controller("score.scoreController")
public class ScoreController {
	@Autowired
	private ScoreService service;
	@Autowired
	private MyUtil util;
	
	
	@RequestMapping("/score/list")
	public String list(
			@RequestParam (value="page", defaultValue="1") int current_page,
			@RequestParam (defaultValue="hak") String searchKey,
			@RequestParam (defaultValue="") String searchValue,
			HttpServletRequest req,
			Model model
			) throws Exception {
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			searchValue=URLDecoder.decode(searchValue, "UTF-8");
		}
		
		int rows=2;
		int dataCount, total_page;
		
		Map<String, Object> map=new HashMap<>();
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);
		
		dataCount=service.dataCount(map);
		total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page)
			current_page=total_page;
		
		int start=(current_page-1)*rows+1;
		int end=current_page*rows;
		
		map.put("start", start);
		map.put("end", end);
		
		List<Score> list=service.listScore(map);
		
		String cp=req.getContextPath();
		String listUrl=cp+"/score/list";
		if(searchValue.length()!=0) {
			listUrl+="&searchKey="+searchKey;
			listUrl+="&searchValue="+URLEncoder.encode(searchKey,"UTF-8");
		}
		String paging=util.paging(current_page, total_page, listUrl);
		
		model.addAttribute("list",list);
		model.addAttribute("dataCount",dataCount);
		model.addAttribute("dataCount",current_page);
		model.addAttribute("total_page",total_page);
		model.addAttribute("paging",paging);

		return "/score/list";
	}
	
	@RequestMapping(value="/score/write", method=RequestMethod.GET)
	public String insertForm(Model model) {
		model.addAttribute("mode", "insert");
		return "/score/write";
	}
	@RequestMapping(value="/score/write", method=RequestMethod.POST)
	public String insertSubmit(Score dto) {
		
		service.insertScore(dto);
		
		return "redirect:/score/list";
	}
	
	@RequestMapping(value="/score/update", method=RequestMethod.GET)
	public String updateForm(
			String hak,
			Model model
			) {
		
		Score dto=service.readScore(hak);
		model.addAttribute("mode", "update");
		model.addAttribute("dto", dto);
		return "/score/write";
	}
	
	@RequestMapping(value="/score/update", method=RequestMethod.POST)
	public String updateSubmit(Score dto) {
		service.updateScore(dto);
		return "redirect:/score/list";
	}
	@RequestMapping(value="/score/delete", method=RequestMethod.GET)
	public String deleteSubmit(
				String hak
			) {
		service.deleteScore(hak);
		return "redirect:/score/list";
	}
	
	
}
