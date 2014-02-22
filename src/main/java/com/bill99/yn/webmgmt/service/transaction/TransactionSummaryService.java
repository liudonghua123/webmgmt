/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.bill99.yn.webmgmt.service.transaction;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.bill99.yn.webmgmt.entity.Customer;
import com.bill99.yn.webmgmt.entity.TransactionSummary;
import com.bill99.yn.webmgmt.repository.CustomerDao;
import com.bill99.yn.webmgmt.repository.TransactionSummaryDao;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional
public class TransactionSummaryService {

	@Autowired
	private TransactionSummaryDao transactionSummaryDao;
	@Autowired
	private CustomerDao customerDao;

	public TransactionSummary findOne(Long id) {
		return transactionSummaryDao.findOne(id);
	}

	public void save(TransactionSummary entity) {
		transactionSummaryDao.save(entity);
	}

	public void save(Iterable<TransactionSummary> entities) {
		transactionSummaryDao.save(entities);
	}

	public void delete(Long id) {
		transactionSummaryDao.delete(id);
	}

	public Page<TransactionSummary> findAllByCustomerId(Long customer_id, Date startDateTime, Date endDateTime,
		PageRequest pageRequest) {
		String customerName = customerDao.findOne(customer_id).getName();
		return transactionSummaryDao.findByCustomerNameAndSummaryDateBetween(customerName, startDateTime, endDateTime, pageRequest);
	}

	public Page<TransactionSummary> findAllByUserId(Long userId, Date startDateTime,
		Date endDateTime, PageRequest pageRequest) {
		List<Customer> allCustomers = customerDao.findByUserId(userId);
		String[] allCustomerNames = new String[allCustomers.size()];
		for(int i = 0; i < allCustomerNames.length; i ++) {
			allCustomerNames[i] = allCustomers.get(i).getName();
		}
		return transactionSummaryDao.findByCustomerNameInAndSummaryDateBetween(allCustomerNames, startDateTime, endDateTime, pageRequest);
	}

	public List<TransactionSummary> findAll() {
		return (List<TransactionSummary>) transactionSummaryDao.findAll();
	}

	public Page<TransactionSummary> findAll(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<TransactionSummary> spec = buildSpecification(userId, searchParams);

		return transactionSummaryDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<TransactionSummary> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<TransactionSummary> spec = DynamicSpecifications.bySearchFilter(filters.values(), TransactionSummary.class);
		return spec;
	}

	@Autowired
	public void setTransactionSummaryDao(TransactionSummaryDao transactionSummaryDao) {
		this.transactionSummaryDao = transactionSummaryDao;
	}
}
