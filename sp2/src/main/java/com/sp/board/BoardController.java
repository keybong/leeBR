package com.sp.board;


import java.net.URLDecoder;
import java.net.URLEncoder;
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

@Controller("board.boardController")
public class BoardController {
	@Autowired
	private BoardService service;
	@Autowired
	private MyUtil util;
	
	@RequestMapping("/board/list")
	public String list(
			@RequestParam(value="page", defaultValue="1") int current_page,
			@RequestParam(defaultValue="subject") String searchKey,
			@RequestParam(defaultValue="") String searchValue,
			HttpServletRequest req,
			Model model
			) throws Exception{
		
		if (req.getMethod().equalsIgnoreCase("GET"))
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		
		
		Map<String, Object> map=new HashMap<>();
		if(searchValue!="") {
			map.put("searchKey", searchKey);
			map.put("searchValue", searchValue);		
		}
		int dataCount=service.dataCount(map);
		int rows=10;

		//전체 페이지수
		//pageCount 메소드는 dataCount 가 0 이아닐때
		//즉, 데이터가 하나라도 있을때 그리고
		//전체 데이터 수를 표시할 rows로 나누었을때
		//딱떨어진다면 페이지의 수는 dataCount/rows 이지만
		//아니라면 뒷 데이터를 보여주기위해 
		//dataCount/rows+1 을 해준다.
		int total_page=util.pageCount(rows, dataCount);
		
		//마지막 데이터들이 삭제되서 페이지수가 변화하였지만
		//웹은 정적이기 때문에 전체 페이지 수보다 큰 페이지를 
		//클릭시 없는 페이지를 클릭했기 때문에 current_page를
		//수정해준다.
		if(total_page < current_page)
			current_page = total_page;
		
		//start 변수는 몇번째 테이터 부터 표시할것인지
		// 구한다.
		// 시작할 페이지의 전페이지에 총데이터 갯수를 구하는
		//공식이다.
		//즉, current_page-1 은 현재 페이지의 전 페이지를
		// 구하는 것이고 * rows 는 전페이지의 테이터 갯수가 
		// 총 몇개 인지 구하는 것이다.
		// 그러므로 만약 current_page==3 이라면 전페이지는
		// 2 이고 2페이지 까지의 * rows 를 하면 2페이지 까지의
		//전체 테이터수가 나온다
		// 그리고 나서 +1 을 해주면 2페이지의 마지막 테이터 
		//다음 부터 출력이 가능하도록 해주는 것이다.
		int start=(current_page -1)* rows +1;
		
		
		//end 변수는 current_page까지의 전체 테이터수를
		// 계산한다.
		//그러므로 페이징처리는 
		//항상 start-end=rows 가된다.
		int end=current_page*rows;
		//항상 start-end=rows 가된다.
		//항상 start-end=rows 가된다.
		//항상 start-end=rows 가된다.
		//항상 start-end=rows 가된다.
		map.put("start", start);
		map.put("end", end);
		
		int listNum, n=0;
		//우선 list에 전체데이터를 담는다.
		List<Board> list=service.listBoard(map);
		//list의 값을 가져오기위해 Iterator<Board>반복자
		//에 담는다.
		Iterator<Board> it = list.iterator();
		//it.hasNext() 로 값이 없을때 까지 하나씩 값을
		//dto 에 저장한다. 이유는 값을 가져오는 것이
		//목적이 아닌 list 하나하나의 listNum을 넣어주기
		//위해서 이다.
		while(it.hasNext()) {
			//dto에 테이터를 넣어준다.
			Board dto=it.next();
			//listNum은 0부터 나오나?
			//Iterator로 list를 불러올 때 에는 
			//ROWNUM 이 작은수 즉, 최신데이터 부터
			//불러온다.
			//그러니까 start(current_page의 시작테이터의 ROWNUM) 존나어렵네
			listNum =dataCount - (start + n -1);
			dto.setListNum(listNum);
			n++;
		}
		String query="";
		String listUrl, articleUrl;
		if (searchValue.length() != 0) {
			query ="searchKey=" + searchKey + "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		String cp = req.getContextPath();
		listUrl = cp + "/board/list?" + query;
		articleUrl = cp + "/board/article?" + query + "&page=" + current_page;

		// 페이징 처리 결과
		String paging = util.paging(current_page, total_page, listUrl);

		model.addAttribute("list", list);
		model.addAttribute("articleUrl", articleUrl);
		model.addAttribute("page", current_page);
		model.addAttribute("total_page", total_page);
		model.addAttribute("paging", paging);
		
		
		
		return "board/list";
		
	}
	
	@RequestMapping(value="/board/created" , method=RequestMethod.GET)
	public String createdForm(Model model) {
		model.addAttribute("mode", "created");
		return "board/created";
	}
	
	@RequestMapping(value="/board/created" , method=RequestMethod.POST)
	public String createdSubmit(
			Board dto,
			Model model,
			HttpServletRequest req
			) throws Exception {
		dto.setIpAddr(req.getRemoteAddr());
		service.insertBoard(dto,"created");
		
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/board/article", method=RequestMethod.GET)
	public String article(
			@RequestParam int boardNum,
			@RequestParam int page,
			@RequestParam(defaultValue="subject") String searchKey,
			@RequestParam(defaultValue="") String searchValue,
			Model model
			) throws Exception {
		String query="page="+page;
		if(searchValue.length()!=0) {
			query+="&searchKey="+searchKey+"&searchValue="+searchValue;
		}
		searchValue=URLDecoder.decode(searchValue, "UTF-8");
		
		//조회수 증가
		service.updateHitCount(boardNum);
		
		//게시물 가져오기
		Board dto=service.readBoard(boardNum);
		if(dto==null) {
			return "redirect:/board/list?"+query;
		}
		dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
		
		//이전글 다음글
		Map<String, Object> map=new HashMap<>();
		map.put("searchKey", searchKey);
		map.put("searchValue", searchValue);
		map.put("groupNum", dto.getGroupNum());
		map.put("orderNo", dto.getOrderNo());
		
		Board preReadDto=service.preReadBoard(map);
		Board nextReadDto=service.nextReadBoard(map);
		
		
		model.addAttribute("page", page);
		model.addAttribute("query", query);
		model.addAttribute("dto", dto);
		model.addAttribute("preReadDto", preReadDto);
		model.addAttribute("nextReadDto", nextReadDto);
		return "board/article";
	}
}
