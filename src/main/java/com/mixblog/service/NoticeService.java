package com.mixblog.service;

import java.util.List;

import com.mixblog.pojo.Notice;

public interface NoticeService {
	/* ��ȡ�����б� */
	List<Notice> slist();

	


	/* �༭���� */
	Notice sselect(Integer sid);



	/* ��ҳ���� */
	Notice sselect1();
	Notice sselect2();
	Notice sselect3();

	/* �༭���汣�� */
	void nupdate(Notice notice);



	

	


}
