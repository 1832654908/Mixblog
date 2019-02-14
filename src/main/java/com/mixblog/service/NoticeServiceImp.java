package com.mixblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixblog.dao.NoticeMapper;
import com.mixblog.pojo.Notice;
@Service
public class NoticeServiceImp implements NoticeService {
	@Autowired
	private NoticeMapper noticeMapper;
	/* ��ȡ�����б� */
	@Override
	public List<Notice> slist() {
		// TODO Auto-generated method stub
		return noticeMapper.slist();
	}
	
	/* �༭���� */
	@Override
	public Notice sselect(Integer sid) {
		// TODO Auto-generated method stub
		return noticeMapper.sselect(sid);
	}



	@Override
	public Notice sselect1() {
		// TODO Auto-generated method stub
		return noticeMapper.sselect1();
	}


	/* �༭���汣�� */
	@Override
	public void nupdate(Notice notice) {
		// TODO Auto-generated method stub
		noticeMapper.nupdate(notice);
	}



	@Override
	public Notice sselect2() {
		// TODO Auto-generated method stub
		return noticeMapper.sselect2();
	}



	@Override
	public Notice sselect3() {
		// TODO Auto-generated method stub
		return noticeMapper.sselect3();
	}
}
