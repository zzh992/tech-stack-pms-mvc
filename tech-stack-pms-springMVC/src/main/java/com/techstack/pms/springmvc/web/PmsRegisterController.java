package com.techstack.pms.springmvc.web;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.techstack.component.spring.mvc.SpringMVCBaseController;
import com.techstack.pms.biz.PmsUserBiz;
import com.techstack.pms.dao.dto.PmsUserDTO;
import com.techstack.pms.enums.UserTypeEnum;

@Controller
//@RequestMapping("/register_")
public class PmsRegisterController extends SpringMVCBaseController{

	private static final Log log = LogFactory.getLog(PmsRegisterController.class);
	
	@Autowired
	private PmsUserBiz pmsUserBiz;
	
	/**
	 * @Description: 进入注册页面
	 * @param @return    
	 * @return String
	 */
	@RequestMapping("/register_register.action")
	public String register(){
		return "register.jsp";
	}
	
	/**
	 * @Description: 用户注册保存
	 * @param @return    
	 * @return String
	 */
	@RequestMapping("/register_userSave.action")
	public ModelAndView userSave(){
		ModelAndView mav = new ModelAndView("login.jsp");
		ModelMap modelMap = new ModelMap();
		String loginName = getString("loginName");
		PmsUserDTO user  = pmsUserBiz.findUserByLoginName(loginName);
		if(user!=null){
			modelMap.put("loginNameMsg", "用户名已被注册");
			log.info("==== info ==== 用户【"+loginName+"】已被注册");
			mav.setViewName("register.jsp");
			mav.addAllObjects(modelMap);
			return mav;
		}
		user = new PmsUserDTO();
		user.setLoginName(loginName);
		String loginPwd =DigestUtils.sha1Hex(getString("loginPwd"));
		user.setLoginPwd(loginPwd);
		user.setType(UserTypeEnum.USER.getValue());
		//pmsUserBiz.create(user);
		//pmsUserBiz.saveUser(user, "4");
		pmsUserBiz.saveUser(user, Arrays.asList(4L));
		log.info("==== info ==== 用户【"+loginName+"】注册成功");
		mav.addAllObjects(modelMap);
		return mav;
	}
}
