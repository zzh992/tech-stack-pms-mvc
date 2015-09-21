package com.techstack.pms.struts2.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.techstack.component.dwz.DwzUtils;
import com.techstack.component.struts2.Struts2BaseController;
import com.techstack.pms.biz.PmsActionBiz;
import com.techstack.pms.biz.PmsMenuBiz;
import com.techstack.pms.biz.PmsRoleBiz;
import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsMenuDTO;
import com.techstack.pms.dao.dto.PmsRoleDTO;
import com.techstack.pms.enums.NodeTypeEnum;

/**
 * @Title: PmsPermissionAction.java 
 * @Description: 权限管理Action类
 * @author zzh
 */
public class PmsPermissionController extends Struts2BaseController {

	private static final long serialVersionUID = 5588682213578275029L;

	private static Log log = LogFactory.getLog(PmsPermissionController.class);

	@Autowired
	private PmsActionBiz pmsActionBiz;
	@Autowired
	private PmsRoleBiz pmsRoleBiz;
	@Autowired
	private PmsMenuBiz pmsMenuBiz;

	/**
	 * @Description: 分页列出pms权限，也可根据权限获权限名称进行查询.
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:action:view")
	@RequiresPermissions("pms:action:view")
	public String pmsActionList() {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>(); // 业务条件查询参数
			paramMap.put("actionName", getString("actionName")); // 权限名称（模糊查询）
			paramMap.put("action", getString("act")); // 权限（精确查询）
			paramMap.put("act", getString("act"));
			paramMap.put("module", "pmsAction");
			Page<PmsActionDTO> pageBean = pmsActionBiz.listPage(DwzUtils.getPageNum(ServletActionContext.getRequest()), DwzUtils.getNumPerPage(ServletActionContext.getRequest()), paramMap);
			this.putData("pageImpl", pageBean);
			//this.pushData(pageBean);
			this.pushData(paramMap); // 回显查询条件值
			return "pmsActionList";
		} catch (Exception e) {
			log.error("==== error ==== 查询权限失败：", e);
			return DwzUtils.operateErrorInStruts2("获取数据失败");
		}
	}

	/**
	 * @Description: 进入添加Pms权限页面 
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:action:add")
	@RequiresPermissions("pms:action:add")
	public String pmsActionAdd() {
		return "pmsActionAdd";
	}

	/**
	 * @Description: 将权限信息保存到数据库中
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:action:add")
	@RequiresPermissions("pms:action:add")
	public String pmsActionSave() {
		try {
			String actionName = getString("actionName"); // 权限名称
			String action = getString("action"); // 权限标识
			String desc = getString("desc"); // 权限描述
			Long menuId = getLong("menu.id"); // 权限关联的菜单ID
			String menuName = getString("menu.name");	//权限关联的菜单名称
			// 权限
			PmsActionDTO act = new PmsActionDTO();
			act.setActionName(actionName);
			act.setAction(action);
			act.setRemark(desc);
			// 菜单
			PmsMenuDTO menu = new PmsMenuDTO();
			menu.setId(menuId);
			act.setMenuId(menuId); // 设置菜单ID
			act.setMenuName(menuName);

			// 表单数据校验
			String validateMsg = validatePmsAction(act);
			if (StringUtils.isNotBlank(validateMsg)) {
				return DwzUtils.operateErrorInStruts2(validateMsg); // 返回错误信息
			}
			// 检查权限名称是否已存在
			PmsActionDTO checkName = pmsActionBiz.getByActionName(actionName.trim());
			if (checkName != null) {
				return DwzUtils.operateErrorInStruts2("权限名称【" + actionName + "】已存在");
			}
			// 检查权限是否已存在
			PmsActionDTO checkAction = pmsActionBiz.getByAction(action.trim());
			if (checkAction != null) {
				return DwzUtils.operateErrorInStruts2("权限【" + action + "】已存在");
			}

			pmsActionBiz.saveAction(act);
			log.info("==== info ==== 权限【"+action+"】添加成功");
			return DwzUtils.operateSuccessInStruts2("操作成功"); // 返回operateSuccess视图,并提示“操作成功”
		} catch (Exception e) {
			log.error("==== error ==== 权限添加失败", e);
			return DwzUtils.operateErrorInStruts2("保存失败");
		}
	}

	/**
	 * @Description: 添加或修改权限时，查找带回权限要关联的菜单ID.
	 * @param @return    
	 * @return String
	 */
	public String pmsMenuLookUpUI() {
		List treeData = pmsMenuBiz.getMenuByPid(null);
		StringBuffer strJson = new StringBuffer();
		recursionTreeMenuLookUp(0L, strJson, treeData); //从一级菜单开始构建
		putData("tree", strJson.toString());
		return "pmsMenuLookUp";
	}
	
