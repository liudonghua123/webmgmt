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

import com.bill99.yn.webmgmt.entity.TransactionDetail;
import com.bill99.yn.webmgmt.repository.PosDao;
import com.bill99.yn.webmgmt.repository.TransactionDetailDao;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional
public class TransactionDetailService {

	@Autowired
	private TransactionDetailDao transactionDetailDao;
	@Autowired
	private PosDao posDao;

	public TransactionDetail findOne(Long id) {
		return transactionDetailDao.findOne(id);
	}

	public void save(TransactionDetail entity) {
		transactionDetailDao.save(entity);
	}

	public void save(Iterable<TransactionDetail> entities) {
		transactionDetailDao.save(entities);
	}

	public void delete(Long id) {
		transactionDetailDao.delete(id);
	}

	public List<TransactionDetail> findAll() {
		return (List<TransactionDetail>) transactionDetailDao.findAll();
	}

	public Page<TransactionDetail> findAllByUserId(Long user_id, Date startDateTime, Date endDateTime,
			PageRequest pageRequest) {
		// 找到所有客户的终端号
		List<String> terminalNumberList = posDao.findByUserId(user_id);
		// 查找所有属于这些终端号及指定时间范围内的交易详细记录
		return transactionDetailDao.findByTransactionDatetimeAndTerminalNumber(startDateTime, endDateTime, terminalNumberList, pageRequest);
	}

	public Page<TransactionDetail> findAllByCustomerId(Long customer_id, Date startDateTime, Date endDateTime,
			PageRequest pageRequest) {
		// 找到所有客户的终端号
		List<String> terminalNumberList = posDao.findByCustomerId(customer_id);
		// 查找所有属于这些终端号及指定时间范围内的交易详细记录
		return transactionDetailDao.findByTransactionDatetimeAndTerminalNumber(startDateTime, endDateTime, terminalNumberList, pageRequest);
	}

	public List<TransactionDetail> findByTransactionDatetime(Date startDate, Date endDate) {
		return (List<TransactionDetail>) transactionDetailDao.findByTransactionDatetimeBetween(startDate, endDate);
	}

	public List<TransactionDetail> findByTransactionDatetimeAndTerminalNumber(Date startDate, Date endDate, List<String> terminalNumberList) {
		return (List<TransactionDetail>) transactionDetailDao.findByTransactionDatetimeAndTerminalNumber(startDate, endDate, terminalNumberList);
	}

	public Page<TransactionDetail> findAll(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<TransactionDetail> spec = buildSpecification(userId, searchParams);

		return transactionDetailDao.findAll(spec, pageRequest);
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
	private Specification<TransactionDetail> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<TransactionDetail> spec = DynamicSpecifications.bySearchFilter(filters.values(), TransactionDetail.class);
		return spec;
	}

	@Autowired
	public void setTransactionDetailDao(TransactionDetailDao transactionDetailDao) {
		this.transactionDetailDao = transactionDetailDao;
	}
}
