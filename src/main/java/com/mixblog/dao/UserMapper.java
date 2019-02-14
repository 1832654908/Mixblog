package com.mixblog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mixblog.pojo.Code;
import com.mixblog.pojo.User;

public interface UserMapper {
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
	/* ��ֵ��� */
	void umoneyadd(@Param("code")Code code, @Param("uid")Integer uid);

	/* ��ͨVIP */
			/* ��ͨVIP�˻���Ǯ */
			void userktvip1(Integer uid);
			void userktvip2(Integer uid);
			void userktvip3(Integer uid);
			void userktvip4(Integer uid);
			void userktvip5(Integer uid);
	
			/* ��ͨ��ʱ */
			void userudqvip1(Integer uid);
			void userudqvip2(Integer uid);
			void userudqvip3(Integer uid);
			void userudqvip4(Integer uid);
			void userudqvip5(Integer uid);

			/* �ı��Ա״̬ */
			void userktvip(Integer uid);
			/* ����VIP */
			/* ���Ѽ�ʱ */
			void userudqvip11(Integer uid);
			void userudqvip22(Integer uid);
			void userudqvip33(Integer uid);
			void userudqvip44(Integer uid);
			void userudqvip55(Integer uid);
			
			/* ��ֵ��ʹ�������û���� */
			void umoneyadd1(Integer uid);
			void umoneyadd2(Integer uid);
			void umoneyadd3(Integer uid);
			void umoneyadd4(Integer uid);
			void umoneyadd5(Integer uid);

			/* �޸����� */
			void usermodify(String upw,Integer uid);
			/* �û��б� */
			List<User> uuserlist(int startPos, int pageSize);
			/* ͳ�ƻ�Ա���� */
			Integer ucount();

				/* ͳ��VIP���� */
			Integer ucountvip();
			/* ͨ�������û������� */
			List<User> usearch(@Param("uname") String uname);
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


			