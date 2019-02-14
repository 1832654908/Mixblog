package com.mixblog.service;

import java.util.List;


import com.mixblog.pojo.Code;
import com.mixblog.pojo.User;

public interface UserService {
	/* ��½��֤ */
	User userselect(User user);
	/* ע��ʱ��ѯ�Ƿ����ظ����û��� */
	User userselectone(String uname);
	/* �û�ע�� */
	void useradd(User user);
	/* �жϻ�Ա�Ƿ���� */
	void userpdvip(User user);
	/* �����û���Ϣ */
	void usersave(User user);
	/* ��ѯ������Ϣ */
	User userselectus(String name);

	/* ��ͨVIP */
	void userktvip(Integer cmoney,Integer uid);

	/* ����VIP */
	void userktvip1(Integer cmoney, Integer uid);

	/* ��ֵ��� */
	void umoneyadd(User user, Code code);

	/* �޸����� */
	void usermodify(String upw,Integer uid);

	/* �û��б� */
	List<User> uuserlist(int startPos, int pageSize);

	/* ͳ�ƻ�Ա���� */
	Integer ucount();
	Integer ucountvip();

	/* ͨ�������û������� */
	List<User> usearch(String uname);

	/* ͨ��ID��ѯ�û���Ϣ */
	User uselect(Integer uid);

	/* ����Ա��˱����û���Ϣ */
	void adusersave(User user);

	/* ��̨ɾ���û� */
	void aduserdel(Integer uid);

	/* ͨ���������˻� */
	User selectbyemail(String uemail);

	/* ���������� */
	void usersetpw(String uemail, String upw);


	
	
	
}
