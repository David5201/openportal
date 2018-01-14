package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Radiuslinkrecordall;
import com.leeson.core.dao.RadiuslinkrecordallDao;
import com.leeson.core.query.RadiuslinkrecordallQuery;
import com.leeson.core.service.RadiuslinkrecordallService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RadiuslinkrecordallServiceImpl
  implements RadiuslinkrecordallService
{
  private static final Log log = LogFactory.getLog(RadiuslinkrecordallServiceImpl.class);

  @Resource
  RadiuslinkrecordallDao radiuslinkrecordallDao;

  public Long addRadiuslinkrecordall(Radiuslinkrecordall radiuslinkrecordall)
  {
    try
    {
      return this.radiuslinkrecordallDao.addRadiuslinkrecordall(radiuslinkrecordall);
    } catch (SQLException e) {
      log.error("dao addRadiuslinkrecordall error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Radiuslinkrecordall getRadiuslinkrecordallByKey(Long id)
  {
    try
    {
      return this.radiuslinkrecordallDao.getRadiuslinkrecordallByKey(id);
    } catch (SQLException e) {
      log.error("dao getRadiuslinkrecordallbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Radiuslinkrecordall> getRadiuslinkrecordallByKeys(List<Long> idList)
  {
    try {
      return this.radiuslinkrecordallDao.getRadiuslinkrecordallByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getRadiuslinkrecordallsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.radiuslinkrecordallDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(RadiuslinkrecordallQuery radiuslinkrecordallQuery)
  {
    try
    {
      return this.radiuslinkrecordallDao.deleteByQuery(radiuslinkrecordallQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.radiuslinkrecordallDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.radiuslinkrecordallDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateRadiuslinkrecordallByKey(Radiuslinkrecordall radiuslinkrecordall)
  {
    try
    {
      return this.radiuslinkrecordallDao.updateRadiuslinkrecordallByKey(radiuslinkrecordall);
    } catch (SQLException e) {
      log.error("dao updateRadiuslinkrecordall error.Radiuslinkrecordall:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateRadiuslinkrecordallByKeyAll(Radiuslinkrecordall radiuslinkrecordall)
  {
    try
    {
      return this.radiuslinkrecordallDao.updateRadiuslinkrecordallByKeyAll(radiuslinkrecordall);
    } catch (SQLException e) {
      log.error("dao updateRadiuslinkrecordallAll error.Radiuslinkrecordall:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getRadiuslinkrecordallListWithPage(RadiuslinkrecordallQuery radiuslinkrecordallQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.radiuslinkrecordallDao.getRadiuslinkrecordallListWithPage(radiuslinkrecordallQuery));

      page.setTotalCount(this.radiuslinkrecordallDao.getRadiuslinkrecordallListCount(radiuslinkrecordallQuery).intValue());
      page.setPageNo(radiuslinkrecordallQuery.getPage());
      page.setPageSize(radiuslinkrecordallQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Radiuslinkrecordall pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Radiuslinkrecordall> getRadiuslinkrecordallList(RadiuslinkrecordallQuery radiuslinkrecordallQuery)
  {
    try
    {
      return this.radiuslinkrecordallDao.getRadiuslinkrecordallList(radiuslinkrecordallQuery);
    } catch (SQLException e) {
      log.error("get Radiuslinkrecordall list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getRadiuslinkrecordallCount(RadiuslinkrecordallQuery radiuslinkrecordallQuery)
  {
    try
    {
      return this.radiuslinkrecordallDao.getRadiuslinkrecordallListCount(radiuslinkrecordallQuery);
    } catch (SQLException e) {
      log.error("get Radiuslinkrecordall list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.RadiuslinkrecordallServiceImpl
 * JD-Core Version:    0.6.2
 */