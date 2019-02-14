package com.mixblog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mixblog.pojo.Code;

public interface CodeMapper {
	/* ��ѯ��ֵ���Ƿ���Ч */
	Code cselect(String codevar);
	
	/* ��ֵ��״̬��Ϊ��ʹ�� */
	void cuse(String codevar);
	
	/* ��ֵ���б� */
	/* ��ֵ���б� */
	List<Code> clist(int startPos, int pageSize);
	
	/* ��ӳ�ֵ�� */
	void ccodeadd(Code code);
	
	/* ��ѯ�Ƿ�����ظ���ֵ�� */
	Integer ccodeselect(Code code);
	
	/* ͳ������ */
	Integer ccount();
	List<Code> csearch(@Param("codevar") String codevar);
	Integer cucount();
	Integer cncount();
	
}
