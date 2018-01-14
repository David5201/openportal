package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Radiusnas;
import com.leeson.core.dao.RadiusnasDao;
import com.leeson.core.query.RadiusnasQuery;
import com.leeson.core.service.RadiusnasService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RadiusnasServiceImpl
  implements RadiusnasService
{
  private static final Log log = LogFactory.getLog(RadiusnasServiceImpl.class);

  @Resource
  RadiusnasDao radiusnasDao;

  public Long addRadiusnas(Radiusnas radiusnas)
  {
    try
    {
      return this.radiusnasDao.addRadiusnas(radiusnas);
    } catch (SQLException e) {
      log.error("dao addRadiusnas error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Radiusnas getRadiusnasByKey(Long id)
  {
    try
    {
      return this.radiusnasDao.getRadiusnasByKey(id);
    } catch (SQLException e) {
      log.error("dao getRadiusnasbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Radiusnas> getRadiusnasByKeys(List<Long> idList)
  {
    try {
      return this.radiusnasDao.getRadiusnasByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getRadiusnassByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.radiusnasDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(RadiusnasQuery radiusnasQuery)
  {
    try
    {
      return this.radiusnasDao.deleteByQuery(radiusnasQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.radiusnasDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.radiusnasDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateRadiusnasByKey(Radiusnas radiusnas)
  {
    try
    {
      return this.radiusnasDao.updateRadiusnasByKey(radiusnas);
    } catch (SQLException e) {
      log.error("dao updateRadiusnas error.Radiusnas:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateRadiusnasByKeyAll(Radiusnas radiusnas)
  {
    try
    {
      return this.radiusnasDao.updateRadiusnasByKeyAll(radiusnas);
    } catch (SQLException e) {
      log.error("dao updateRadiusnasAll error.Radiusnas:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getRadiusnasListWithPage(RadiusnasQuery radiusnasQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.radiusnasDao.getRadiusnasListWithPage(radiusnasQuery));

      page.setTotalCount(this.radiusnasDao.getRadiusnasListCount(radiusnasQuery).intValue());
      page.setPageNo(radiusnasQuery.getPage());
      page.setPageSize(radiusnasQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Radiusnas pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Radiusnas> getRadiusnasList(RadiusnasQuery radiusnasQuery)
  {
    try
    {
      return this.radiusnasDao.getRadiusnasList(radiusnasQuery);
    } catch (SQLException e) {
      log.error("get Radiusnas list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getRadiusnasCount(RadiusnasQuery radiusnasQuery)
  {
    try
    {
      return this.radiusnasDao.getRadiusnasListCount(radiusnasQuery);
    } catch (SQLException e) {
      log.error("get Radiusnas list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.RadiusnasServiceImpl
 * JD-Core Version:    0.6.2
 */