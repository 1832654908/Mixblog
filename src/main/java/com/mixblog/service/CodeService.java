package com.mixblog.service;

import java.util.List;


import com.mixblog.pojo.Code;

public interface CodeService {


	/* ��ѯ��ֵ���Ƿ���Ч */
	Code cselect(String codevar);
	/* ��ֵ��״̬��Ϊ��ʹ�� */
	void cuse(Code code);

	/* ��ֵ���б� */
	List<Code> clist(int startPos, int pageSize);

	/* ��ӳ�ֵ�� */
	void ccodeadd(Code code);

	/* ��ѯ�Ƿ�����ظ���ֵ�� */
	Integer ccodeselect(Code code);

	/* ͳ������ */
	Integer ccount();
	List<Code> csearch(String codevar);
	Integer cucount();
	Integer cncount();
	
	
	

}
