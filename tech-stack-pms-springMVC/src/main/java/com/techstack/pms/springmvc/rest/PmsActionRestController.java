package com.techstack.pms.springmvc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techstack.component.spring.mvc.MediaTypes;
import com.techstack.pms.biz.PmsActionBiz;
import com.techstack.pms.dao.dto.PmsActionDTO;

@RestController
@RequestMapping(value = "/pmsAction")
public class PmsActionRestController {
	
	@Autowired
	private PmsActionBiz pmsActionBiz;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
	public ResponseEntity<?> insertPmsMenu(PmsActionDTO pmsActionDTO){
		pmsActionBiz.saveAction(pmsActionDTO);
		return new ResponseEntity(pmsActionDTO, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, consumes = MediaTypes.JSON)
	public void deletePmsMenu(PmsActionDTO pmsActionDTO){
		pmsActionBiz.deleteActionById(pmsActionDTO.getId());
	}
	
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaTypes.JSON)
	public ResponseEntity<?> updatePmsMenu(PmsActionDTO pmsActionDTO){
		pmsActionBiz.updateAction(pmsActionDTO);
		return new ResponseEntity(pmsActionDTO, HttpStatus.OK);
	}
	
}
