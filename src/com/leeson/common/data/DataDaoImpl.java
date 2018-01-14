/*    */ package com.leeson.common.data;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
/*    */ import org.springframework.jdbc.core.support.JdbcDaoSupport;
/*    */ import org.springframework.jdbc.support.rowset.SqlRowSet;
/*    */ 
/*    */ public class DataDaoImpl extends JdbcDaoSupport
/*    */   implements DataDao
/*    */ {
/*    */   private static String db;
/*    */ 
/*    */   public List<String> listDBs()
/*    */   {
/* 13 */     String sql = "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA";
/* 14 */     List dbs = new ArrayList();
/* 15 */     SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
/* 16 */     while (set.next()) {
/* 17 */       dbs.add(set.getString(1));
/*    */     }
/* 19 */     return dbs;
/*    */   }
/*    */ 
/*    */   public List<Table> listTables() {
/* 23 */     String sql = "select table_name,table_comment,engine,table_rows,auto_increment from information_schema.tables where table_schema='" + 
/* 24 */       db + "'";
/* 25 */     List tables = new ArrayList();
/* 26 */     SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
/* 27 */     while (set.next()) {
/* 28 */       Table table = new Table();
/* 29 */       table.setName(set.getString(1));
/* 30 */       table.setComment(set.getString(2).split(";")[0]);
/* 31 */       table.setEngine(set.getString(3));
/* 32 */       table.setRows(Integer.valueOf(set.getInt(4)));
/* 33 */       table.setAuto_increment(Integer.valueOf(set.getInt(5)));
/* 34 */       tables.add(table);
/*    */     }
/* 36 */     return tables;
/*    */   }
/*    */ 
/*    */   public Table findTable(String tablename) {
/* 40 */     String sql = "select table_name,table_comment,engine,table_rows,auto_increment from information_schema.tables where table_schema='" + 
/* 41 */       db + "' and table_name='" + tablename + "'";
/* 42 */     List tables = new ArrayList();
/* 43 */     SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
/* 44 */     while (set.next()) {
/* 45 */       Table table = new Table();
/* 46 */       table.setName(set.getString(1));
/* 47 */       table.setComment(set.getString(2).split(";")[0]);
/* 48 */       table.setEngine(set.getString(3));
/* 49 */       table.setRows(Integer.valueOf(set.getInt(4)));
/* 50 */       table.setAuto_increment(Integer.valueOf(set.getInt(5)));
/* 51 */       tables.add(table);
/*    */     }
/* 53 */     if (tables.size() > 0) {
/* 54 */       return (Table)tables.get(0);
/*    */     }
/* 56 */     return null;
/*    */   }
/*    */ 
/*    */   public List<Field> listFields(String tablename)
/*    */   {
/* 62 */     String sql = "select column_name,column_type,column_default,column_key,column_comment,is_nullable,extra from information_schema.columns where table_schema='" + 
/* 63 */       db + "' and table_name='" + tablename + "'";
/* 64 */     List fields = new ArrayList();
/* 65 */     SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
/* 66 */     while (set.next()) {
/* 67 */       Field field = new Field();
/* 68 */       field.setName(set.getString(1));
/* 69 */       field.setFieldType(set.getString(2));
/* 70 */       field.setFieldDefault(set.getString(3));
/* 71 */       field.setFieldProperty(set.getString(4));
/* 72 */       field.setComment(set.getString(5));
/* 73 */       field.setNullable(set.getString(6));
/* 74 */       field.setExtra(set.getString(7));
/* 75 */       fields.add(field);
/*    */     }
/* 77 */     return fields;
/*    */   }
/*    */ 
/*    */   public List<Constraints> listConstraints(String tablename) {
/* 81 */     String sql = "select constraint_name,table_name,column_name,referenced_table_name,referenced_column_name from information_schema.KEY_COLUMN_USAGE where constraint_schema='" + 
/* 82 */       db + "' and table_name='" + tablename + "'";
/* 83 */     List constraints = new ArrayList();
/* 84 */     SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
/* 85 */     while (set.next()) {
/* 86 */       Constraints constraint = new Constraints();
/* 87 */       constraint.setName(set.getString(1));
/* 88 */       constraint.setTableName(set.getString(2));
/* 89 */       constraint.setColumnName(set.getString(3));
/* 90 */       constraint.setReferencedTableName(set.getString(4));
/* 91 */       constraint.setReferencedColumnName(set.getString(5));
/* 92 */       constraints.add(constraint);
/*    */     }
/* 94 */     return constraints;
/*    */   }
/*    */ 
/*    */   public static void setDb(String indb) {
/* 98 */     db = indb;
/*    */   }
/*    */ }

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.data.DataDaoImpl
 * JD-Core Version:    0.6.2
 */