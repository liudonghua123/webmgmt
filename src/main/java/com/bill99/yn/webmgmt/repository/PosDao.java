package com.bill99.yn.webmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bill99.yn.webmgmt.entity.Pos;

public interface PosDao extends PagingAndSortingRepository<Pos, Long>, JpaSpecificationExecutor<Pos> {
	
	@Query(value="select pos.terminalNumber from Pos pos where pos.customer.id=?1")
	public List<String> findByCustomerId(Long customerId);

	@Query(value="select pos.terminalNumber from Pos pos where pos.customer.id in ( select customer.id from Customer customer where customer.user.id = ?1)")
	public List<String> findByUserId(Long userId);
}
