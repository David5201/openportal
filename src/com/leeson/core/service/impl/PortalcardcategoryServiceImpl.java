package com.leeson.core.service.impl;

import com.leeson.common.page.Pagination;
import com.leeson.core.bean.Portalcardcategory;
import com.leeson.core.dao.PortalcardcategoryDao;
import com.leeson.core.query.PortalcardcategoryQuery;
import com.leeson.core.service.PortalcardcategoryService;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalcardcategoryServiceImpl
  implements PortalcardcategoryService
{
  private static final Log log = LogFactory.getLog(PortalcardcategoryServiceImpl.class);

  @Resource
  PortalcardcategoryDao portalcardcategoryDao;

  public Long addPortalcardcategory(Portalcardcategory portalcardcategory)
  {
    try
    {
      return this.portalcardcategoryDao.addPortalcardcategory(portalcardcategory);
    } catch (SQLException e) {
      log.error("dao addPortalcardcategory error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Portalcardcategory getPortalcardcategoryByKey(Long id)
  {
    try
    {
      return this.portalcardcategoryDao.getPortalcardcategoryByKey(id);
    } catch (SQLException e) {
      log.error("dao getPortalcardcategorybyKey error.:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalcardcategory> getPortalcardcategoryByKeys(List<Long> idList)
  {
    try {
      return this.portalcardcategoryDao.getPortalcardcategoryByKeys(idList);
    } catch (SQLException e) {
      log.error("dao getPortalcardcategorysByKeys erorr." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKey(Long id)
  {
    try
    {
      return this.portalcardcategoryDao.deleteByKey(id);
    } catch (SQLException e) {
      log.error("dao deleteByKey error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByQuery(PortalcardcategoryQuery portalcardcategoryQuery)
  {
    try
    {
      return this.portalcardcategoryDao.deleteByQuery(portalcardcategoryQuery);
    } catch (SQLException e) {
      log.error("dao deleteByQuery error. :" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteByKeys(List<Long> idList)
  {
    try
    {
      return this.portalcardcategoryDao.deleteByKeys(idList);
    } catch (SQLException e) {
      log.error("dao deleteByKeys error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer deleteAll()
  {
    try
    {
      return this.portalcardcategoryDao.deleteAll();
    } catch (SQLException e) {
      log.error("dao deleteAll error. s:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalcardcategoryByKey(Portalcardcategory portalcardcategory)
  {
    try
    {
      return this.portalcardcategoryDao.updatePortalcardcategoryByKey(portalcardcategory);
    } catch (SQLException e) {
      log.error("dao updatePortalcardcategory error.Portalcardcategory:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  public Integer updatePortalcardcategoryByKeyAll(Portalcardcategory portalcardcategory)
  {
    try
    {
      return this.portalcardcategoryDao.updatePortalcardcategoryByKeyAll(portalcardcategory);
    } catch (SQLException e) {
      log.error("dao updatePortalcardcategoryAll error.Portalcardcategory:" + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Pagination getPortalcardcategoryListWithPage(PortalcardcategoryQuery portalcardcategoryQuery)
  {
    try {
      Pagination page = new Pagination();
      page.setList(this.portalcardcategoryDao.getPortalcardcategoryListWithPage(portalcardcategoryQuery));

      page.setTotalCount(this.portalcardcategoryDao.getPortalcardcategoryListCount(portalcardcategoryQuery).intValue());
      page.setPageNo(portalcardcategoryQuery.getPage());
      page.setPageSize(portalcardcategoryQuery.getPageSize());
      return page;
    } catch (Exception e) {
      log.error("get Portalcardcategory pagination error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public List<Portalcardcategory> getPortalcardcategoryList(PortalcardcategoryQuery portalcardcategoryQuery)
  {
    try
    {
      return this.portalcardcategoryDao.getPortalcardcategoryList(portalcardcategoryQuery);
    } catch (SQLException e) {
      log.error("get Portalcardcategory list error." + e.getMessage(), e);
    }throw new RuntimeException();
  }

  @Transactional(readOnly=true)
  public Integer getPortalcardcategoryCount(PortalcardcategoryQuery portalcardcategoryQuery)
  {
    try
    {
      return this.portalcardcategoryDao.getPortalcardcategoryListCount(portalcardcategoryQuery);
    } catch (SQLException e) {
      log.error("get Portalcardcategory list Count." + e.getMessage(), e);
    }throw new RuntimeException();
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.core.service.impl.PortalcardcategoryServiceImpl
 * JD-Core Version:    0.6.2
 */