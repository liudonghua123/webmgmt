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
import com.bill99.yn.webmgmt.entity.TransactionSummary;
import com.bill99.yn.webmgmt.util.Utils;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TransactionSummaryDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private TransactionDetailDao transactionDetailDao;
	@Autowired
	private TransactionSummaryDao transactionSummaryDao;

	@Test
	public void testFindAllByCustomerId() throws Exception {
		Date startDateTime = Utils.parseDateTime("2014-02-17 23:57:00");
		Date endDateTime = Utils.parseDateTime("2014-02-18 23:57:10");
		PageRequest pageRequest = new PageRequest(0, 10);
		Page<TransactionSummary> transactionSummarys = transactionSummaryDao.findByCustomerNameAndSummaryDateBetween("昆明市官渡区禄丰便利店", startDateTime, endDateTime, pageRequest);
		for(TransactionSummary t : transactionSummarys.getContent()) {
			System.out.println(t);
		}
	}

	@Test
	public void testFindAllByUserId() throws Exception {
		Date startDateTime = Utils.parseDateTime("2014-02-17 23:57:00");
		Date endDateTime = Utils.parseDateTime("2014-02-18 23:57:10");
		PageRequest pageRequest = new PageRequest(0, 10);
		String[] allCustomerNames = new String[2];
		allCustomerNames[0] = "昆明市官渡区禄丰便利店";
		allCustomerNames[1] = "昆明市五华区世界水汇副食品经营部";
		Page<TransactionSummary> transactionSummarys = transactionSummaryDao.findByCustomerNameInAndSummaryDateBetween(allCustomerNames, startDateTime, endDateTime, pageRequest);
		for(TransactionSummary t : transactionSummarys.getContent()) {
			System.out.println(t);
		}
		
	}
}
