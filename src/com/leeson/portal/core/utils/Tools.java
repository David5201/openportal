package com.leeson.portal.core.utils;

public class Tools
{
  public static String IntToHex(int n)
  {
    char[] ch = new char[20];
    int nIndex = 0;
    while (true) {
      int m = n / 16;
      int k = n % 16;
      if (k == 15)
        ch[nIndex] = 'F';
      else if (k == 14)
        ch[nIndex] = 'E';
      else if (k == 13)
        ch[nIndex] = 'D';
      else if (k == 12)
        ch[nIndex] = 'C';
      else if (k == 11)
        ch[nIndex] = 'B';
      else if (k == 10)
        ch[nIndex] = 'A';
      else
        ch[nIndex] = ((char)(48 + k));
      nIndex++;
      if (m == 0)
        break;
      n = m;
    }
    StringBuffer sb = new StringBuffer();
    sb.append(ch, 0, nIndex);
    sb.reverse();
    String strHex = new String("0x");
    strHex = strHex + sb.toString();
    return strHex;
  }

  public static int HexToInt(String strHex)
  {
    int nResult = 0;
    if (!IsHex(strHex))
      return nResult;
    String str = strHex.toUpperCase();
    if ((str.length() > 2) && 
      (str.charAt(0) == '0') && (str.charAt(1) == 'X')) {
      str = str.substring(2);
    }

    int nLen = str.length();
    for (int i = 0; i < nLen; i++) {
      char ch = str.charAt(nLen - i - 1);
      try {
        nResult += GetHex(ch) * GetPower(16, i);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return nResult;
  }

  public static int GetHex(char ch) throws Exception
  {
    if ((ch >= '0') && (ch <= '9'))
      return ch - '0';
    if ((ch >= 'a') && (ch <= 'f'))
      return ch - 'a' + 10;
    if ((ch >= 'A') && (ch <= 'F'))
      return ch - 'A' + 10;
    throw new Exception("error param");
  }

  public static int GetPower(int nValue, int nCount) throws Exception
  {
    if (nCount < 0)
      throw new Exception("nCount can't small than 1!");
    if (nCount == 0)
      return 1;
    int nSum = 1;
    for (int i = 0; i < nCount; i++) {
      nSum *= nValue;
    }
    return nSum;
  }

  public static boolean IsHex(String strHex)
  {
    int i = 0;
    if ((strHex.length() > 2) && 
      (strHex.charAt(0) == '0') && (
      (strHex.charAt(1) == 'X') || (strHex.charAt(1) == 'x')));
    for (i = 2; 
      i < strHex.length(); i++) {
      char ch = strHex.charAt(i);
      if (((ch < '0') || (ch > '9')) && ((ch < 'A') || (ch > 'F')) && (
        (ch < 'a') || (ch > 'f')))
      {
        return false;
      }
    }
    return true;
  }
}

/* Location:           C:\Users\Thinkpad\Desktop\Tool\jd-gui\jd-gui\spring-ops-3.2.4.RELEASE.jar
 * Qualified Name:     com.leeson.portal.core.utils.Tools
 * JD-Core Version:    0.6.2
 */