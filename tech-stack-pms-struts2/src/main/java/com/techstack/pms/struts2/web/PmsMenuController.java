package com.techstack.pms.struts2.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import com.techstack.component.dwz.DwzUtils;
import com.techstack.component.struts2.Struts2BaseController;
import com.techstack.pms.biz.PmsActionBiz;
import com.techstack.pms.biz.PmsMenuBiz;
import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsMenuDTO;
import com.techstack.pms.enums.NodeTypeEnum;

/**
 * @Title: PmsMenuAction.java 
 * @Description: 菜单ACTION
 * @author zzh
 */
public class PmsMenuController extends Struts2BaseController {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(PmsMenuController.class);
	
	/**	菜单的编辑URL	*/
	private static final String EDIT_MENU_ACTION = "pmsMenu_pmsMenuEdit.action";	

	//private PmsMenu pmsMenu = new PmsMenu();

	/*@Override
	public PmsMenu getModel() {
		return pmsMenu;
	}*/

	@Autowired
	private PmsMenuBiz pmsMenuBiz;

	@Autowired
	private PmsActionBiz pmsActionBiz;

	/**
	 * 列出要管理的菜单.
	 * @return PmsMenuList .
	 */
	//@Permission("pms:menu:view")
	@RequiresPermissions("pms:menu:view")
	public String pmsMenuList() {
		StringBuffer strJson = new StringBuffer();
		List treeData = pmsMenuBiz.getMenuByPid(null);
		recursionTreeMenu(0L, strJson, treeData, EDIT_MENU_ACTION); //从一级菜单开始递归
		//String str = pmsMenuBiz.getTreeMenu(EDIT_MENU_ACTION);//构建树形菜单的HTML
		this.putData("tree", strJson.toString());
		return "pmsMenuList";
	}
	
	/**
	 * @Description: 递归输出树形菜单
	 * @param @param pId (父节点ID，若为0则表示一级菜单)
	 * @param @param buffer
	 * @param @param list
	 * @param @param url    
	 * @return void
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void recursionTreeMenu(Long pId, StringBuffer buffer, List list, String url) {
		if (pId == 0) {	//若为一级菜单
			buffer.append("<ul class=\"tree treeFolder collapse \" >");
		} else {
			buffer.append("<ul>");
		}
		List<PmsMenuDTO> listMenu = getSonMenuListByPid(pId, list);
		if(listMenu != null && !listMenu.isEmpty()){
			for (PmsMenuDTO menu : listMenu) {
				Long id = menu.getId();
				String name = menu.getName();
				Integer isLeaf = menu.getIsLeaf();
				buffer.append("<li><a onclick=\"onClickMenuNode(" + id + ")\"  href=\"" + url + "?id=" + id + "\" target=\"ajax\" rel=\"jbsxBox\"  value=" + id + ">" + name + "</a>");
				if (!NodeTypeEnum.LEAF.getValue().equals(isLeaf)) {	//非叶子节点继续递归
					recursionTreeMenu(id, buffer, list, url);
				}
				buffer.append("</li>");
			}
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
	 * 进入新菜单添加页面.
	 * @return PmsMenuAdd .
	 */
	//@Permission("pms:menu:add")
	@RequiresPermissions("pms:menu:add")
	public String pmsMenuAdd() {
		Long pid = getLong("pid");
		if (null != pid) {
			PmsMenuDTO parentMenu = pmsMenuBiz.getById(pid);
			this.putData("parentMenu", parentMenu);
		}
		return "pmsMenuAdd";
	}

	/**
	 * 保存新增菜单.
	 * @return operateSuccess or operateError .
	 */
	//@Permission("pms:menu:add")
	@RequiresPermissions("pms:menu:add")
	public String pmsMenuSave() {
		try {
			String name = getString("name");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isLeaf", NodeTypeEnum.LEAF.getValue());
			map.put("name", name);
			List<PmsMenuDTO> list = pmsMenuBiz.getMenuByNameAndIsLeaf(map);
			if (list.size() > 0) {
				//return operateError("同级菜单名称不能重复");
				return DwzUtils.operateErrorInStruts2("同级菜单名称不能重复");
			}
			PmsMenuDTO pmsMenu = new PmsMenuDTO();
			pmsMenu.setName(name);
			pmsMenu.setNumber(getString("number"));
			pmsMenu.setUrl(getString("url"));
			pmsMenu.setTargetName(getString("targetName"));
			if(getLong("parentId") == null){
				pmsMenu.setParentId(0L);
			}else{
				pmsMenu.setParentId(getLong("parentId"));
			}
			pmsMenuBiz.createMenu(pmsMenu);
			log.info("==== info ==== 添加菜单【"+pmsMenu.getName()+"】成功");
		} catch (Exception e) {
			log.error("==== error ==== 添加菜单出错", e);
			//return operateError("添加菜单出错");
			return DwzUtils.operateErrorInStruts2("添加菜单出错");
		}
		return DwzUtils.operateSuccessInStruts2("操作成功");
	}

