
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
/*
 * MD5 算法
*/
public class MD5 {   
    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    public MD5() {
    }
    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }
    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }
    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
    
    private int compare(String str, String target) {
    	  int d[][]; // 矩阵
    	  int n = str.length();
    	  int m = target.length();
    	  int i; // 遍历str的
    	  int j; // 遍历target的
    	  char ch1; // str的
    	  char ch2; // target的
    	  int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
    	  if (n == 0) {
    	   return m;
    	  }
    	  if (m == 0) {
    	   return n;
    	  }
    	  d = new int[n + 1][m + 1];
    	  for (i = 0; i <= n; i++) { // 初始化第一列
    	   d[i][0] = i;
    	  }
    	  for (j = 0; j <= m; j++) { // 初始化第一行
    	   d[0][j] = j;
    	  }
    	  for (i = 1; i <= n; i++) { // 遍历str
    	   ch1 = str.charAt(i - 1);
    	   // 去匹配target
    	   for (j = 1; j <= m; j++) {
    	    ch2 = target.charAt(j - 1);
    	    if (ch1 == ch2) {
    	     temp = 0;
    	    } else {
    	     temp = 1;
    	    }
    	  // 左边+1,上边+1, 左上角+temp取最小
    	    d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
    	   }
    	  }
    	  return d[n][m];
    	 }
    	 private int min(int one, int two, int three) {
    	  return (one = one < two ? one : two) < three ? one : three;
    	 }
    	 /**

    	 * 获取两字符串的相似度

    	 * 

    	 * @param str

    	 * @param target

    	 * @return

    	 */

    	 public float getSimilarityRatio(String str, String target) {
    	  return 1 - (float)compare(str, target)/Math.max(str.length(), target.length());
    	 }
    
    	 
    public static void main(String[] args) {
    	String [] ss=new String[20];
    	for(int i=0;i<=9;i++){
    	System.out.print("请输入");
        MD5 getMD5 = new MD5();
        Scanner scan = new Scanner(System.in);
        String read = scan.nextLine();
        ss[i]=MD5.GetMD5Code(read);
        System.out.println("加密后："+ss[i]);
        
        Compare lt = new Compare();
        String str = ss[0];
        String target = ss[i];
        if(i>0){
        System.out.println("相似度："+ 100*lt.getSimilarityRatio(str, target) +"%");
        }
    	}
    }
}