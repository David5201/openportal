package com.leeson.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class MySQLUtils
{
  public static boolean backup(String username, String password, String dbName, String backupPath)
  {
    boolean status = false;
    String sys = System.getProperty("os.name");
    System.out.println(sys);

    String command = "";
    if (sys.startsWith("W")) {
      command = "cmd /c mysqldump -u " + username + " -p" + password + 
        " --default-character-set=utf8 " + dbName + ">" + 
        backupPath;
    } else if (sys.startsWith("L")) {
      command = "mysqldump -u " + username + " -p" + password + " " + 
        dbName + " -r " + backupPath;
    } else {
      System.out.println("操作系统属性获取失败！！");
      return false;
    }
    try {
      Process runtimeProcess = Runtime.getRuntime().exec(command);
      int processComplete = runtimeProcess.waitFor();
      if (processComplete == 0) {
        System.out.println("MySQLManager: Backup database Successfull");
        status = true;
      } else {
        System.out.println("MySQLManager: Backup database Failure!");
      }
    } catch (IOException ioe) {
      System.out.println("Exception IO");
      ioe.printStackTrace();
    } catch (Exception e) {
      System.out.println("Exception");
      e.printStackTrace();
    }
    return status;
  }

  public static boolean restore(String username, String password, String dbName, String filePath)
  {
    boolean status = false;
    String sys = System.getProperty("os.name");
    System.out.println(sys);

    String command = "";
    if (sys.startsWith("W")) {
      command = "cmd /c mysql -u " + username + " -p" + password + 
        " --default-character-set=utf8 " + dbName + "<" + 
        filePath;
      try {
        Process runtimeProcess = Runtime.getRuntime().exec(command);
        int processComplete = runtimeProcess.waitFor();
        if (processComplete == 0) {
          System.out
            .println("MySQLManager:Restore database Successfull");
          status = true;
        } else {
          System.out.println("MySQLManager:Restore database Failure");
        }
      } catch (IOException ioe) {
        System.out.println("Exception IO");
        ioe.printStackTrace();
      } catch (Exception e) {
        System.out.println("Exception");
        e.printStackTrace();
      }
      return status;
    }if (sys.startsWith("L")) {
      command = "mysql -u " + username + " -p" + password + " " + 
        dbName + " < " + filePath;
      try
      {
        Runtime runtime = Runtime.getRuntime();
        String[] commandL = { "/bin/bash", "-c", command };
        Process process = runtime.exec(commandL);

        InputStreamReader in = new InputStreamReader(
          process.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String line = null;
        while ((line = br.readLine()) != null) {
          System.out.println(line);
        }
        br.close();
        in.close();

        InputStreamReader in2 = new InputStreamReader(
          process.getErrorStream());
        BufferedReader br2 = new BufferedReader(in2);
        String line2 = null;
        while ((line2 = br2.readLine()) != null) {
          System.out.println("=" + line2);
        }
        br2.close();
        in2.close();
        System.out.println("MySQLManager:Restore database Successfull");
        return true;
      } catch (Exception e) {
        System.out.println("MySQLManager:Restore database Failure");
        System.out.println(e);
        return false;
      }
    }

    System.out.println("操作系统属性获取失败！！");
    return false;
  }

  public static void main(String[] args)
  {
    backup("root", "iwsiqh", "openportalserver", "d:/update.dat");
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.common.utils.MySQLUtils
 * JD-Core Version:    0.6.2
 */