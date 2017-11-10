package com.sp.score;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("score.scoreService")
public class ScoreServiceImpl implements ScoreService{
	@Autowired
	private ScoreDAO dao;
	
	@Override
	public int insertScore(Score dto) {
		int result=dao.insertScore(dto);
		return result;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result=dao.dataCount(map);
		return result;
	}

	@Override
	public List<Score> listScore(Map<String, Object> map) {
		List<Score> list=dao.listScore(map);
		return list;
	}

	@Override
	public Score readScore(String hak) {
		Score dto=dao.readScore(hak);
		return dto;
	}

	@Override
	public int updateScore(Score dto) {
		int result=dao.updateScore(dto);
		return result;
	}

	@Override
	public int deleteScore(String hak) {
		int result=dao.deleteScore(hak);
		return result;
	}
	
}
