package com.leeson.common.data;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DataMngImpl
  implements DataMng
{
  private DataDao dao;

  @Transactional(readOnly=true)
  public List<String> listDBs()
  {
    return this.dao.listDBs();
  }

  @Transactional(readOnly=true)
  public List<Table> listTabels() {
    return this.dao.listTables();
  }

  @Transactional(readOnly=true)
  public Table findTable(String tablename) {
    return this.dao.findTable(tablename);
  }

  @Transactional(readOnly=true)
  public List<Field> listFields(String tablename) {
    return this.dao.listFields(tablename);
  }

  @Transactional(readOnly=true)
  public List<Constraints> listConstraints(String tablename) {
    return this.dao.listConstraints(tablename);
  }

  @Transactional(readOnly=true)
  public int setDb(String db) {
    DataDaoImpl.setDb(db);
    return 1;
  }

  @Autowired
  public void setDao(DataDao dao)
  {
    this.dao = dao;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.data.DataMngImpl
 * JD-Core Version:    0.6.2
 */