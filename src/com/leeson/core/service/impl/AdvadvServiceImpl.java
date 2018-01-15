package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Advadv;
import com.leeson.core.dao.AdvadvDao;
import com.leeson.core.query.AdvadvQuery;
import com.leeson.core.service.AdvadvService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdvadvServiceImpl implements AdvadvService {
	private static final Log log = LogFactory.getLog(AdvadvServiceImpl.class);

	@Resource
	AdvadvDao advadvDao;

	public Long addAdvadv(Advadv advadv) {
		try {
			return this.advadvDao.addAdvadv(advadv);
		} catch (SQLException e) {
			log.error("dao addAdvadv error.:" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	@Transactional(readOnly = true)
	public Advadv getAdvadvByKey(Long id) {
		try {
			return this.advadvDao.getAdvadvByKey(id);
		} catch (SQLException e) {
			log.error("dao getAdvadvbyKey error.:" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	@Transactional(readOnly = true)
	public List<Advadv> getAdvadvByKeys(List<Long> idList) {
		try {
			return this.advadvDao.getAdvadvByKeys(idList);
		} catch (SQLException e) {
			log.error("dao getAdvadvsByKeys erorr." + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	public Integer deleteByKey(Long id) {
		try {
			return this.advadvDao.deleteByKey(id);
		} catch (SQLException e) {
			log.error("dao deleteByKey error. :" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	public Integer deleteByQuery(AdvadvQuery advadvQuery) {
		try {
			return this.advadvDao.deleteByQuery(advadvQuery);
		} catch (SQLException e) {
			log.error("dao deleteByQuery error. :" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	public Integer deleteByKeys(List<Long> idList) {
		try {
			return this.advadvDao.deleteByKeys(idList);
		} catch (SQLException e) {
			log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	public Integer deleteAll() {
		try {
			return this.advadvDao.deleteAll();
		} catch (SQLException e) {
			log.error("dao deleteAll error. s:" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	public Integer updateAdvadvByKey(Advadv advadv) {
		try {
			return this.advadvDao.updateAdvadvByKey(advadv);
		} catch (SQLException e) {
			log.error("dao updateAdvadv error.Advadv:" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	public Integer updateAdvadvByKeyAll(Advadv advadv) {
		try {
			return this.advadvDao.updateAdvadvByKeyAll(advadv);
		} catch (SQLException e) {
			log.error("dao updateAdvadvAll error.Advadv:" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	@Transactional(readOnly = true)
	public Pagination getAdvadvListWithPage(AdvadvQuery advadvQuery) {
		try {
			Pagination page = new Pagination();
			page.setList(this.advadvDao.getAdvadvListWithPage(advadvQuery));

			page.setTotalCount(this.advadvDao.getAdvadvListCount(advadvQuery).intValue());
			page.setPageNo(advadvQuery.getPage());
			page.setPageSize(advadvQuery.getPageSize());
			return page;
		} catch (Exception e) {
			log.error("get Advadv pagination error." + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	@Transactional(readOnly = true)
	public List<Advadv> getAdvadvList(AdvadvQuery advadvQuery) {
		try {
			return this.advadvDao.getAdvadvList(advadvQuery);
		} catch (SQLException e) {
			log.error("get Advadv list error." + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	@Transactional(readOnly = true)
	public Integer getAdvadvCount(AdvadvQuery advadvQuery) {
		try {
			return this.advadvDao.getAdvadvListCount(advadvQuery);
		} catch (SQLException e) {
			log.error("get Advadv list Count." + e.getMessage(), e);
		}
		throw new RuntimeException();
	}
}

/*
 * Location:
 * C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name: com.leeson.core.service.impl.AdvadvServiceImpl JD-Core
 * Version: 0.6.2
 */