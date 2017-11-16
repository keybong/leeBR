package com.sp.notice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sp.common.FileManager;
import com.sp.common.dao.CommonDAO;

@Service("notice.noticeService")
public class NoticeServiceImpl implements NoticeService{
	@Autowired
	private CommonDAO dao;
	@Autowired
	private FileManager fileManager;
	
	@Override
	public int insertNotice(Notice dto, String pathname) {
		int result=0;
		try {
			if(dto.getUpload()!=null&& ! dto.getUpload().isEmpty()) {
				String saveFilename=
						fileManager.doFileUpload(dto.getUpload(), pathname);
				dto.setSaveFilename(saveFilename);
				dto.setOriginalFilename(dto.getUpload().getOriginalFilename());
				dto.setFileSize(dto.getUpload().getSize());
			}
			
			result=dao.insertData("notice.insertNotice", dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result=0;
		try {
			result=dao.selectOne("notice.dataCount", map);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	@Override
	public List<Notice> listNotice(Map<String, Object> map) {
		List<Notice> list=null;
		try {
			list=dao.selectList("notice.listNotice", map);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	@Override
	public List<Notice> listNoticeTop() {
		List<Notice> list=null;
		try {
			list=dao.selectList("notice.listNoticeTop");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	@Override
	public int updateHitCount(int num) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Notice readNotice(int num) {
		Notice dto=null;
		try {
			dto=dao.selectOne("notice.readNotice", num);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return dto;
	}

	@Override
	public Notice preReadNotice(Map<String, Object> map) {
		Notice dto=null;
		try {
			dto=dao.selectOne("notice.preReadNotice", map);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return dto;
	}

	@Override
	public Notice nextReadNotice(Map<String, Object> map) {
		Notice dto=null;
		try {
			dto=dao.selectOne("notice.nextReadNotice", map);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return dto;
	}

	@Override
	public int updateNotice(Notice dto, String pathname) {
		int result=0;
		try {
			if(dto.getUpload()!=null&& ! dto.getUpload().isEmpty()) {
				if(dto.getSaveFilename().length()!=0)
					fileManager.doFileDelete(dto.getSaveFilename(), pathname);
				
				String saveFilename=fileManager.doFileUpload(dto.getUpload(), pathname);
				dto.setSaveFilename(saveFilename);
				dto.setOriginalFilename(dto.getUpload().getOriginalFilename());
				dto.setFileSize(dto.getUpload().getSize());
			}
			result=dao.updateData("notice.updateNotice", dto);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	@Override
	public int deleteNotice(int num, String pathname) {
		int result=0;
		try {
			Notice dto=readNotice(num);
			if(dto.getUpload()!=null&& ! dto.getUpload().isEmpty()) {
				if(dto.getSaveFilename().length()!=0)
					fileManager.doFileDelete(dto.getSaveFilename(), pathname);
			}
			result=dao.deleteData("notice.deleteNotice", num);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

}
