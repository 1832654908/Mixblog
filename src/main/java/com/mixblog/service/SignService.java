package com.mixblog.service;

import java.util.List;

import com.mixblog.pojo.Sign;

public interface SignService {
	/* ������½��¼ */
	void signadd(Sign sign);

	/* ��¼��¼�� */
	/* List<Sign> slist(); */

	Integer scount();

	List<Sign> slist(int startPos, int pageSize);


}
