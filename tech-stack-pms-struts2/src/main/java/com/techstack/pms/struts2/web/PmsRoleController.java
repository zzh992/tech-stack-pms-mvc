package com.techstack.pms.struts2.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.techstack.component.dwz.DwzUtils;
import com.techstack.component.shiro.ShiroUser;
import com.techstack.component.struts2.Struts2BaseController;
import com.techstack.pms.biz.PmsActionBiz;
import com.techstack.pms.biz.PmsMenuBiz;
import com.techstack.pms.biz.PmsRoleBiz;
import com.techstack.pms.biz.PmsUserBiz;
import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsMenuDTO;
import com.techstack.pms.dao.dto.PmsRoleDTO;
import com.techstack.pms.dao.dto.PmsUserDTO;
import com.techstack.pms.enums.NodeTypeEnum;
import com.techstack.pms.enums.RoleTypeEnum;
import com.techstack.pms.enums.UserTypeEnum;

/**
 * @Title: PmsRoleAction.java 
 * @Description: 角色管理ACTION
 * @author zzh
 */
public class PmsRoleController extends Struts2BaseController{

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(PmsRoleController.class);
	
	@Autowired
	private PmsActionBiz pmsActionBiz;
	@Autowired
	private PmsRoleBiz pmsRoleBiz;
	@Autowired
	private PmsUserBiz pmsUserBiz;
	@Autowired
	private PmsMenuBiz pmsMenuBiz;
	
	
	/**
	 * @Description: 获取角色列表
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:role:view")
	@RequiresPermissions("pms:role:view")
	public String pmsRoleList() {
		try {

			Map<String, Object> paramMap = new HashMap<String, Object>(); // 业务条件查询参数
			paramMap.put("roleName", getString("roleName")); // 角色名称（模糊查询）
			paramMap.put("module", "pmsRole");
			Page<PmsRoleDTO> pageBean = pmsRoleBiz.listPage(DwzUtils.getPageNum(ServletActionContext.getRequest()), DwzUtils.getNumPerPage(ServletActionContext.getRequest()), paramMap);

			ShiroUser user = this.getCurrentUser();
			this.pushData(user);
			this.putData("pageImpl", pageBean);
			//this.pushData(pageBean);
			// 回显查询条件值
			this.pushData(paramMap);		
			
			this.putData("RoleTypeEnumList", RoleTypeEnum.toList());
			this.putData("RoleTypeEnum", RoleTypeEnum.toMap());
			this.putData("UserTypeEnum", UserTypeEnum.toMap());
			
			return "pmsRoleList";
		} catch (Exception e) {
			log.error("==== error ==== 查询角色失败：", e);
			return DwzUtils.operateErrorInStruts2("获取数据失败");
		}
	}

	/**
	 * @Description: 转到添加角色页面 .
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:role:add")
	@RequiresPermissions("pms:role:add")
	public String pmsRoleAdd() {
		try {
			return "pmsRoleAdd";
		} catch (Exception e) {
			log.error("==== error ==== 进入角色添加页面失败", e);
			return DwzUtils.operateErrorInStruts2("获取数据失败");
		}
	}

	/**
	 * @Description: 保存新添加的一个角色 
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:role:add")
	@RequiresPermissions("pms:role:add")
	public String pmsRoleSave() {
		try {
			String roleName = getString("roleName");
			PmsRoleDTO roleCheck = pmsRoleBiz.getByRoleName(roleName);
			if (roleCheck != null) {
				return DwzUtils.operateErrorInStruts2("角色名【" + roleName + "】已存在");
			}

			// 保存基本角色信息
			PmsRoleDTO pmsRole = new PmsRoleDTO();
			pmsRole.setRoleType(RoleTypeEnum.USER.getValue()); // 角色类型（1:超级管理员角色，0:普通用户角色）
			pmsRole.setRoleName(roleName);
			pmsRole.setRemark(getString("desc"));
			pmsRole.setCreateTime(new Date());

			// 表单数据校验
			String validateMsg = validatePmsRole(pmsRole);
			if (StringUtils.isNotBlank(validateMsg)) {
				return DwzUtils.operateErrorInStruts2(validateMsg); // 返回错误信息
			}

			pmsRoleBiz.saveRole(pmsRole);
			log.info("==== info ==== 添加角色【"+roleName+"】成功");
			return DwzUtils.operateSuccessInStruts2("操作成功");
		} catch (Exception e) {
			log.error("==== error ==== 添加角色失败：", e);
			return DwzUtils.operateErrorInStruts2("保存数据失败");
		}
	}

	/**
	 * @Description: 校验角色表单数据
	 * @param @param pmsRole
	 * @param @return    
	 * @return String
	 */
	private String validatePmsRole(PmsRoleDTO pmsRole) {
		String msg = ""; // 用于存放校验提示信息的变量
		String roleName = pmsRole.getRoleName(); // 角色名称
		String desc = pmsRole.getRemark(); // 描述
		// 角色名称 actionName
		msg += DwzUtils.lengthValidate("角色名称", roleName, true, 3, 90);
		// 描述 desc
		msg += DwzUtils.lengthValidate("描述", desc, true, 3, 300);
		return msg;
	}

