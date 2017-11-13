package com.sp.bbs;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

@Controller("bbs.boardController")
public class BoardController {
	@Autowired
	private BoardService service;
	@Autowired
	private MyUtil util;

	@RequestMapping("/bbs/list")
	public String list(@RequestParam(value = "page", defaultValue = "1") int current_page,
			@RequestParam(defaultValue = "subject") String searchKey,
			@RequestParam(defaultValue = "") String searchValue, @RequestParam(defaultValue = "5") int rows,
			HttpServletRequest req, Model model) throws Exception {

		int dataCount;
		int total_page;

		if (req.getMethod().equalsIgnoreCase("GET"))
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		// 테이터 갯수
		Map<String, Object> map = new HashMap<>();
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);
		dataCount = service.dataCount(map);

		// 총페이지수
		total_page = util.pageCount(rows, dataCount);

		// 다른사람이 자료를 삭제하여 전체 페이지가 변화 된 경우
		if (total_page < current_page)
			current_page = total_page;

		// 리스트에 출력할 데이터 가져오기
		int start = (current_page - 1) * rows + 1;
		int end = current_page * rows;
		map.put("start", start);
		map.put("end", end);
		List<Board> list = service.listBoard(map);

		// 출력번호, 뉴이미지
		int listNum, n = 0;
		long gap;
		Date endDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Iterator<Board> it = list.iterator();
		while (it.hasNext()) {
			Board dto = it.next();
			listNum = dataCount - (start + n - 1);
			dto.setListNum(listNum);
			n++;
			Date beginDate = sdf.parse(dto.getCreated());
			// 날짜 차이 (시간)
			gap = (endDate.getTime() - beginDate.getTime()) / (60 * 60 * 1000);
			dto.setGap(gap);

			dto.setCreated(dto.getCreated().substring(0, 10));
		}

		String query = "rows=" + rows;
		String listUrl, articleUrl;
		if (searchValue.length() != 0) {
			query += "&searchKey=" + searchKey + "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		String cp = req.getContextPath();
		listUrl = cp + "/bbs/list?" + query;
		articleUrl = cp + "/bbs/article?" + query + "&page=" + current_page;

		// 페이징 처리 결과
		String paging = util.paging(current_page, total_page, listUrl);

		model.addAttribute("list", list);
		model.addAttribute("articleUrl", articleUrl);
		model.addAttribute("page", current_page);
		model.addAttribute("total_page", total_page);
		model.addAttribute("dataCount", dataCount);
		model.addAttribute("paging", paging);
		model.addAttribute("rows", rows);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchValue", searchValue);

		return "bbs/list";
	}

	@RequestMapping(value = "/bbs/created", method = RequestMethod.GET)
	public String createdForm(Model model) throws Exception {
		model.addAttribute("mode", "created");
		return "bbs/created";
	}

	@RequestMapping(value = "/bbs/created", method = RequestMethod.POST)
	public String createdSubmit(Board dto, HttpServletRequest req) throws Exception {
		dto.setIpAddr(req.getRemoteAddr());
		service.insertBoard(dto);

		return "redirect:/bbs/list";
	}

	@RequestMapping(value = "/bbs/article")
	public String article(@RequestParam String page, @RequestParam(defaultValue = "subject") String searchKey,
			@RequestParam(defaultValue = "") String searchValue, @RequestParam int rows, @RequestParam int num,
			Model model) throws Exception {

		String query = "page=" + page + "&rows=" + rows;
		if (searchValue.length() != 0) {
			query += "&searchKey=" + searchKey;
			query += "&searchValue=" + searchValue;
		}

		searchValue = URLDecoder.decode(searchValue, "UTF-8");

		Board dto = service.readBoard(num);
		if (dto == null) {
			return "redirect:/bbs/list?" + query;
		}

		// 엔터를 br로
		// dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		// 이전글/다음글
		Map<String, Object> map = new HashMap<>();
		map.put("num", num);
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);

		Board preReadDto = service.preReadBoard(map);
		Board nextReadDto = service.nextReadBoard(map);

		service.updateHitCount(num);
		model.addAttribute("dto", dto);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("rows", rows);
		model.addAttribute("preReadDto", preReadDto);
		model.addAttribute("nextReadDto", nextReadDto);

		return "bbs/article";
	}
	
	
	@RequestMapping (value="/bbs/update", method=RequestMethod.GET)
	public String updateForm(
			@RequestParam int num,
			@RequestParam String page,
			@RequestParam int rows,
			Model model) throws Exception {
		Board dto= service.readBoard(num);
		if(dto==null) {
			return "redirect:/bbs/list?page="+page+"&rows="+rows;
		}
		model.addAttribute("dto", dto);
		model.addAttribute("mode", "update");
		model.addAttribute("page", page);
		model.addAttribute("rows", rows);
		
		return "bbs/created";
	}
	
	@RequestMapping (value="/bbs/update", method=RequestMethod.POST)
	public String updateSubmit(
			Board dto,
			@RequestParam String page,
			@RequestParam int rows,
			HttpServletRequest req
			) {
		
		dto.setIpAddr(req.getRemoteAddr());
		service.updateBoard(dto);
		
		return "redirect:/bbs/list?page="+page+"&rows="+rows;
	}
	
	
	@RequestMapping (value="/bbs/delete")
	public String deleteBoard(
			@RequestParam int num,
			@RequestParam String page,
			@RequestParam int rows
			) {
		service.deleteBoard(num);
		return "redirect:/bbs/list?page="+page+"&rows="+rows;
	}
	

	@RequestMapping (value="/bbs/deleteList", method=RequestMethod.POST)
	public String deleteList(
			@RequestParam Integer[] nums,
			@RequestParam String page,
			@RequestParam int rows
			){
		//Arrays.asList : 배열을 List로 변환
		service.deleteBoardList(Arrays.asList(nums));
		return "redirect:/bbs/list?page="+page+"&rows="+rows;
	}
}
