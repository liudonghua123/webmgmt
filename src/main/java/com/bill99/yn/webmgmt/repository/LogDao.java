package com.bill99.yn.webmgmt.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bill99.yn.webmgmt.entity.Log;
import com.bill99.yn.webmgmt.entity.LogAction;

public interface LogDao extends PagingAndSortingRepository<Log, Long>, JpaSpecificationExecutor<Log> {

	@Query(value = "select log.content from Log log where log.logAction = ?1")
	// none-nativeQuery will produce query like select log0_.content as col_0_0_ from ss_log log0_ where log0_.log_action='STORE_DATA'
	//@Query(value = "select content from ss_log where log_action = ?1", nativeQuery = true)
	// nativeQuery will produce query like select content from ss_log where log_action = x'ACED00057E720026636F6D2E62696C6C39392E796E2E7765626D676D742E656E746974792E4C6F67416374696F6E00000000000000001200007872000E6A6176612E6C616E672E456E756D0000000000000000120000787074000A53544F52455F44415441'
	// which is something wrong
	Iterable<String> findContentByLogAction(LogAction logAction);

}
