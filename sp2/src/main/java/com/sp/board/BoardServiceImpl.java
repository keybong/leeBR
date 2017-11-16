package com.sp.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.common.dao.CommonDAO;

@Service("board.boardService")
public class BoardServiceImpl implements BoardService{

	@Autowired
	private CommonDAO dao;
	
	@Override
	public int insertBoard(Board dto, String mode) {
		int result=0;
		try {
			int seq=dao.selectOne("board.seq");
			if(mode.equals("created")) {
				//새글일 때
				dto.setBoardNum(seq);
				dto.setGroupNum(seq);
				dto.setDepth(0);
				dto.setOrderNo(0);
				dto.setParent(0);
			}else {
				//답변일 때
				//orderNo 변경
				Map<String, Object> map=new HashMap<>();
				map.put("groupNum", dto.getGroupNum());
				map.put("orderNo", dto.getOrderNo());
				dao.updateData("board.updateOrderNo", map);
				
				dto.setBoardNum(seq);
				dto.setDepth(dto.getDepth()+1);
				dto.setOrderNo(dto.getOrderNo()+1);
			}
			result=dao.insertData("board.insertBoard", dto);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	@Override
	public List<Board> listBoard(Map<String, Object> map) {
		List<Board> list=new ArrayList<>();
		try {
			list=dao.selectList("board.listBoard", map);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result=0;
		try {
			result=dao.selectOne("board.dataCountBoard", map);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	@Override
	public int updateHitCount(int boardNum) {
		int result=0;
		try {
			result=dao.updateData("board.updateHitCount", boardNum);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	@Override
	public Board readBoard(int boardNum) {
		Board dto=null;
		try {
			dto=dao.selectOne("board.readBoard", boardNum);
		} catch (Exception e) {
			
		}
		return dto;
	}

	@Override
	public Board preReadBoard(Map<String, Object> map) {
		Board dto=null;
		try {
			dto=dao.selectOne("board.preReadBoard", map);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return dto;
	}

	@Override
	public Board nextReadBoard(Map<String, Object> map) {
		Board dto=null;
		try {
			dto=dao.selectOne("board.nextReadBoard", map);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return dto;
	}

	@Override
	public int updateBoard(Board dto) {
		int result=0;
		try {
			result=dao.updateData("board.updateBoard", dto);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	@Override
	public int deleteBoard(int boardNum) {
		int result=0;
		try {
			result=dao.deleteData("board.deleteBoard", boardNum);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
	
	
}
