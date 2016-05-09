package com.techstack.pms.springmvc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techstack.pms.biz.PmsActionBiz;

@RestController
@RequestMapping(value = "/pmsAction")
public class PmsActionRestController {
	
	@Autowired
	private PmsActionBiz pmsActionBiz;
}