	/**
	 * 进入菜单修改页面.
	 * @return
	 */
//	@Permission("pms:menu:view")
	@RequiresPermissions("pms:menu:view")
	public String pmsMenuEdit() {
		Long id = getLong("id");
		if (null != id) {
			PmsMenuDTO currentMenu = pmsMenuBiz.getById(id);
			PmsMenuDTO parentMenu = pmsMenuBiz.getById(currentMenu.getParentId());
			this.putData("currentMenu", currentMenu);
			this.putData("parentMenu", parentMenu);
		}
		return "pmsMenuEdit";
	}

	/**
	 * 保存要修改的菜单.
	 * @return
	 */
	//@Permission("pms:menu:edit")
	@RequiresPermissions("pms:menu:edit")
	public String pmsMenuUpdate() {
		try {
			Long id = getLong("menuId");
			PmsMenuDTO pmsMenu = pmsMenuBiz.getById(id);
			pmsMenu.setName(getString("name"));
			pmsMenu.setNumber(getString("number"));
			pmsMenu.setUrl(getString("url"));
			pmsMenu.setTargetName(getString("targetName"));
			pmsMenuBiz.update(pmsMenu);
			log.info("==== info ==== 修改菜单【"+pmsMenu.getName()+"】成功");
			//return operateSuccess();
			return DwzUtils.operateSuccessInStruts2("操作成功");
		} catch (Exception e) {
			log.error("==== error ==== 修改菜单出错", e);
			return DwzUtils.operateErrorInStruts2("保存菜单出错");
		}

	}

	/**
	 * 删除菜单.
	 * @return
	 */
	//@Permission("pms:menu:delete")
	@RequiresPermissions("pms:menu:delete")
	public String pmsMenuDel() {
		try {
			Long menuId = getLong("id");
			if (menuId == null || menuId.longValue() == 0) {
				return DwzUtils.operateErrorInStruts2("无法获取要删除的数据");
			}
			PmsMenuDTO menu = pmsMenuBiz.getById(menuId);
			if (menu == null) {
				return DwzUtils.operateErrorInStruts2("无法获取要删除的数据");
			}
			Long parentId = menu.getParentId(); // 获取父菜单ID

			// 先判断此菜单下是否有子菜单
			List<PmsMenuDTO> childMenuList = pmsMenuBiz.listByParentId(menuId);
			if (childMenuList != null && !childMenuList.isEmpty()) {
				return DwzUtils.operateErrorInStruts2("此菜单下关联有【" + childMenuList.size() + "】个子菜单，不能支接删除!");
			}

			// 判断是否有权限关联到此菜单上，如有则不能删除
			List<PmsActionDTO> actionList = pmsActionBiz.listByMenuId(menuId);
			if (actionList != null && !actionList.isEmpty()) {
				return DwzUtils.operateErrorInStruts2("此菜单下关联有【" + actionList.size() + "】个权限，要先解除关联后才能删除此菜单!");
			}

			// 删除掉菜单
			pmsMenuBiz.delete(menuId);

			// 删除菜单后，要判断其父菜单是否还有子菜单，如果没有子菜单了就要装其父菜单设为叶子节点
			List<PmsMenuDTO> childList = pmsMenuBiz.listByParentId(parentId);
			if (childList == null || childList.isEmpty()) {
				// 此时要将父菜单设为叶子
				PmsMenuDTO parent = pmsMenuBiz.getById(parentId);
				if(parent != null){
					parent.setIsLeaf(NodeTypeEnum.LEAF.getValue());
					pmsMenuBiz.update(parent);
				}
			}
			log.info("==== info ==== 删除菜单【"+menu.getName()+"】成功");
			return DwzUtils.operateSuccessInStruts2("操作成功");
		} catch (Exception e) {
			log.error("==== error ==== 删除菜单出错", e);
			return DwzUtils.operateErrorInStruts2("删除菜单出错");
		}
	}

}
