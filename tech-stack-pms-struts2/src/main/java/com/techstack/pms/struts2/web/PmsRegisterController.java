package com.techstack.pms.struts2.web;

import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.component.struts2.Struts2BaseController;
import com.techstack.pms.biz.PmsUserBiz;
import com.techstack.pms.dao.dto.PmsUserDTO;
import com.techstack.pms.enums.UserTypeEnum;

/**
 * @Title: PmsRegisterAction.java 
 * @Description: 用户注册ACTION
 * @author zzh
 */
public class PmsRegisterController extends Struts2BaseController{

	private static final long serialVersionUID = 1L;
	
	private static final Log log = LogFactory.getLog(PmsRegisterController.class);
	
	@Autowired
	private PmsUserBiz pmsUserBiz;
	
	/**
	 * @Description: 进入注册页面
	 * @param @return    
	 * @return String
	 */
	public String register(){
		return "register";
	}
	
	/**
	 * @Description: 用户注册保存
	 * @param @return    
	 * @return String
	 */
	public String userSave(){
		String loginName = getString("loginName");
		PmsUserDTO user  = pmsUserBiz.findUserByLoginName(loginName);
		if(user!=null){
			this.putData("loginNameMsg", "用户名已被注册");
			log.info("==== info ==== 用户【"+loginName+"】已被注册");
			return "register";
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
		return "login";
	}
}
