package com.bill99.yn.webmgmt.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bill99.yn.webmgmt.entity.TransactionSummary;

public interface TransactionSummaryDao extends PagingAndSortingRepository<TransactionSummary, Long>, JpaSpecificationExecutor<TransactionSummary> {

	Page<TransactionSummary> findByCustomerNameAndSummaryDateBetween(String customerName,
			Date startDateTime, Date endDateTime, Pageable pageRequest);

	Page<TransactionSummary> findByCustomerNameInAndSummaryDateBetween(String[] allCustomerNames,
			Date startDateTime, Date endDateTime, Pageable pageRequest);

}
