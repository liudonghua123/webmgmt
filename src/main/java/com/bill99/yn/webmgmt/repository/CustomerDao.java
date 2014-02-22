package com.bill99.yn.webmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bill99.yn.webmgmt.entity.Customer;

public interface CustomerDao extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

	List<Customer> findByUserId(Long userId);

}
