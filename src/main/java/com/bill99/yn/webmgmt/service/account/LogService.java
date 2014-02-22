package com.bill99.yn.webmgmt.service.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

import com.bill99.yn.webmgmt.entity.Log;
import com.bill99.yn.webmgmt.entity.LogAction;
import com.bill99.yn.webmgmt.repository.LogDao;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional
public class LogService {

	private LogDao logDao;

	public Log get(Long id) {
		return logDao.findOne(id);
	}

	public List<String> get(LogAction logAction) {
		List<String> dates = (List<String>) logDao.findContentByLogAction(logAction);
//		for(int i = 0; i < dates.size(); i ++) {
//			dates.set(i, dates.get(i).trim());
//		}
		return dates;
	}

	public void save(Log entity) {
		logDao.save(entity);
	}

	public void delete(Long id) {
		logDao.delete(id);
	}

	public List<Log> findAll() {
		return (List<Log>) logDao.findAll();
	}

	public Page<Log> findAll(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Log> spec = buildSpecification(searchParams);

		return logDao.findAll(spec, pageRequest);
	}

	public Page<Log> findAllByLogAction(int pageNumber, int pageSize, String sortType, LogAction... logAction) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Log> spec = buildSpecificationByLogAction(logAction);

		return logDao.findAll(spec, pageRequest);
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

	private Specification<Log> buildSpecificationByLogAction(final LogAction... logAction) {
		return new Specification<Log>() {
			public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				for(LogAction action : logAction) {
					predicates.add(cb.equal(root.get("logAction"), action));
				}
				return cb.or(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Log> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Log> spec = DynamicSpecifications.bySearchFilter(filters.values(), Log.class);
		return spec;
	}

	@Autowired
	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}
}
