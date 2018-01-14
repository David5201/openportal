package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portaldepartment;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.dao.PortaldepartmentDao;
import com.leeson.core.query.PortaldepartmentQuery;
import com.leeson.core.query.PortaluserQuery;
import com.leeson.core.service.PortaldepartmentService;
import com.leeson.core.service.PortaluserService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortaldepartmentServiceImpl
  implements PortaldepartmentService
{
  private static final Log log = LogFactory.getLog(PortaldepartmentServiceImpl.class);

  @Resource
  PortaldepartmentDao portaldepartmentDao;

  @Resource
  PortaluserService portaluserService;

  public Long addPortaldepartment(Portaldepartment portaldepartment)
  {
    try
    {
      return this.portaldepartmentDao.addPortaldepartment(portaldepartment);
    } catch (SQLException e) {
      log.error("dao addPortaldepartment error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portaldepartment getPortaldepartmentByKey(Long id)
  {
    try
    {
      return this.portaldepartmentDao.getPortaldepartmentByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortaldepartmentbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portaldepartment> getPortaldepartmentByKeys(List<Long> idList)
  {
    try {
      return this.portaldepartmentDao.getPortaldepartmentByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortaldepartmentsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      List ids = new ArrayList();
      ids.add(id);
      WalkDelete(ids);

      PortaluserQuery uq = new PortaluserQuery();
      uq.setDepartmentId(id);
      List<Portaluser> us = this.portaluserService.getPortaluserList(uq);
      for (Portaluser u : us) {
        u.setDepartmentId(null);
        this.portaluserService.updatePortaluserByKeyAll(u);
      }

      return this.portaldepartmentDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortaldepartmentQuery portaldepartment)
  {
    try
    {
      List ids = new ArrayList();
      ids.add(portaldepartment.getId());
      WalkDelete(ids);

      PortaluserQuery uq = new PortaluserQuery();
      uq.setDepartmentId(portaldepartment.getId());
      List<Portaluser> us = this.portaluserService.getPortaluserList(uq);
      for (Portaluser u : us) {
        u.setDepartmentId(null);
        this.portaluserService.updatePortaluserByKeyAll(u);
      }

      return this.portaldepartmentDao.deleteByQuery(portaldepartment);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      WalkDelete(idList);
      Iterator localIterator2;
      for (Iterator localIterator1 = idList.iterator(); localIterator1.hasNext(); 
        localIterator2.hasNext())
      {
        Long id = (Long)localIterator1.next();
        PortaluserQuery uq = new PortaluserQuery();
        uq.setDepartmentId(id);
        List us = this.portaluserService.getPortaluserList(uq);
        localIterator2 = us.iterator(); 
				  Portaluser u = (Portaluser)localIterator2.next();
        u.setDepartmentId(null);
        this.portaluserService.updatePortaluserByKeyAll(u);
				  continue; 
      }

      return this.portaldepartmentDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortaldepartmentByKey(Portaldepartment portaldepartment)
  {
    try
    {
      return this.portaldepartmentDao.updatePortaldepartmentByKey(portaldepartment);
    } catch (SQLException e) {
      log.error("dao updatePortaldepartment error.Portaldepartment:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortaldepartmentByKeyAll(Portaldepartment portaldepartment)
  {
    try
    {
      return this.portaldepartmentDao.updatePortaldepartmentByKeyAll(portaldepartment);
    } catch (SQLException e) {
      log.error("dao updatePortaldepartmentAll error.Portaldepartment:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortaldepartmentListWithPage(PortaldepartmentQuery portaldepartmentQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portaldepartmentDao.getPortaldepartmentListWithPage(portaldepartmentQuery));

      page.setTotalCount(this.portaldepartmentDao.getPortaldepartmentListCount(portaldepartmentQuery).intValue());
      page.setPageNo(portaldepartmentQuery.getPage());
      page.setPageSize(portaldepartmentQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portaldepartment pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portaldepartment> getPortaldepartmentList(PortaldepartmentQuery portaldepartmentQuery)
  {
    try
    {
      return this.portaldepartmentDao.getPortaldepartmentList(portaldepartmentQuery);
    } catch (SQLException e) {
      log.error("get Portaldepartment list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortaldepartmentCount(PortaldepartmentQuery portaldepartmentQuery)
  {
    try
    {
      return this.portaldepartmentDao.getPortaldepartmentListCount(portaldepartmentQuery);
    } catch (SQLException e) {
      log.error("get Portaldepartment list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public void WalkDelete(List<Long> list)
  {
    for (Long id : list) {
      PortaldepartmentQuery pq = new PortaldepartmentQuery();
      pq.setParentId(id);
      List ps = getPortaldepartmentList(pq);
      if (ps.size() > 0) {
        List sids = new ArrayList();
        Iterator localIterator3;
        for (Iterator localIterator2 = ps.iterator(); localIterator2.hasNext(); 
          localIterator3.hasNext())
        {
          Portaldepartment p = (Portaldepartment)localIterator2.next();
          sids.add(p.getId());

          PortaluserQuery uq = new PortaluserQuery();
          uq.setDepartmentId(p.getId());
          List us = this.portaluserService.getPortaluserList(uq);
          localIterator3 = us.iterator(); 
					Portaluser u = (Portaluser)localIterator3.next();
          u.setDepartmentId(null);
          this.portaluserService.updatePortaluserByKeyAll(u);
					continue;
        }

        WalkDelete(sids);
        deleteByKeys(sids);
      }
    }
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortaldepartmentServiceImpl
 * JD-Core Version:    0.6.2
 */