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
public class TransactionDetailDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private TransactionDetailDao transactionDetailDao;

	@Test
	public void testGetByLogAction() throws Exception {
//		Date startDate = Utils.parseDateTime("2014-02-19 23:57:00");
//		Date endDate = Utils.parseDateTime("2014-02-19 23:57:10");
		Date startDate = Utils.parseDateTime("2014-02-17 23:57:00");
		Date endDate = Utils.parseDateTime("2014-02-18 23:57:10");
		String[] terminalNumbers = new String[2];
		terminalNumbers[0] = "33037855";
		terminalNumbers[1] = "23021438";
//		Iterable<TransactionDetail> transactionDetails = transactionDetailDao.findByTransactionDatetimeBetween(startDate, endDate);
		String terminalNumberString = "'33037855','23021438'";
		List<String> terminalNumberList = new ArrayList<String>();
		terminalNumberList.add("33037855");
		terminalNumberList.add("23021438");
		PageRequest page = new PageRequest(0, 10);
		Page<TransactionDetail> transactionDetails = transactionDetailDao.findByTransactionDatetimeAndTerminalNumber(startDate, endDate, terminalNumberList, page);
		for(TransactionDetail transactionDetail : transactionDetails.getContent()) {
			System.out.println(transactionDetail);
		}
	}
}