	/**
	 * @Description:  转到角色修改页面 .
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:role:edit")
	@RequiresPermissions("pms:role:edit")
	public String pmsRoleEdit() {
		try {
			Long roleId = getLong("roleId");
			PmsRoleDTO pmsRole = pmsRoleBiz.getById(roleId);
			if (pmsRole == null) {
				return DwzUtils.operateErrorInStruts2("获取数据失败");
			}

			// 普通用户没有修改超级管理员角色的权限
			if (UserTypeEnum.USER.getValue().equals(this.getCurrentUser().getType()) 
			 && RoleTypeEnum.ADMIN.getValue().equals(pmsRole.getRoleType())) {
				return DwzUtils.operateErrorInStruts2("你没有修改超级管理员角色的权限");
			}

			this.pushData(pmsRole);
			
			return "pmsRoleEdit";
		} catch (Exception e) {
			log.error("==== error ==== 进入修改角色页面失败：", e);
			return DwzUtils.operateErrorInStruts2("获取数据失败");
		}
	}

	/**
	 * @Description: 保存修改后的角色信息
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:role:edit")
	@RequiresPermissions("pms:role:edit")
	public String pmsRoleUpdate() {
		try {
			Long id = getLong("id");

			PmsRoleDTO pmsRole = pmsRoleBiz.getById(id);
			if (pmsRole == null) {
				return DwzUtils.operateErrorInStruts2("无法获取要修改的数据");
			}

			// 普通用户没有修改超级管理员角色的权限
			if (UserTypeEnum.USER.getValue().equals(this.getCurrentUser().getType()) 
			 && RoleTypeEnum.ADMIN.getValue().equals(pmsRole.getRoleType())) {
				return DwzUtils.operateErrorInStruts2("你没有修改超级管理员角色的权限");
			}

			String roleName = getString("roleName");
			PmsRoleDTO roleCheck = pmsRoleBiz.findByRoleNameNotEqId(id, roleName);
			if (roleCheck != null) {
				return DwzUtils.operateErrorInStruts2("角色名【" + roleName + "】已存在");
			}

			pmsRole.setRoleName(roleName);
			pmsRole.setRemark(getString("remark"));

			// 表单数据校验
			String validateMsg = validatePmsRole(pmsRole);
			if (StringUtils.isNotBlank(validateMsg)) {
				return DwzUtils.operateErrorInStruts2(validateMsg); // 返回错误信息
			}

			pmsRoleBiz.updateRole(pmsRole);
			log.info("==== info ==== 修改角色【"+roleName+"】成功");
			return DwzUtils.operateSuccessInStruts2("操作成功");
		} catch (Exception e) {
			log.error("==== error ==== 修改角色失败", e);
			return DwzUtils.operateErrorInStruts2("保存失败");
		}
	}

	/**
	 * @Description: 删除一个角色
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:role:delete")
	@RequiresPermissions("pms:role:delete")
	public String pmsRoleDel() {
		try {
			Long roleId = getLong("roleId");

			PmsRoleDTO role = pmsRoleBiz.getById(roleId);
			if (role == null) {
				return DwzUtils.operateErrorInStruts2("无法获取要删除的角色");
			}
			if (RoleTypeEnum.ADMIN.getValue().equals(role.getRoleType())) {
				return DwzUtils.operateErrorInStruts2("超级管理员角色不可删除");
			}

			String msg = "";
			// 判断是否有用户关联到此角色
			int userCount = pmsUserBiz.countUserByRoleId(roleId);
			if (userCount > 0) {
				msg += "【" + userCount + "】个用户";
			}
			// 判断是否有权限关联到此角色 
			// int actionCount = pmsActionBiz.countActionByRoleId(roleId);
			// if (actionCount > 0){
			// msg += "【"+actionCount+"】个权限";
			// }
			// // 判断是否有菜单关联到此角色
			// int menuCount = pmsMenuBiz.countMenuByRoleId(roleId);
			// if (menuCount > 0){
			// msg += "【"+menuCount+"】个菜单";
			// }

			if (StringUtils.isNotBlank(msg)) {
				msg += "关联到此角色，要先解除所有关联后才能删除!";
				return DwzUtils.operateErrorInStruts2("有" + msg);
			}
			
			pmsRoleBiz.deleteRoleById(roleId);
			log.info("==== info ==== 删除角色成功");
			return DwzUtils.operateSuccessInStruts2("操作成功");
		} catch (Exception e) {
			log.error("==== error ==== 删除角色失败", e);
			return DwzUtils.operateErrorInStruts2("删除失败");
		}
	}

	/**
	 * @Description: 分配权限UI
	 * @param @return    
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	//@Permission("pms:role:edit")
	@RequiresPermissions("pms:role:edit")
	public String assignPermissionUI() {
		Long roleId = getLong("roleId");

		PmsRoleDTO role = pmsRoleBiz.getById(roleId);
		if (role == null) {
			return DwzUtils.operateErrorInStruts2("无法获取角色信息");
		}
		// 普通用户没有修改超级管理员角色的权限
		if (UserTypeEnum.USER.getValue().equals(this.getCurrentUser().getType()) 
		 && RoleTypeEnum.ADMIN.getValue().equals(role.getRoleType())) {
			return DwzUtils.operateErrorInStruts2("你没有修改超级管理员角色的权限");
		}

		String menuIds = "";
		//String actionIds = "";
		List<Long> actionIds = new ArrayList<Long>();
		try {
			menuIds = pmsMenuBiz.getMenuIdsByRoleId(roleId); // 根据角色查找角色对应的菜单ID集
			actionIds = pmsActionBiz.getActionIdsByRoleId(roleId); // 根据角色查找角色对应的功能权限ID集
		} catch (Exception e) {
			log.error("==== error ==== 根据角色ID，找不到对应的菜单、权限", e);
		}

		// 前面加个逗号，方便接下来的处理
		menuIds = "," + menuIds;
		//actionIds = "," + actionIds;
		
		List allMenuList = pmsMenuBiz.getMenuByPid(null); // 获取所有的菜单
		StringBuffer treeBuf = new StringBuffer();
		buildPermissionTree(0L, treeBuf, allMenuList, menuIds, actionIds); //从一级菜单开始构建

		this.putData("menuActionTree", treeBuf.toString());

		// 查询角色对应的用户
		List<PmsUserDTO> userList = (List<PmsUserDTO>) pmsUserBiz.listUserByRoleId(roleId);
		this.putData("userList", userList);

		this.putData("roleId", roleId);
		return "assignPermissionUI";
	}
	
	/**
	 * @Description: 创建分配权限的菜单树
	 * @param @param pId
	 * @param @param treeBuf
	 * @param @param allMenuList
	 * @param @param menuIds
	 * @param @param actionIds    
	 * @return void
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildPermissionTree(Long pId, StringBuffer treeBuf, List allMenuList, String menuIds, List<Long> actionIds) {
		if (pId == 0) {  // 为一级菜单
			treeBuf.append("<ul class=\"tree treeFolder treeCheck expand\" >");
		} else {
			treeBuf.append("<ul>");
		}

		List<PmsMenuDTO> sonMenuList = getSonMenuListByPid(pId, allMenuList);
		for (PmsMenuDTO sonMenu : sonMenuList) {
			Long menuId = sonMenu.getId();
			Long parentId = sonMenu.getParentId();
			String name = sonMenu.getName();
			Integer isLeaf = sonMenu.getIsLeaf();
			if (menuIds.indexOf("," + menuId + ",") > -1) {
				treeBuf.append("<li><a menuid='" + menuId + "' checked='true' pid='" + parentId + "' isleaf='" + isLeaf + "'>" + name + " (M)</a>");
			} else {
				treeBuf.append("<li><a menuid='" + menuId + "' pid='" + parentId + "' isleaf='" + isLeaf + "'>" + name + " (M)</a>");
			}
			if (isLeaf == NodeTypeEnum.LEAF.getValue()) {  // 如果叶子菜单，则处理挂在此菜单下的权限功能点

				// 获取叶子菜单下所有的功能权限
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("menuId", Long.valueOf(menuId));
				List<PmsActionDTO> actionList = pmsActionBiz.listByMenuId(menuId);
				if (null != actionList && !actionList.isEmpty()) {
					treeBuf.append("<ul>");
					for (int j = 0; j < actionList.size(); j++) {
						PmsActionDTO action = actionList.get(j);
						//if (actionIds.indexOf("," + action.getId().toString() + ",") > -1) {
						if (actionIds.indexOf(action.getId()) > -1) {
							treeBuf.append("<li><a checked='true' actionid='" + action.getId() + "'>" + action.getActionName() + " (A)</a>");
						} else {
							treeBuf.append("<li><a actionid='" + action.getId() + "'>" + action.getActionName() + " (A)</a>");
						}
					}
					treeBuf.append("</ul>");
				}

			} else {
				// 不是叶子菜单，递归
				buildPermissionTree(menuId, treeBuf, allMenuList, menuIds, actionIds);
			}
			treeBuf.append("</li>");
		}

		treeBuf.append("</ul>");
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

	/**
	 * @Description: 分配角色权限
	 * @param     
	 * @return void
	 */
	//@Permission("pms:role:edit")
	@RequiresPermissions("pms:role:edit")
	public void assignPermission() {
		try {

			Long roleId = getLong("roleId");

			PmsRoleDTO role = pmsRoleBiz.getById(roleId);
			if (role == null) {
				getOutputMsg().put("MSG", "无法获取角色信息");
				return;
			}
			// 普通用户没有修改超级管理员角色的权限
			if (UserTypeEnum.USER.getValue().equals(this.getCurrentUser().getType()) 
					 && RoleTypeEnum.ADMIN.getValue().equals(role.getRoleType())) {
				getOutputMsg().put("MSG", "你没有修改超级管理员角色的权限");
				return;
			}
			
			String menuIds = getString("menuIds");
			
			if (StringUtils.isNotBlank(menuIds)) {
				// 去除js错误选择导致的 undefined
				menuIds = menuIds.replaceAll("undefined,", "");
			}
			
			String actionIds = getString("actionIds");
			
			if (StringUtils.isNotBlank(actionIds)) {
				// 去除js错误选择导致的 undefined
				actionIds = actionIds.replaceAll("undefined,", "");
			}
			// 分配菜单权限，功能权限
			pmsMenuBiz.assignPermission(roleId, menuIds, actionIds);

			// String menuNameBuffer = theMenusIdsChangeNames(menuIds); // 查询菜单的

			// String actionNameBuffer = theActionIdsChangeNames(actionIds);
			getOutputMsg().put("STATE", "SUCCESS");
		} catch (Exception e) {
			log.error("分配权限出现错误!", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "分配权限出现错误。" + e.getMessage());
		}
		outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * @Description: 把权限的ID转换成NAME
	 * @param @param actionIds
	 * @param @return    
	 * @return String
	 */
	@SuppressWarnings("unused")
	private String theActionIdsChangeNames(String actionIds) {
		if (StringUtils.isEmpty(actionIds))
			return null;
		StringBuffer actionBuffer = new StringBuffer();
		int actionNum = actionIds.indexOf(",");
		if (actionNum <= 0) {
			PmsActionDTO action = pmsActionBiz.getById(Long.valueOf(actionIds));
			actionBuffer.append(action.getActionName());
		} else {
			String[] actionArray = actionIds.split(",");
			for (int i = 0; i < actionArray.length; i++) {
				PmsActionDTO action = pmsActionBiz.getById(Long.valueOf(actionArray[i]));
				if (i == actionArray.length - 1) {
					actionBuffer.append(action.getActionName());
				} else {
					actionBuffer.append(action.getActionName()).append(",");
				}
			}
		}
		return actionBuffer.toString();
	}

	/**
	 * @Description: 把菜单的ID转换成name
	 * @param @param menuIds
	 * @param @return    
	 * @return String
	 */
	@SuppressWarnings("unused")
	private String theMenusIdsChangeNames(String menuIds) {
		if (StringUtils.isEmpty(menuIds))
			return null;
		StringBuffer menuBuffer = new StringBuffer(); // 追加菜单的名称
		int menuNum = menuIds.indexOf(",");
		if (menuNum <= 0) {
			PmsMenuDTO menu = pmsMenuBiz.getById(Long.valueOf(menuIds));
			menuBuffer.append(menu.getName());
		} else {
			String[] menuArray = menuIds.split(",");
			for (int i = 0; i < menuArray.length; i++) {
				PmsMenuDTO menu = pmsMenuBiz.getById(Long.valueOf(menuArray[i]));
				if (i == menuArray.length - 1) {
					menuBuffer.append(menu.getName());
				} else {
					menuBuffer.append(menu.getName()).append(",");
				}
			}
		}
		return menuBuffer.toString();
	}
}
