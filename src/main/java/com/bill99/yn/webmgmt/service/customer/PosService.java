package com.bill99.yn.webmgmt.service.customer;

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

import com.bill99.yn.webmgmt.entity.Pos;
import com.bill99.yn.webmgmt.repository.PosDao;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional
public class PosService {

	private PosDao posDao;

	public Pos get(Long id) {
		return posDao.findOne(id);
	}

	public void save(Pos entity) {
		posDao.save(entity);
	}

	public void delete(Long id) {
		posDao.delete(id);
	}

	public List<Pos> findAll() {
		return (List<Pos>) posDao.findAll();
	}

	public Page<Pos> findAll(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Pos> spec = buildSpecification(userId, searchParams);

		return posDao.findAll(spec, pageRequest);
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
	private Specification<Pos> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("customer.id", new SearchFilter("customer.id", Operator.EQ, userId));
		Specification<Pos> spec = DynamicSpecifications.bySearchFilter(filters.values(), Pos.class);
		return spec;
	}

	@Autowired
	public void setPosDao(PosDao posDao) {
		this.posDao = posDao;
	}
}
