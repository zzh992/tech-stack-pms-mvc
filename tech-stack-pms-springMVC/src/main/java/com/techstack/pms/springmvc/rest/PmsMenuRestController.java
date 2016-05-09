package com.techstack.pms.springmvc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techstack.pms.biz.PmsMenuBiz;

@RestController
@RequestMapping(value = "/pmsMenu")
public class PmsMenuRestController {

	@Autowired
	private PmsMenuBiz pmsMenuBiz;
}
