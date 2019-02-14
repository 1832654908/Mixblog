package com.mixblog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mixblog.pojo.Resources;

public interface ResourcesMapper {
	/* �����б� */
	List<Resources> rlist(int startPos, int pageSize);
	List<Resources> rflsplist(int startPos, int pageSize);
	List<Resources> rsysplist(int startPos, int pageSize);
	List<Resources> rjpttlist(int startPos, int pageSize);
	List<Resources> rzxsplist(int startPos, int pageSize);
	/* ͳ���������� */
	Integer rcount();
	Integer rflspcount();
	Integer rsyspcount();
	Integer rjpttcount();
	Integer rzxspcount();
	/* �������� */
	void radd(Resources resources);
	
	/* ��������ǰ��ѯ�Ƿ�����ظ� */
	Integer rsrname(String rname);
	
	/* ��ѯ������Դ������ */
	Resources rselectno(Integer rid);
	/* ���±������½ӿ� */
	void pupdate(Resources resources);
	/* ������Դ */
	List<Resources> rsearch(@Param("rname") String rname);
	/* ɾ������ */
	void adminpdel(Integer rid);
}
