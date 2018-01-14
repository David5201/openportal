package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.common.utils.stringUtils;
import com.leeson.core.bean.Portalprivilege;
import com.leeson.core.dao.PortalprivilegeDao;
import com.leeson.core.dao.PortalroleprivilegeDao;
import com.leeson.core.query.PortalprivilegeQuery;
import com.leeson.core.service.PortalprivilegeService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("portalprivilegeServiceImpl")
@Transactional
public class PortalprivilegeServiceImpl implements PortalprivilegeService {
	private static final Log log = LogFactory.getLog(PortalprivilegeServiceImpl.class);

	@Resource
	PortalprivilegeDao portalprivilegeDao;

	@Resource
	PortalroleprivilegeDao portalroleprivilegeDao;

	public Long addPortalprivilege(Portalprivilege portalprivilege) {
		try {
			return this.portalprivilegeDao.addPortalprivilege(portalprivilege);
		} catch (SQLException e) {
			log.error("dao addPortalprivilege error.:" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	@Transactional(readOnly = true)
	public Portalprivilege getPortalprivilegeByKey(Long id) {
		try {
			return this.portalprivilegeDao.getPortalprivilegeByKey(id);
		} catch (SQLException e) {
			log.error("dao getPortalprivilegebyKey error.:" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	@Transactional(readOnly = true)
	public List<Portalprivilege> getPortalprivilegeByKeys(List<Long> idList) {
		try {
			return this.portalprivilegeDao.getPortalprivilegeByKeys(idList);
		} catch (SQLException e) {
			log.error("dao getPortalprivilegesByKeys erorr." + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	public Integer deleteByKey(Long id) {
		try {
			List ids = new ArrayList();
			ids.add(id);
			WalkDelete(ids);
			return this.portalprivilegeDao.deleteByKey(id);
		} catch (SQLException e) {
			log.error("dao deleteByKey error. :" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	public Integer deleteByQuery(PortalprivilegeQuery portalprivilege) {
		try {
			List ids = new ArrayList();
			ids.add(portalprivilege.getId());
			WalkDelete(ids);
			return this.portalprivilegeDao.deleteByQuery(portalprivilege);
		} catch (SQLException e) {
			log.error("dao deleteByQuery error. :" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	public Integer deleteByKeys(List<Long> idList) {
		try {
			WalkDelete(idList);
			return this.portalprivilegeDao.deleteByKeys(idList);
		} catch (SQLException e) {
			log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	public Integer updatePortalprivilegeByKey(Portalprivilege portalprivilege) {
		try {
			return this.portalprivilegeDao.updatePortalprivilegeByKey(portalprivilege);
		} catch (SQLException e) {
			log.error("dao updatePortalprivilege error.Portalprivilege:" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	public Integer updatePortalprivilegeByKeyAll(Portalprivilege portalprivilege) {
		try {
			return this.portalprivilegeDao.updatePortalprivilegeByKeyAll(portalprivilege);
		} catch (SQLException e) {
			log.error("dao updatePortalprivilegeAll error.Portalprivilege:" + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	@Transactional(readOnly = true)
	public Pagination getPortalprivilegeListWithPage(PortalprivilegeQuery portalprivilegeQuery) {
		try {
			Pagination page = new Pagination();
			page.setList(this.portalprivilegeDao.getPortalprivilegeListWithPage(portalprivilegeQuery));

			page.setTotalCount(this.portalprivilegeDao.getPortalprivilegeListCount(portalprivilegeQuery).intValue());
			page.setPageNo(portalprivilegeQuery.getPage());
			page.setPageSize(portalprivilegeQuery.getPageSize());
			return page;
		} catch (Exception e) {
			log.error("get Portalprivilege pagination error." + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	@Transactional(readOnly = true)
	public List<Portalprivilege> getPortalprivilegeList(PortalprivilegeQuery portalprivilegeQuery) {
		try {
			return this.portalprivilegeDao.getPortalprivilegeList(portalprivilegeQuery);
		} catch (SQLException e) {
			log.error("get Portalprivilege list error." + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	@Transactional(readOnly = true)
	public Integer getPortalprivilegeCount(PortalprivilegeQuery portalprivilegeQuery) {
		try {
			return this.portalprivilegeDao.getPortalprivilegeListCount(portalprivilegeQuery);
		} catch (SQLException e) {
			log.error("get Portalprivilege list Count." + e.getMessage(), e);
		}
		throw new RuntimeException();
	}

	@Transactional(readOnly = true)
	public List<Portalprivilege> findTopList() {
		List<Portalprivilege> allprivileges = new ArrayList();
		PortalprivilegeQuery portalprivilegeQuery = new PortalprivilegeQuery();
		portalprivilegeQuery.orderbyPosition(true);
		try {
			allprivileges = this.portalprivilegeDao.getPortalprivilegeList(portalprivilegeQuery);
		} catch (SQLException e) {
			log.error("get Portalprivilege findTopList error." + e.getMessage(), e);
			return null;
		}
		List topList = new ArrayList();
		for (Portalprivilege privilege : allprivileges) {
			if ((privilege.getUrl() != null) && (stringUtils.isBlank(privilege.getUrl()))) {
				privilege.setUrl(null);
			}
			if (privilege.getParentId() == null) {
				topList.add(privilege);
			}
		}
		return topList;
	}

	@Transactional(readOnly = true)
	public Collection<String> getAllPrivilegeUrls() {
		List<Portalprivilege> privileges = new ArrayList<Portalprivilege>();
		try {
			privileges = this.portalprivilegeDao.getPortalprivilegeList(new PortalprivilegeQuery());
		} catch (SQLException e) {
			log.error("get Portalprivilege getAllPrivilegeUrls error." + e.getMessage(), e);
			return null;
		}
		Collection allPriviUrls = new HashSet(privileges.size());
		for (Portalprivilege privilege : privileges) {
			if (stringUtils.isNotBlank(privilege.getUrl())) {
				allPriviUrls.add(privilege.getUrl());
			}
		}
		return allPriviUrls;
	}

	@Transactional(readOnly = true)
	public List<Portalprivilege> findChildrenList(Long parentId) {
		PortalprivilegeQuery portalprivilegeQuery = new PortalprivilegeQuery();
		portalprivilegeQuery.setParentId(parentId);
		portalprivilegeQuery.orderbyPosition(true);
		try {
			return this.portalprivilegeDao.getPortalprivilegeList(portalprivilegeQuery);
		} catch (SQLException e) {
			log.error("get Portalprivilege findChildrenList error." + e.getMessage(), e);
		}
		return null;
	}

	private void WalkDelete(List<Long> list) throws SQLException {
		for (Long id : list) {
			PortalprivilegeQuery pq = new PortalprivilegeQuery();
			pq.setParentId(id);
			List<Portalprivilege> ps = getPortalprivilegeList(pq);
			if (ps.size() > 0) {
				List sids = new ArrayList();
				for (Portalprivilege p : ps) {
					sids.add(p.getId());
				}
				this.portalroleprivilegeDao.deleteByKeys(sids);
				WalkDelete(sids);
				deleteByKeys(sids);
			}
		}
		this.portalroleprivilegeDao.deleteByKeys(list);
	}

	public void editPosUP(Long id) {
		Portalprivilege p = getPortalprivilegeByKey(id);
		PortalprivilegeQuery pq = new PortalprivilegeQuery();
		if (p.getParentId() != null) {
			pq.setParentId(p.getParentId());
		}
		pq.setParentId(p.getParentId());
		pq.orderbyPosition(true);
		pq.setFields("id,position");
		List<Portalprivilege> ps = getPortalprivilegeList(pq);
		int[] b = new int[ps.size()];
		Long[] bId = new Long[ps.size()];
		int index = 0;
		int tempPos = 0;
		Long tempId = null;
		for (Portalprivilege cp : ps) {
			b[index] = cp.getPosition().intValue();
			bId[index] = cp.getId();
			if (cp.getId() == id) {
				if (index == 0) {
					tempPos = b[0];
					tempId = bId[0];
				} else {
					tempPos = b[(index - 1)];
					tempId = bId[(index - 1)];
				}
			}

			index++;
		}
		Portalprivilege tP = new Portalprivilege();
		tP.setId(tempId);
		tP.setPosition(p.getPosition());
		updatePortalprivilegeByKey(tP);
		tP.setId(id);
		tP.setPosition(Integer.valueOf(tempPos));
		updatePortalprivilegeByKey(tP);
	}

	public void editPosDown(Long id) {
		Portalprivilege p = getPortalprivilegeByKey(id);
		PortalprivilegeQuery pq = new PortalprivilegeQuery();
		if (p.getParentId() != null) {
			pq.setParentId(p.getParentId());
		}
		pq.setParentId(p.getParentId());
		pq.orderbyPosition(false);
		pq.setFields("id,position");
		List<Portalprivilege> ps = getPortalprivilegeList(pq);
		int[] b = new int[ps.size()];
		Long[] bId = new Long[ps.size()];
		int index = 0;
		int tempPos = 0;
		Long tempId = null;
		for (Portalprivilege cp : ps) {
			b[index] = cp.getPosition().intValue();
			bId[index] = cp.getId();
			if (cp.getId() == id) {
				if (index == 0) {
					tempPos = b[0];
					tempId = bId[0];
				} else {
					tempPos = b[(index - 1)];
					tempId = bId[(index - 1)];
				}
			}

			index++;
		}
		Portalprivilege tP = new Portalprivilege();
		tP.setId(tempId);
		tP.setPosition(p.getPosition());
		updatePortalprivilegeByKey(tP);
		tP.setId(id);
		tP.setPosition(Integer.valueOf(tempPos));
		updatePortalprivilegeByKey(tP);
	}
}

/*
 * Location:
 * C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name: com.leeson.core.service.impl.PortalprivilegeServiceImpl
 * JD-Core Version: 0.6.2
 */