	/**
	 * @Description: 查找带回权限树
	 * @param @param pId
	 * @param @param buffer
	 * @param @param list    
	 * @return void
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void recursionTreeMenuLookUp(Long pId, StringBuffer buffer, List list) {
		if (pId == 0) {	//为一级菜单
			buffer.append("<ul class=\"tree treeFolder\" >");
		} else {
			buffer.append("<ul>");
		}
		List<PmsMenuDTO> sonMenuList = getSonMenuListByPid(pId, list);
		for (PmsMenuDTO sonMenu : sonMenuList) {
			Long id = sonMenu.getId();
			Long parentId = sonMenu.getParentId();
			String name = sonMenu.getName();
			Integer isLeaf = sonMenu.getIsLeaf();

			if (isLeaf == NodeTypeEnum.LEAF.getValue()) {	//若为叶子节点
				buffer.append("<li><a onclick=\"$.bringBack({id:'" + id + "', name:'" + name + "'})\"  href=\"javascript:\"  >" + name + "</a>");
			} else {
				buffer.append("<li><a id='" + id + "' pid='" + parentId + "' isleaf='" + isLeaf + "'>" + name + "</a>");
			}

			if (isLeaf != NodeTypeEnum.LEAF.getValue()) { //非叶子节点继续递归
				recursionTreeMenuLookUp(id, buffer, list);
			}
			buffer.append("</li>");
		}
		buffer.append("</ul>");
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
	 * @Description: 校验Pms权限信息
	 * @param @param pmsAction
	 * @param @return    
	 * @return String
	 */
	private String validatePmsAction(PmsActionDTO pmsAction) {
		String msg = ""; // 用于存放校验提示信息的变量
		String actionName = pmsAction.getActionName(); // 权限名称
		String action = pmsAction.getAction(); // 权限标识
		String desc = pmsAction.getRemark(); // 权限描述
		// 权限名称 actionName
		msg += DwzUtils.lengthValidate("权限名称", actionName, true, 3, 90);
		// 权限标识 action
		msg += DwzUtils.lengthValidate("权限标识", action, true, 3, 100);
		// 描述 desc
		msg += DwzUtils.lengthValidate("描述", desc, true, 3, 60);
		// 校验菜单ID是否存在
		if (null != pmsAction.getMenuId()) {
			PmsMenuDTO menu = pmsMenuBiz.getById(pmsAction.getMenuId());
			if (menu == null) {
				msg += "，请选择权限关联的菜单";
			}
		} else {
			msg += "，请选择权限关联的菜单";
		}
		return msg;
	}

	/**
	 * @Description: 转到权限修改页面 .
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:action:edit")
	@RequiresPermissions("pms:action:edit")
	public String pmsActionEdit() {
		try {
			Long id = getLong("id");
			PmsActionDTO pmsAction = pmsActionBiz.getById(id);
			this.putData("pmsAction", pmsAction);
			return "pmsActionEdit";
		} catch (Exception e) {
			log.error("==== error ==== 进入权限修改页面失败：", e);
			return DwzUtils.operateErrorInStruts2("获取数据失败");
		}
	}

	/**
	 * @Description: 保存修改后的权限信息
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:action:edit")
	@RequiresPermissions("pms:action:edit")
	public String pmsActionUpdate() {
		try {
			Long id = getLong("actionId");
			PmsActionDTO pmsAction = pmsActionBiz.getById(id);
			if (pmsAction == null) {
				return DwzUtils.operateErrorInStruts2("无法获取要修改的数据");
			} else {

				String actionName = getString("actionName");
				// String action = getString("action");
				String desc = getString("desc");

				pmsAction.setActionName(actionName);
				// pmsAction.setAction(action);
				pmsAction.setRemark(desc);

				// 表单数据校验
				String validateMsg = validatePmsAction(pmsAction);
				if (StringUtils.isNotBlank(validateMsg)) {
					return DwzUtils.operateErrorInStruts2(validateMsg); // 返回错误信息
				}

				// 检查权限名称是否已存在
				PmsActionDTO checkName = pmsActionBiz.getByActionNameNotEqId(actionName, id);
				if (checkName != null) {
					return DwzUtils.operateErrorInStruts2("权限名称【" + actionName + "】已存在");
				}
				// 检查权限是否已存在
				// PmsAction checkAction =
				// pmsActionBiz.getByActionNotEqId(action, id);
				// if (checkAction != null){
				// return operateError("权限【"+action+"】已存在");
				// }

				pmsActionBiz.updateAction(pmsAction);
				log.info("==== info ==== 权限【"+actionName+"】修改成功");
				return DwzUtils.operateSuccessInStruts2("操作成功");
			}
		} catch (Exception e) {
			log.error("==== error ==== 权限修改失败", e);
			return DwzUtils.operateErrorInStruts2("修改失败");
		}
	}

	/**
	 * @Description: 删除一条权限记录
	 * @param @return    
	 * @return String
	 */
	//@Permission("pms:action:delete")
	@RequiresPermissions("pms:action:delete")
	public String pmsActionDel() {
		try {
			Long actionId = getLong("id");
			PmsActionDTO act = pmsActionBiz.getById(actionId);
			if (act == null) {
				return DwzUtils.operateErrorInStruts2("无法获取要删除的数据");
			}
			// 判断此权限是否关联有角色，要先解除与角色的关联后才能删除该权限
			List<PmsRoleDTO> roleList = pmsRoleBiz.listByActionId(actionId);
			if (roleList != null && !roleList.isEmpty()) {
				return DwzUtils.operateErrorInStruts2("权限【" + act.getAction() + "】关联了【" + roleList.size() + "】个角色，要解除所有关联后才能删除。其中一个角色名为:" + roleList.get(0).getRoleName());
			}
			pmsActionBiz.deleteActionById(actionId);
			log.info("==== info ==== 删除权限【"+act.getAction()+"】成功");
			return DwzUtils.operateSuccessInStruts2("操作成功"); // 返回operateSuccess视图,并提示“操作成功”
		} catch (Exception e) {
			log.error("==== error ==== 删除权限失败", e);
			return DwzUtils.operateErrorInStruts2("删除限权异常");
		}
	}

}
