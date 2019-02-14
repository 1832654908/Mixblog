package com.mixblog.interceotor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mixblog.pojo.Admin;

public class Interceptor2 implements HandlerInterceptor {

	
	//��ǰ�������е�preHandle���У��Ż��������afterCompletion
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// ҳ����Ⱦ֮��
		
	}

	
	
	//���������������У��Ż������������
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		// controller�з���ִ��֮��
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// controller�з���ִ��֮ǰ
		//�ж��������Ƿ��� /denglu.action,����Ƿ���
		//�������/denglu.action���ж��û��Ƿ��¼�������û�е�¼���ض��򵽵�¼ҳ�棬�����¼��������
		
		String requestURI = request.getRequestURI();
		if(!requestURI.contains("/adminlogin")) {//����������plist.action���ж��Ƿ��¼��
			Admin admin = (Admin)request.getSession().getAttribute("admin");
			if(admin==null) {//û�е�¼��
				response.sendRedirect(request.getContextPath()+"/login");
				response.getWriter().write("");
				return false;
			}
		}
		return true;//�Ƿ����
	}

}
