package com.techstack.pms.struts2.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.component.shiro.ShiroUser;
import com.techstack.component.struts2.Struts2BaseController;
import com.techstack.pms.biz.PmsActionBiz;
import com.techstack.pms.biz.PmsMenuBiz;
import com.techstack.pms.biz.PmsRoleBiz;
import com.techstack.pms.biz.PmsUserBiz;
import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsMenuDTO;
import com.techstack.pms.dao.dto.PmsUserDTO;
import com.techstack.pms.enums.NodeTypeEnum;

/**
 * @Title: PmsLoginAction.java 
 * @Description: 用户登录ACTION
 * @author zzh
 */
public class PmsLoginController extends Struts2BaseController {
	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(PmsLoginController.class);

	@Autowired
	private PmsActionBiz pmsActionBiz;
	@Autowired
	private PmsRoleBiz pmsRoleBiz;
	@Autowired
	private PmsUserBiz pmsUserBiz;
	@Autowired
	private PmsMenuBiz pmsMenuBiz;

	/**
	 * 进入登录页面.
	 * 
	 * @return
	 */
	public String loginPage() {
		Object error = getHttpRequest().getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if(error != null){
			this.putData("loginInfo", "Login fail, please check the username or password");
		}
		return "login";
	}

	/**
	 * 登录验证Action
	 * 
	 * @return
	 * @throws Exception
	 */
	/*public String userLogin() {
		try {
			String loginName = getString("loginName");

			if (StringUtils.isBlank(loginName)) {
				this.putData("loginNameMsg", "用户名不能为空");
				return "input";
			}

			this.putData("loginName", loginName);
			PmsUser user = pmsUserBiz.findUserByLoginName(loginName);
			if (user == null) {
				this.putData("loginNameMsg", "用户名不存在");
				return "input";
			}

			String pwd = getString("loginPwd");
			if (StringUtils.isBlank(pwd)) {
				this.putData("loginPwdMsg", "密码不能为空");
				return "input";
			}

			// 加密明文密码
			// 验证密码
			if (user.getLoginPwd().equals(DigestUtils.sha1Hex(pwd))) {// 密码正确
				// 用户信息，包括登录信息和权限
				Map<String, Object> userInfoMap = new HashMap<String, Object>();
				userInfoMap.put("pmsUser", user);
				userInfoMap.put("pmsAction", getActions(user));

				getSessionMap().put("userInfoMap", userInfoMap);
				this.putData("loginName", loginName);
				
				try {
					this.putData("tree", buildUserPermissionMenu(user));
					pmsUserBiz.update(user);

				} catch (Exception e) {
					log.error("==== error ==== 登录出现异常",e);
					return "input";
				}
				log.info("==== info ==== 用户"+loginName+"登录系统");
				return "main";

			} else {
				// 密码错误
				log.warn("== wrongPassword");
				String msg = "密码错误";

				pmsUserBiz.update(user);
				this.putData("loginPwdMsg", msg);
				return "input";
			}

		} catch (RuntimeException e) {
			log.error("==== error ==== 登录出现异常：", e);
			this.putData("errorMsg", "登录出错");
			return "input";
		} catch (Exception e) {
			log.error("==== error ==== 登录出现异常：", e);
			this.putData("errorMsg", "登录出错");
			return "input";
		}
	}*/
	
	public String mainpage(){
		// 用户信息，包括登录信息和权限
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Map<String, Object> userInfoMap = new HashMap<String, Object>();
		PmsUserDTO user = pmsUserBiz.findUserByLoginName(shiroUser.getUsername());
		userInfoMap.put("pmsUser", user);
		userInfoMap.put("pmsAction", getActions(user));

		getSessionMap().put("userInfoMap", userInfoMap);
		this.putData("loginName", shiroUser.getUsername());
		
		try {
			this.putData("tree", buildUserPermissionMenu(user));
			//pmsUserBiz.update(user); TODO:为什么update

		} catch (Exception e) {
			log.error("==== error ==== 登录出现异常",e);
			return "input";
		}
		log.info("==== info ==== 用户"+shiroUser.getUsername()+"登录系统");
		return "main";
	}

