package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Radiuslinkrecordallout;
import com.leeson.core.dao.RadiuslinkrecordalloutDao;
import com.leeson.core.query.RadiuslinkrecordalloutQuery;
import com.leeson.core.service.RadiuslinkrecordalloutService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RadiuslinkrecordalloutServiceImpl
  implements RadiuslinkrecordalloutService
{
  private static final Log log = LogFactory.getLog(RadiuslinkrecordalloutServiceImpl.class);

  @Resource
  RadiuslinkrecordalloutDao radiuslinkrecordalloutDao;

  public Long addRadiuslinkrecordallout(Radiuslinkrecordallout radiuslinkrecordallout)
  {
    try
    {
      return this.radiuslinkrecordalloutDao.addRadiuslinkrecordallout(radiuslinkrecordallout);
    } catch (SQLException e) {
      log.error("dao addRadiuslinkrecordallout error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Radiuslinkrecordallout getRadiuslinkrecordalloutByKey(Long id)
  {
    try
    {
      return this.radiuslinkrecordalloutDao.getRadiuslinkrecordalloutByKey(id);
    } catch (SQLException e) {
      log.error("dao getRadiuslinkrecordalloutbyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Radiuslinkrecordallout> getRadiuslinkrecordalloutByKeys(List<Long> idList)
  {
    try {
      return this.radiuslinkrecordalloutDao.getRadiuslinkrecordalloutByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getRadiuslinkrecordalloutsByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.radiuslinkrecordalloutDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(RadiuslinkrecordalloutQuery radiuslinkrecordalloutQuery)
  {
    try
    {
      return this.radiuslinkrecordalloutDao.deleteByQuery(radiuslinkrecordalloutQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.radiuslinkrecordalloutDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.radiuslinkrecordalloutDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateRadiuslinkrecordalloutByKey(Radiuslinkrecordallout radiuslinkrecordallout)
  {
    try
    {
      return this.radiuslinkrecordalloutDao.updateRadiuslinkrecordalloutByKey(radiuslinkrecordallout);
    } catch (SQLException e) {
      log.error("dao updateRadiuslinkrecordallout error.Radiuslinkrecordallout:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updateRadiuslinkrecordalloutByKeyAll(Radiuslinkrecordallout radiuslinkrecordallout)
  {
    try
    {
      return this.radiuslinkrecordalloutDao.updateRadiuslinkrecordalloutByKeyAll(radiuslinkrecordallout);
    } catch (SQLException e) {
      log.error("dao updateRadiuslinkrecordalloutAll error.Radiuslinkrecordallout:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getRadiuslinkrecordalloutListWithPage(RadiuslinkrecordalloutQuery radiuslinkrecordalloutQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.radiuslinkrecordalloutDao.getRadiuslinkrecordalloutListWithPage(radiuslinkrecordalloutQuery));

      page.setTotalCount(this.radiuslinkrecordalloutDao.getRadiuslinkrecordalloutListCount(radiuslinkrecordalloutQuery).intValue());
      page.setPageNo(radiuslinkrecordalloutQuery.getPage());
      page.setPageSize(radiuslinkrecordalloutQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Radiuslinkrecordallout pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Radiuslinkrecordallout> getRadiuslinkrecordalloutList(RadiuslinkrecordalloutQuery radiuslinkrecordalloutQuery)
  {
    try
    {
      return this.radiuslinkrecordalloutDao.getRadiuslinkrecordalloutList(radiuslinkrecordalloutQuery);
    } catch (SQLException e) {
      log.error("get Radiuslinkrecordallout list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getRadiuslinkrecordalloutCount(RadiuslinkrecordalloutQuery radiuslinkrecordalloutQuery)
  {
    try
    {
      return this.radiuslinkrecordalloutDao.getRadiuslinkrecordalloutListCount(radiuslinkrecordalloutQuery);
    } catch (SQLException e) {
      log.error("get Radiuslinkrecordallout list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.RadiuslinkrecordalloutServiceImpl
 * JD-Core Version:    0.6.2
 */