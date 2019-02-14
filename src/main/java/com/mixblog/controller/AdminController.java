package com.mixblog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mixblog.pojo.Admin;
import com.mixblog.pojo.Code;
import com.mixblog.pojo.Md5;
import com.mixblog.pojo.Notice;
import com.mixblog.pojo.Page;
import com.mixblog.pojo.Resources;
import com.mixblog.pojo.Sign;
import com.mixblog.pojo.User;
import com.mixblog.service.AdminService;
import com.mixblog.service.CodeService;
import com.mixblog.service.ResourcesService;
import com.mixblog.service.SignService;
import com.mixblog.service.NoticeService;
import com.mixblog.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userservice;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private SignService signService;

	/* ����Ա��¼ҳ�� */
	@RequestMapping(value = "adminlogin")
	public String AdminLogin(HttpSession session) {
		session.removeAttribute("admin");
		return "admin/adminlogin";
	}

	/* ����Ա��½�ӿ� */
	@RequestMapping(value = "adminlogin1")
	public @ResponseBody String AdminLogin1(Admin admin, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		Admin select = adminService.adminselect(admin.getAuser(), admin.getApw());
		if (select != null) {
			/*
			 * cookie���� ʱ�� Cookie cookie = new Cookie("key","value");
			 * cookie.setMaxAge(9999999); cookie.setPath(request.getContextPath());
			 * response.addCookie(cookie); System.out.println(cookie.getMaxAge());
			 * System.out.println(cookie); System.out.println(cookie.getName());
			 * System.out.println(cookie.getValue());
			 */
			
			session.setAttribute("admin", select);
			session.setMaxInactiveInterval(99999999);
			return "ok";
		} else {
			return "no";
		}
	}

	/* �޸Ĺ���Ա����ҳ�� */
	@RequestMapping(value = "adminpw")
	public String AdminPw() {
		return "admin/adminpwed";
	}

	/* �޸Ĺ���Ա����ӿ� */
	@RequestMapping(value = "adminpwedit")
	public @ResponseBody String AdminPwedit(Admin admin, HttpSession session) {
		Admin admin1 = (Admin) session.getAttribute("admin");
		if (admin.getApw() != null) {
			adminService.adminpwedit(admin1.getAid(), admin.getApw());
			return "ok";
		} else {

			return "no";
		}
	}

	/* ������ҳҳ�� */
	@RequestMapping(value = "adminhome")
	public String AdminHome(ModelMap map) {
		/* �û����� */
		Integer c = userservice.ucount();
		/* Vip���� */
		Integer v = userservice.ucountvip();
		/* ��Դ���� */
		Integer r = resourcesService.rcount();
		Integer rfl = resourcesService.rflspcount();
		Integer rsy = resourcesService.rsyspcount();
		Integer rjp = resourcesService.rjpttcount();
		Integer rzx = resourcesService.rzxspcount();
		/* ��ֵ������ */
		Integer co = codeService.ccount();
		Integer cou = codeService.cucount();
		Integer con = codeService.cncount();
		map.put("c", c);
		map.put("r", r);
		map.put("v", v);
		map.put("rfl", rfl);
		map.put("rsy", rsy);
		map.put("rjp", rjp);
		map.put("rzx", rzx);
		map.put("cou", cou);
		map.put("con", con);
		map.put("co", co);
		return "admin/home";
	}

	/* �����б�ҳ�� */
	@RequestMapping(value = "adminpost")
	public String AdminPost(Model model, Resources resources, HttpServletRequest request) {
		// ��ȡ��ǰҳ��
		String pageNow = request.getParameter("pageNow");
		// ��ȡ��ҳ��
		int totalCount = (Integer) resourcesService.rcount();
		Page page = null;
		List<Resources> list = new ArrayList<Resources>();
		if (pageNow != null) {
			page = new Page(Integer.parseInt(pageNow), totalCount);
			list = this.resourcesService.rlist(page.getStartPos(), page.getPageSize());
		} else {
			page = new Page(1, totalCount);
			list = this.resourcesService.rlist(page.getStartPos(), page.getPageSize());
		}
		model.addAttribute("rlist", list);
		model.addAttribute("page", page);
		model.addAttribute("totalCount", totalCount);
		return "admin/adminpost";
	}

	/* �������� */
	@RequestMapping(value = "searchresources")
	public String SearchR(String rname, Model model) {
		List<Resources> rlist = resourcesService.rsearch(rname);
		model.addAttribute("rlist", rlist);
		return "admin/adminpost";

	}

	/* д������ҳ�� */
	@RequestMapping(value = "adminnewpost")
	public String AdminNewPost() {
		return "admin/adminnewpost";
	}

	/* д�����½ӿ� */
	@RequestMapping(value = "adminnewpost1")
	public @ResponseBody String AdminNewPost1(Resources resources, HttpServletRequest reqeust) {
		Integer r = resourcesService.rsrname(resources.getRname());
		if (r == 1) {
			return "no";
		} else {
			Pattern p_img = Pattern.compile("(<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>)");
			Matcher m_img = p_img.matcher(reqeust.getParameter("rcontent"));
			while (m_img.find()) {
				System.out.println(m_img);
				String img = m_img.group(2); // m_img.group(1) Ϊ�������img��ǩ m_img.group(2) Ϊ���src��ֵ
				System.out.println(img);
				resources.setRpic(img);
				break;
			}
			resourcesService.radd(resources);
			return "ok";
		}

	}

	/* ���±༭ҳ�� */
	@RequestMapping(value = "adminpedit")
	public String AdminPedit(Integer rid, HttpSession session) {
		Resources resources = resourcesService.rselectno(rid);
		session.setAttribute("resourcesed", resources);
		return "admin/adminpostedit";
	}

	/* ���±༭�ӿ� */
	@RequestMapping(value = "adminpedit1")
	public @ResponseBody String AdminPedit1(Resources resources, HttpSession session, HttpServletRequest reqeust) {
		Resources resources1 = (Resources) session.getAttribute("resourcesed");
		resources.setRid(resources1.getRid());
		if (resources.getRid() != null) {
			String content = reqeust.getParameter("rcontent");
			String url = content.split("src=")[1].split("\"")[1];
			resources.setRpic(url);
			resourcesService.pupdate(resources);
			return "ok";
		} else {
			return "no";
		}
	}

	/* ɾ�����½ӿ� */
	@RequestMapping(value = "adminpdel")
	public String AdminPdel(Integer rid) {
		resourcesService.adminpdel(rid);
		return "redirect:adminpost";

	}

	/* ��Ա�б�ҳ�� */
	@RequestMapping(value = "adminuser")
	public String AdminUser(Model model, User user, HttpServletRequest request) {
		// ��ȡ��ǰҳ��
		String pageNow = request.getParameter("pageNow");
		// ��ȡ��ҳ��
		int totalCount = (Integer) userservice.ucount();
		Page page = null;
		List<User> list = new ArrayList<User>();
		if (pageNow != null) {
			page = new Page(Integer.parseInt(pageNow), totalCount);
			list = this.userservice.uuserlist(page.getStartPos(), page.getPageSize());
		} else {
			page = new Page(1, totalCount);
			list = this.userservice.uuserlist(page.getStartPos(), page.getPageSize());
		}

		model.addAttribute("ulist", list);
		model.addAttribute("page", page);
		model.addAttribute("totalCount", totalCount);
		return "admin/adminuser";
	}

	/* ������Ա */
	@RequestMapping(value = "searchuser")
	public String SearchUser(String uname, Model model) {
		List<User> ulist = userservice.usearch(uname);
		System.out.println(ulist);
		model.addAttribute("ulist", ulist);
		return "admin/adminusers";
	}

	/* �����Ա */
	@RequestMapping(value = "manageruser")
	public String Manageruser(Integer uid, HttpSession session) {
		User user = userservice.uselect(uid);
		session.setAttribute("user1", user);
		return "admin/adminuseredit";
	}

	/* ����Ա��˱����û���Ϣ�ӿ� */
	@RequestMapping(value = "adusersave")
	public @ResponseBody String AdUserSave(User user, HttpSession session) {
		User attribute = (User) session.getAttribute("user1");
		System.out.println(user.getUpw());
		attribute.setUpw(Md5.md5Password(user.getUpw()));
		user.setUid(attribute.getUid());
		user.setUpw(attribute.getUpw());
		System.out.println(user.getUpw());
		userservice.adusersave(user);
		return "ok";
	}

	/* ɾ����Ա */
	@RequestMapping(value = "aduserdel")
	public String AduserDel(Integer uid) {
		userservice.aduserdel(uid);
		return "redirect:adminuser";

	}

	/* ��ֵ�� */
	@RequestMapping(value = "admincode")
	public String AdminCode(Model model, HttpServletRequest request) {
		// ��ȡ��ǰҳ��
		String pageNow = request.getParameter("pageNow");
		// ��ȡ��ҳ��
		int totalCount = (Integer) codeService.ccount();
		Page page = null;
		List<Code> list = new ArrayList<Code>();
		if (pageNow != null) {
			page = new Page(Integer.parseInt(pageNow), totalCount);
			list = this.codeService.clist(page.getStartPos(), page.getPageSize());
		} else {
			page = new Page(1, totalCount);
			list = this.codeService.clist(page.getStartPos(), page.getPageSize());
		}

		model.addAttribute("code", list);
		model.addAttribute("page", page);
		model.addAttribute("totalCount", totalCount);
		return "admin/admincode";

	}

	/* ������ֵ�� */
	@RequestMapping(value = "searchcode")
	public String SearchC(String codevar, Model model) {
		List<Code> clist = codeService.csearch(codevar);
		model.addAttribute("code", clist);
		return "admin/admincode";

	}

	/* ��ӳ�ֵ��ҳ�� */
	@RequestMapping(value = "admincodenew")
	public String AdminCodeNew(Model model) {
		return "admin/admincodenew";
	}

	/* ��ӳ�ֵ��ӿ� */
	@RequestMapping(value = "admincode1")
	public @ResponseBody String AdminCode1(Code code) {
		System.out.println();
		Integer ccodeselect = codeService.ccodeselect(code);
		if (ccodeselect == null) {
			codeService.ccodeadd(code);
			return "ok";
		} else {
			return "no";
		}
	}

	/* �˳���̨ϵͳ */
	@RequestMapping(value = "adminexit")
	public String AdminExit(HttpSession session) {
		session.removeAttribute("admin");
		return "redirect:adminlogin";
	}

	/* ������ҳ�� */
	@RequestMapping(value = "adminnotice")
	public String AdminNotice(Model model) {
		List<Notice> system = noticeService.slist();
		model.addAttribute("system", system);
		return "admin/adminnotice";
	}

	/* ����༭ҳ�� */
	@RequestMapping(value = "adminnedit")
	public String AdminNedit(Integer sid, HttpSession session) {
		Notice notice = noticeService.sselect(sid);
		session.setAttribute("noticeed", notice);
		return "admin/adminnedit";
	}

	/* ����༭�ӿ� */
	@RequestMapping(value = "adminnedit1")
	public @ResponseBody String AdminNedit1(Notice notice, HttpSession session) {
		Notice notice1 = (Notice) session.getAttribute("noticeed");
		notice.setSid(notice1.getSid());
		if (notice.getSid() != null) {
			noticeService.nupdate(notice);
			return "ok";
		} else {
			return "no";
		}
	}

	/* ������¼ */
	@RequestMapping(value = "development")
	public String Development() {
		return "admin/development";

	}

	/* ��¼��¼ */
	@RequestMapping(value = "adminsign")
	public String AdminSign(Model model, HttpServletRequest request) {
		// ��ȡ��ǰҳ��
		String pageNow = request.getParameter("pageNow");
		// ��ȡ��ҳ��
		int totalCount = (Integer) signService.scount();
		Page page = null;
		List<Sign> list = new ArrayList<Sign>();
		if (pageNow != null) {
			page = new Page(Integer.parseInt(pageNow), totalCount);
			list = this.signService.slist(page.getStartPos(), page.getPageSize());
		} else {
			page = new Page(1, totalCount);
			list = this.signService.slist(page.getStartPos(), page.getPageSize());
		}

		model.addAttribute("slist", list);
		model.addAttribute("page", page);
		model.addAttribute("totalCount", totalCount);

		/*
		 * List<Sign> slist=signService.slist(); model.addAttribute("slist", slist);
		 */
		return "admin/adminsign";

	}
}
