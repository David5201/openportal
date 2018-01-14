package com.leeson.common.data;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class DataDaoImpl extends JdbcDaoSupport
  implements DataDao
{
  private static String db;

  public List<String> listDBs()
  {
    String sql = "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA";
    List dbs = new ArrayList();
    SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
    while (set.next()) {
      dbs.add(set.getString(1));
    }
    return dbs;
  }

  public List<Table> listTables() {
    String sql = "select table_name,table_comment,engine,table_rows,auto_increment from information_schema.tables where table_schema='" + 
      db + "'";
    List tables = new ArrayList();
    SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
    while (set.next()) {
      Table table = new Table();
      table.setName(set.getString(1));
      table.setComment(set.getString(2).split(";")[0]);
      table.setEngine(set.getString(3));
      table.setRows(Integer.valueOf(set.getInt(4)));
      table.setAuto_increment(Integer.valueOf(set.getInt(5)));
      tables.add(table);
    }
    return tables;
  }

  public Table findTable(String tablename) {
    String sql = "select table_name,table_comment,engine,table_rows,auto_increment from information_schema.tables where table_schema='" + 
      db + "' and table_name='" + tablename + "'";
    List tables = new ArrayList();
    SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
    while (set.next()) {
      Table table = new Table();
      table.setName(set.getString(1));
      table.setComment(set.getString(2).split(";")[0]);
      table.setEngine(set.getString(3));
      table.setRows(Integer.valueOf(set.getInt(4)));
      table.setAuto_increment(Integer.valueOf(set.getInt(5)));
      tables.add(table);
    }
    if (tables.size() > 0) {
      return (Table)tables.get(0);
    }
    return null;
  }

  public List<Field> listFields(String tablename)
  {
    String sql = "select column_name,column_type,column_default,column_key,column_comment,is_nullable,extra from information_schema.columns where table_schema='" + 
      db + "' and table_name='" + tablename + "'";
    List fields = new ArrayList();
    SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
    while (set.next()) {
      Field field = new Field();
      field.setName(set.getString(1));
      field.setFieldType(set.getString(2));
      field.setFieldDefault(set.getString(3));
      field.setFieldProperty(set.getString(4));
      field.setComment(set.getString(5));
      field.setNullable(set.getString(6));
      field.setExtra(set.getString(7));
      fields.add(field);
    }
    return fields;
  }

  public List<Constraints> listConstraints(String tablename) {
    String sql = "select constraint_name,table_name,column_name,referenced_table_name,referenced_column_name from information_schema.KEY_COLUMN_USAGE where constraint_schema='" + 
      db + "' and table_name='" + tablename + "'";
    List constraints = new ArrayList();
    SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
    while (set.next()) {
      Constraints constraint = new Constraints();
      constraint.setName(set.getString(1));
      constraint.setTableName(set.getString(2));
      constraint.setColumnName(set.getString(3));
      constraint.setReferencedTableName(set.getString(4));
      constraint.setReferencedColumnName(set.getString(5));
      constraints.add(constraint);
    }
    return constraints;
  }

  public static void setDb(String indb) {
    db = indb;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.data.DataDaoImpl
 * JD-Core Version:    0.6.2
 */