	/**
	 * 跳转到退出确认页面.
	 * 
	 * @return LogOutConfirm.
	 */
	public String logoutConfirm() {
		return "logoutConfirm";
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {
		getSessionMap().clear();
		return "logout";
	}

	/**
	 * 跳转到登录超时确认页面.
	 * 
	 * @return LogOutConfirm.
	 * @throws Exception
	 */
	public String timeoutConfirm() throws Exception {
		return "timeoutConfirm";
	}

	/**
	 * 获取用户的权限标识字符串，以逗号分隔
	 * @param pmsUser
	 * @return
	 */
	private List<String> getActions(PmsUserDTO pmsUser) {
		// 根据用户ID得到该用户的所有角色拼成的字符串
		List<Long> roleIds = pmsRoleBiz.getRoleIdsByUserId(pmsUser.getId());
		// 根据角色ID字符串得到该用户的所有权限拼成的字符串
		List<Long> actionIds = new ArrayList<Long>();
		if (roleIds!=null && !roleIds.isEmpty()) {
			actionIds = pmsActionBiz.getActionIdsByRoleIds(roleIds);
		}
		// 根据权限ID字符串得到权限列表
		List<PmsActionDTO> pmsActionList = new ArrayList<PmsActionDTO>();
		if (!"".equals(actionIds)) {
			pmsActionList = pmsActionBiz.findActionsByIds(actionIds);
		}
		
		List<String> actionList = new ArrayList<String>();
		for (PmsActionDTO pmsAction : pmsActionList) {
			actionList.add(pmsAction.getAction());
		}
		log.info("==== info ==== 用户"+pmsUser.getLoginName()+"有"+actionList.size()+"个权限点");
		return actionList;
	}

	/**
	 * 获取用户的菜单权限
	 * @param pmsUser
	 * @return
	 * @throws Exception
	 */
	private String buildUserPermissionMenu(PmsUserDTO pmsUser){
		// 根据用户ID得到该用户的所有角色拼成的字符串
		List<Long> roleIds = pmsRoleBiz.getRoleIdsByUserId(pmsUser.getId());
		if (roleIds == null || roleIds.isEmpty()) {
			log.info("==== info ==== 用户[" + pmsUser.getLoginName() + "]没有配置对应的权限角色");
			throw new RuntimeException("该帐号已被取消所有系统权限");
		}
		// 根据操作员拥有的角色ID,构建管理后台的树形权限功能菜单
		List treeData = pmsMenuBiz.listByRoleIds(roleIds);
		StringBuffer strJson = new StringBuffer();
		buildAdminPermissionTree(0L, strJson, treeData); //从一级菜单开始构建
		return strJson.toString();
	}
	
	/**
	 * @Description: 构建管理后台的树形权限功能菜单
	 * @param @param pId
	 * @param @param treeBuf
	 * @param @param menuList    
	 * @return void
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildAdminPermissionTree(Long pId, StringBuffer treeBuf, List menuList) {
		List<PmsMenuDTO> sonMenuList = getSonMenuListByPid(pId, menuList);
		for (PmsMenuDTO sonMenu : sonMenuList) {
			Long id = sonMenu.getId();
			String name = sonMenu.getName();
			Integer isLeaf = sonMenu.getIsLeaf();
			String url = sonMenu.getUrl();
			
			String navTabId = "";
			if (!StringUtils.isEmpty(sonMenu.getTargetName())) {
				navTabId = sonMenu.getTargetName(); // 用于刷新查询页面
			}
			
			if(sonMenu.getParentId() == 0) {	//若是一级菜单
				treeBuf.append("<div class='accordionHeader'>");
				treeBuf.append("<h2>" + name + "</h2>");
				treeBuf.append("</div>");
				treeBuf.append("<div class='accordionContent'>");
			}
			
			if (isLeaf == NodeTypeEnum.LEAF.getValue()) {	//若是叶子节点
				treeBuf.append("<li><a href='" + url + "' target='navTab' rel='" + navTabId + "'>" + name + "</a></li>");
			} else {
				if(sonMenu.getParentId() == 0) {	//若是一级菜单
					treeBuf.append("<ul class='tree treeFolder'>");
				} else {
					treeBuf.append("<li><a>" + name + "</a>");
					treeBuf.append("<ul>");
				}
				
				buildAdminPermissionTree(id, treeBuf, menuList);
				
				if(sonMenu.getParentId() == 0) {	//若是一级菜单
					treeBuf.append("</ul>");
				} else {
					treeBuf.append("</ul></li>");
				}
				
			}
			
			if(sonMenu.getParentId() == 0) {	//若是一级菜单
				treeBuf.append("</div>");
			}
		}

	}
	
	/**
	 * @Description: 根据(pId)获取(menuList)中的所有子菜单集合.
	 * @param @param pId
	 * @param @param menuList
	 * @param @return    
	 * @return List<Map>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<PmsMenuDTO> getSonMenuListByPid(Long pId, List<PmsMenuDTO> menuList) {
		List sonMenuList = new ArrayList<PmsMenuDTO>();
		for (PmsMenuDTO menu : menuList) {
			if (menu != null) {
				Long parentId = menu.getParentId();
				if (parentId == pId) {
					sonMenuList.add(menu);
				}
			}
		}
		return sonMenuList;
	}


}
