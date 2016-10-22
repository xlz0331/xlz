
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
/*
 * MD5 �㷨
*/
public class MD5 {   
    // ȫ������
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    public MD5() {
    }
    // ������ʽΪ���ָ��ַ���
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
    // ������ʽֻΪ����
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }
    // ת���ֽ�����Ϊ16�����ִ�
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
            // md.digest() �ú�������ֵΪ��Ź�ϣֵ�����byte����
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
    
    private int compare(String str, String target) {
    	  int d[][]; // ����
    	  int n = str.length();
    	  int m = target.length();
    	  int i; // ����str��
    	  int j; // ����target��
    	  char ch1; // str��
    	  char ch2; // target��
    	  int temp; // ��¼��ͬ�ַ�,��ĳ������λ��ֵ������,����0����1
    	  if (n == 0) {
    	   return m;
    	  }
    	  if (m == 0) {
    	   return n;
    	  }
    	  d = new int[n + 1][m + 1];
    	  for (i = 0; i <= n; i++) { // ��ʼ����һ��
    	   d[i][0] = i;
    	  }
    	  for (j = 0; j <= m; j++) { // ��ʼ����һ��
    	   d[0][j] = j;
    	  }
    	  for (i = 1; i <= n; i++) { // ����str
    	   ch1 = str.charAt(i - 1);
    	   // ȥƥ��target
    	   for (j = 1; j <= m; j++) {
    	    ch2 = target.charAt(j - 1);
    	    if (ch1 == ch2) {
    	     temp = 0;
    	    } else {
    	     temp = 1;
    	    }
    	  // ���+1,�ϱ�+1, ���Ͻ�+tempȡ��С
    	    d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
    	   }
    	  }
    	  return d[n][m];
    	 }
    	 private int min(int one, int two, int three) {
    	  return (one = one < two ? one : two) < three ? one : three;
    	 }
    	 /**

    	 * ��ȡ���ַ��������ƶ�

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
    	System.out.print("������");
        MD5 getMD5 = new MD5();
        Scanner scan = new Scanner(System.in);
        String read = scan.nextLine();
        ss[i]=MD5.GetMD5Code(read);
        System.out.println("���ܺ�"+ss[i]);
        
        Compare lt = new Compare();
        String str = ss[0];
        String target = ss[i];
        if(i>0){
        System.out.println("���ƶȣ�"+ 100*lt.getSimilarityRatio(str, target) +"%");
        }
    	}
    }
}