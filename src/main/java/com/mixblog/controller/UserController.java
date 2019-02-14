package com.mixblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mixblog.pojo.Code;
import com.mixblog.pojo.Consumption;
import com.mixblog.pojo.MailUtil;
import com.mixblog.pojo.Md5;
import com.mixblog.pojo.Recharge;
import com.mixblog.pojo.Sign;
import com.mixblog.pojo.User;
import com.mixblog.service.CodeService;
import com.mixblog.service.ConsumptionService;
import com.mixblog.service.RechargeService;
import com.mixblog.service.SignService;
import com.mixblog.service.UserService;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.Version;
import eu.bitwalker.useragentutils.UserAgent;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private ConsumptionService consumptionService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private SignService signService;

	/* �û���¼ҳ�� */
	@RequestMapping(value = "login")
	public String Login() {
		return "login";
	}

	/* �û���½�ӿ� */
	@RequestMapping(value = "login1")
	public @ResponseBody String Login1(User user, HttpSession session, HttpServletRequest request) {
		user.setUpw(Md5.md5Password(user.getUpw()));
		user.setUemail(user.getUname());
		User select = userService.userselect(user);/* ��ѯ�û��������Ƿ���ȷ */
		if (select != null) {
			
			
			  Browser browser =UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser(); /*��ȡ�������Ϣ*/ 
			  Version version =browser.getVersion(request.getHeader("User-Agent")); /*��ȡ������汾��*/ 
			  String info =browser.getName() + "/" + version.getVersion(); /*�������Ϣ�ͻ�����Ϣ */
			  /* ��ȡIP��ַ */
			  String ip = null;
		        String ipAddresses = request.getHeader("X-Forwarded-For");
		        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
		            ipAddresses = request.getHeader("Proxy-Client-IP");
		        }
		        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
		            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
		        }
		        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
		            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
		        }
		        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
		            ipAddresses = request.getHeader("X-Real-IP");
		        }
		        if (ipAddresses != null && ipAddresses.length() != 0) {
		            ip = ipAddresses.split(",")[0];
		        }
		        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
		            ip = request.getRemoteAddr();
		        }
		        
			  String str =IPApi.queryIP(ip); JSONObject jsonObject = JSONObject.parseObject(str);
			  String county = jsonObject.getJSONObject("result").getString("area")+jsonObject.getJSONObject("result").getString("location"); 
			  Sign sign = new Sign(); 
			  sign.setSignuid(select.getUid()); 
			  sign.setSignip(ip);
			  sign.setSignbrower(info); 
			  sign.setSigncountry(county);
			  signService.signadd(sign);
			 
			userService.userpdvip(user);/* �ж�VIP�Ƿ���� */
			session.setAttribute("user", select);
			return "ok";
		} else {
			return "no";
		}
	}

	/* �û�ע��ҳ�� */
	@RequestMapping(value = "register")
	public String Register() {
		return "register";
	}

	/* �û�ע��ӿ� */
	@RequestMapping(value = "register1")
	public @ResponseBody String Register1(User user) {
		User userselectone = userService.userselectone(user.getUname());
		User userselectemail = userService.selectbyemail(user.getUemail());
		/* ע��ǰ�ж��Ƿ����û����ظ� */
		System.out.println(userselectemail);
		if (userselectone == null) {
			/* �ж������Ƿ���� */
			if (userselectemail == null) {
				user.setUpw(Md5.md5Password(user.getUpw()));
				System.out.println(user.getUpw());
				userService.useradd(user);
				return "ok";
			} else {
				return "nn";

			}

		} else {
			return "no";
		}
	}

	/* �û���Ϣҳ�� */
	@RequestMapping(value = "userinfo")
	public String UserInfo(HttpSession session) {
		User user = (User) session.getAttribute("user");
		User userselectus = userService.userselectus(user.getUname());
		session.setAttribute("user", userselectus);
		return "user/userinfo";
	}

	/* �����û���Ϣ�ӿ� */
	@RequestMapping(value = "usersave")
	public @ResponseBody String UserSave(User user, HttpSession session) {
		User attribute = (User) session.getAttribute("user");
		user.setUname(attribute.getUname());
		userService.usersave(user);
		System.out.println("OK");
		return "ok";
	}

	/* �û���ֵ��¼ҳ�� */
	@RequestMapping(value = "useradd")
	public String UserAdd(HttpSession session, Model model, Recharge recharge) {
		User attribute = (User) session.getAttribute("user");
		recharge.setRuserid(attribute.getUid());
		List<Recharge> rlist = rechargeService.rlistone(recharge);
		model.addAttribute("recharge", rlist);
		return "user/useradd";
	}

	/* �û����Ѽ�¼ҳ�� */
	@RequestMapping(value = "usercart")
	public String UserCart(HttpSession session, Consumption consumption, Model model) {
		User attribute = (User) session.getAttribute("user");
		consumption.setCuserid(attribute.getUid());
		List<Consumption> clist = consumptionService.clistone(consumption);
		model.addAttribute("consumption", clist);
		return "user/usercart";
	}

	/* �û�Ǯ��ҳ�� */
	@RequestMapping(value = "usermoney")
	public String UserMoney(HttpSession session) {

		User attribute = (User) session.getAttribute("user");
		User uselect = userService.uselect(attribute.getUid());
		session.setAttribute("user", uselect);
		return "user/usermoney";
	}

	/* �û���ֵ�ӿ� ��ʱ��д */
	@RequestMapping(value = "usermoneyadd")
	public String UserMoneyAdd() {
		return null;
	}

	/* ��ͨVIP�ӿ� */
	@RequestMapping(value = "userktvip")
	public @ResponseBody String UserKtvip(Consumption consumption, HttpSession session) {
		User user = (User) session.getAttribute("user");
		/* �жϵ�ǰ����Ƿ��㹻��ͨVIP */
		if (consumption.getCmoney() > user.getUbalance()) {
			return "no";
		} else {
			/* ���� */
			if (user.getUvip() == 1) {
				userService.userktvip1(consumption.getCmoney(), user.getUid());
				return "ok1";
			} else {
				/* �׳� */
				userService.userktvip(consumption.getCmoney(), user.getUid());
				return "ok";
			}
		}
	}

	/* ��ֵ�� �ӿ� */
	@RequestMapping(value = "usercode")
	public @ResponseBody String UserCode(Code code, HttpSession session) {
		/* ��ѯ��ֵ���Ƿ���Ч */
		Code cselect = codeService.cselect(code.getCodevar());
		User user = (User) session.getAttribute("user");
		if (cselect == null) {
			return "error1";

		} else if (cselect.getCodetype() == 0) {
			/* ��ֵ��״̬��Ϊ��ʹ�� */
			codeService.cuse(code);
			/* Ϊ�˻�������� */
			userService.umoneyadd(user, code);
			return "ok";
		} else if (cselect.getCodetype() == 1) {
			return "no";
		} else {
			return "error";
		}
	}

	/* �û��޸�����ҳ�� */
	@RequestMapping(value = "usermodify")
	public String Usermodify() {
		return "user/usermodify";
	}

	/* �û��޸�����ӿ� */
	@RequestMapping(value = "usermodify1")
	public @ResponseBody String Usermodify1(String upw, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user.getUpw() != upw) {
			user.setUpw(Md5.md5Password(upw));
			userService.usermodify(user.getUpw(), user.getUid());
			session.removeAttribute("user");
			return "ok";
		} else {
			return "no";
		}
	}

	/* �û��˳�ϵͳ */
	@RequestMapping(value = "exitlogin")
	public String ExitLogin(HttpSession session,HttpServletRequest request,HttpServletResponse response,String uname) {
		session.removeAttribute("user");
		return "login";
	}

	/* 1.�û��һ�����-��������ҳ�� */
	@RequestMapping(value = "findpw")
	public String FindPw() {
		return "user/userfindpw";
	}

	/* 2.�û��һ�����-�ύ�����ַ */
	@RequestMapping(value = "findpw1")
	public @ResponseBody String FindPw1(User user, HttpServletResponse response, HttpServletRequest request,
			HttpSession session) {
		User user1 = userService.selectbyemail(user.getUemail());/* ���������ѯ�Ƿ���� */
		if (user1 != null) {
			System.out.println("�յ�����");
			String text = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));/* ���������֤�� */
			String email = user.getUemail();
			Map map = new HashMap();
			map.put("yzm", text);
			map.put("email", email);
			session.setAttribute("find", map);
			MailUtil.sendMail(email, "���������һ������ʼ�", "�����ڲ����һ����룬��֤��Ϊ" + text);
			return "success";
		} else {
			return "error";
		}

	}

	/* 3.�û��һ�����-������֤��ҳ���ж����� */
	@RequestMapping(value = "userJudge")
	public String UserJudge(HttpSession session) {
		if (session.getAttribute("find") != null) {/* �жϵ�ǰ�Ƿ����һ��������� */
			return "user/userjudge";
		}
		return "login";
	}

	/* 4.�û��һ�����-������֤���ж����� */
	@RequestMapping(value = "userJudge1")
	public @ResponseBody String UserJudge1(String yzm, HttpSession session) {
		Map attribute = (Map) session.getAttribute("find");
		if (attribute.get("yzm").equals(yzm)) {/* ��������û��������֤���Ƿ�һ�� */
			return "success";
		} else {
			return "error";
		}
	}

	/* 5.�û��һ�����-����������ҳ�� */
	@RequestMapping(value = "userSetNew")
	public String UserSetNew(HttpSession session) {
		if (session.getAttribute("find") != null) {/* �жϵ�ǰ�Ƿ����һ��������� */
			return "user/usersetnew";
		}
		return "login";
	}

	/* 6.�û��һ�����-���������� */
	@RequestMapping(value = "userSetNew1")
	public @ResponseBody String UserSetNew1(User user, HttpSession session) {
		Map attribute = (Map) session.getAttribute("find");
		String email = (String) attribute.get("email");
		System.out.println(email);
		userService.usersetpw(email, Md5.md5Password(user.getUpw()));
		session.removeAttribute("find");
		return "success";
	}
}
