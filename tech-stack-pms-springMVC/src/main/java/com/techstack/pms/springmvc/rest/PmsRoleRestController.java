package com.techstack.pms.springmvc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techstack.pms.biz.PmsRoleBiz;

@RestController
@RequestMapping(value = "/pmsRole")
public class PmsRoleRestController {

	@Autowired
	private PmsRoleBiz pmsRoleBiz;
}
