package com.bill99.yn.webmgmt.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bill99.yn.webmgmt.entity.TransactionDetail;

public interface TransactionDetailDao extends PagingAndSortingRepository<TransactionDetail, Long>, JpaSpecificationExecutor<TransactionDetail> {

	public Iterable<TransactionDetail> findByTransactionDatetimeBetween(Date startDate, Date endDate);
	
	public Iterable<TransactionDetail> findByTransactionDatetimeBetweenAndTerminalNumberIn(Date startDate, Date endDate, String[] terminalNumbers);
	
	public Page<TransactionDetail> findByTransactionDatetimeBetweenAndTerminalNumberIn(Date startDate, Date endDate, String[] terminalNumbers, Pageable page);
	
	// same as findByTransactionDatetimeBetweenAndTerminalNumberIn
	@Query(value = "select * from ss_transaction_detail ts where transaction_datetime between ?1 and ?2 and terminal_number in(?3)", nativeQuery = true)
	//@Query(value = "select detail from TransactionDetail detail where detail.transactionDatetime between ?1 and ?2 and terminal_number in(?3)", nativeQuery = false)
	public Iterable<TransactionDetail> findByTransactionDatetimeAndTerminalNumber(Date startDate, Date endDate, List<String> terminalNumberList);
	
	@Query(value = "select detail from TransactionDetail detail where detail.transactionDatetime between ?1 and ?2 and terminal_number in(?3)")
	public Page<TransactionDetail> findByTransactionDatetimeAndTerminalNumber(Date startDate, Date endDate, List<String> terminalNumberList, Pageable page);
}
