package com.techstack.pms.springmvc.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techstack.component.spring.mvc.MediaTypes;
import com.techstack.pms.biz.PmsRoleBiz;
import com.techstack.pms.dao.dto.PmsRoleDTO;

@RestController
@RequestMapping(value = "/pmsRole")
public class PmsRoleRestController {

	@Autowired
	private PmsRoleBiz pmsRoleBiz;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> createPmsRole(PmsRoleDTO pmsRole){
		pmsRoleBiz.saveRole(pmsRole);
		return new ResponseEntity(pmsRole, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, consumes = MediaTypes.JSON)
	public ResponseEntity<?> deletePmsRole(Long roleId){
		pmsRoleBiz.deleteRoleById(roleId);
		return new ResponseEntity(null, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaTypes.JSON)
	public ResponseEntity<?> updatePmsRole(PmsRoleDTO pmsRole){
		pmsRoleBiz.updateRole(pmsRole);
		return new ResponseEntity(pmsRole, HttpStatus.OK);
	}
	
	@RequestMapping(value ="/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<?> getPmsRole(@PathVariable("id") Long roleId){
		PmsRoleDTO pmsRoleDTO = pmsRoleBiz.getById(roleId);
		return new ResponseEntity(pmsRoleDTO, HttpStatus.OK);
	}
	
	
	@RequestMapping(value ="/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public Page<PmsRoleDTO> pmsRolePage(String loginName, Integer pageNumber, Integer pageSize){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", loginName); 
		Page<PmsRoleDTO> pmsRolePage = pmsRoleBiz.listPage(pageNumber, pageSize, paramMap);
		return pmsRolePage;
	}
}
