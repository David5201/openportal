package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portaluser;
import com.leeson.core.dao.PortaluserDao;
import com.leeson.core.query.PortaluserQuery;
import com.leeson.core.service.PortaluserService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortaluserServiceImpl
  implements PortaluserService
{
  private static final Log log = LogFactory.getLog(PortaluserServiceImpl.class);

  @Resource
  PortaluserDao portaluserDao;

  public Long addPortaluser(Portaluser portaluser)
  {
    try
    {
      return this.portaluserDao.addPortaluser(portaluser);
    } catch (SQLException e) {
      log.error("dao addPortaluser error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portaluser getPortaluserByKey(Long id)
  {
    try
    {
      return this.portaluserDao.getPortaluserByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortaluserbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portaluser> getPortaluserByKeys(List<Long> idList)
  {
    try {
      return this.portaluserDao.getPortaluserByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalusersByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portaluserDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortaluserQuery portaluser)
  {
    try
    {
      return this.portaluserDao.deleteByQuery(portaluser);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portaluserDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortaluserByKey(Portaluser portaluser)
  {
    try
    {
      return this.portaluserDao.updatePortaluserByKey(portaluser);
    } catch (SQLException e) {
      log.error("dao updatePortaluser error.Portaluser:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortaluserByKeyAll(Portaluser portaluser)
  {
    try
    {
      return this.portaluserDao.updatePortaluserByKeyAll(portaluser);
    } catch (SQLException e) {
      log.error("dao updatePortaluserAll error.Portaluser:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortaluserListWithPage(PortaluserQuery portaluserQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portaluserDao.getPortaluserListWithPage(portaluserQuery));

      page.setTotalCount(this.portaluserDao.getPortaluserListCount(portaluserQuery).intValue());
      page.setPageNo(portaluserQuery.getPage());
      page.setPageSize(portaluserQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portaluser pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portaluser> getPortaluserList(PortaluserQuery portaluserQuery)
  {
    try
    {
      return this.portaluserDao.getPortaluserList(portaluserQuery);
    } catch (SQLException e) {
      log.error("get Portaluser list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortaluserCount(PortaluserQuery portaluserQuery)
  {
    try
    {
      return this.portaluserDao.getPortaluserListCount(portaluserQuery);
    } catch (SQLException e) {
      log.error("get Portaluser list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortaluserServiceImpl
 * JD-Core Version:    0.6.2
 */