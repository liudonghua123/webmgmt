/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bill99.yn.webmgmt.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import com.bill99.yn.webmgmt.entity.TransactionDetail;
import com.bill99.yn.webmgmt.util.Utils;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class PosDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private PosDao posDao;

	@Test
	public void testFindByUserId() throws Exception {
		List<String> terminalNumbers = posDao.findByUserId(2l);
		for(String terminalNumber : terminalNumbers) {
			System.out.println(terminalNumber);
		}
	}

	@Test
	public void testFindByCustomerId() throws Exception {
		List<String> terminalNumbers = posDao.findByCustomerId(3l);
		for(String terminalNumber : terminalNumbers) {
			System.out.println(terminalNumber);
		}
	}
}
