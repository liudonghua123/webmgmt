/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bill99.yn.webmgmt.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;

import com.bill99.yn.webmgmt.entity.LogAction;
import com.bill99.yn.webmgmt.entity.Task;
import com.bill99.yn.webmgmt.entity.TransactionDetail;
import com.bill99.yn.webmgmt.entity.TransactionSummary;
import com.bill99.yn.webmgmt.util.ExcelUtil;

import org.springside.modules.test.spring.SpringTransactionalTestCase;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class LogDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private LogDao logDao;

	@Test
	public void testGetByLogAction() throws Exception {
		List<String> storedDateString = (List<String>) logDao.findContentByLogAction(LogAction.STORE_DATA);
		for(String date : storedDateString) {
			System.out.println(date);
		}
		ExcelUtil excelUtil = new ExcelUtil("C:/Users/Liu.D.H/Desktop/org.0.0000109155/20140221001819.xls", storedDateString);
		List<TransactionDetail> transactionDetails = excelUtil.getTransactionDetails();
		List<TransactionSummary> transactionSummarys = excelUtil.getTransactionSummarys();
	}
}
