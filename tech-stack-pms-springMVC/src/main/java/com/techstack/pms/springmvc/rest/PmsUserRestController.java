package com.techstack.pms.springmvc.rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techstack.component.spring.mvc.MediaTypes;
import com.techstack.pms.biz.PmsRoleBiz;
import com.techstack.pms.biz.PmsUserBiz;
import com.techstack.pms.dao.dto.PmsUserDTO;

@RestController
@RequestMapping(value = "/pmsUser")
public class PmsUserRestController {
	
	//private static Logger logger = LoggerFactory.getLogger(PmsUserRestController.class);
	
	@Autowired
	private PmsRoleBiz pmsRoleBiz;
	@Autowired
	private PmsUserBiz pmsUserBiz;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> createPmsUser(PmsUserDTO pmsUser){
		return null;
	}
	
	@RequestMapping(value ="/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public PmsUserDTO getPmsUser(@PathVariable("id") Long userId){
		PmsUserDTO pmsUserDTO = pmsUserBiz.getById(userId);
		return pmsUserDTO;
	}
	
	
	@RequestMapping(value ="/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Page<PmsUserDTO> pmsUserPage(String loginName, Integer pageNumber, Integer pageSize){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", loginName); 
		Page<PmsUserDTO> pmsUserPage = pmsUserBiz.listPage(pageNumber, pageSize, paramMap);
		return pmsUserPage;
	}
}
