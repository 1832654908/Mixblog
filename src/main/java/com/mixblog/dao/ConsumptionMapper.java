package com.mixblog.dao;

import java.util.List;


import com.mixblog.pojo.Consumption;


public interface ConsumptionMapper {
	
	void cadd1(Integer uid);
	void cadd2(Integer uid);
	void cadd3(Integer uid);
	void cadd4(Integer uid);
	void cadd5(Integer uid);

	/* ��ȡ�����嵥���� */
	List<Consumption> clistone(Consumption consumption);
}
