package com.techstack.pms.springmvc.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techstack.component.spring.mvc.MediaTypes;
import com.techstack.pms.biz.PmsMenuBiz;
import com.techstack.pms.dao.dto.PmsMenuDTO;

@RestController
@RequestMapping(value = "/pmsMenu")
public class PmsMenuRestController {

	@Autowired
	private PmsMenuBiz pmsMenuBiz;
	
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> insertPmsMenu(PmsMenuDTO pmsMenuDTO){
		pmsMenuBiz.createMenu(pmsMenuDTO);
		return new ResponseEntity(pmsMenuDTO, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, consumes = MediaTypes.JSON)
	public void deletePmsMenu(PmsMenuDTO pmsMenuDTO){
		pmsMenuBiz.delete(pmsMenuDTO.getId());
	}
	
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaTypes.JSON)
	public ResponseEntity<?> updatePmsMenu(PmsMenuDTO pmsMenuDTO){
		pmsMenuBiz.update(pmsMenuDTO);
		return new ResponseEntity(pmsMenuDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value ="/tree", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<?> getMenuTree (){
		List<PmsMenuDTO> pmsMenuDTOList= pmsMenuBiz.getAllMenu();
		return new ResponseEntity(pmsMenuDTOList, HttpStatus.OK);
	}
}
