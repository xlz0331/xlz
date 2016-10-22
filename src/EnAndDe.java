/**验证RSA算法的加密和解密原理**/

import java.util.Scanner;
public class EnAndDe {
	private long p = 0;
	private long q = 0;
	private long n = 0;
	private long f = 0; // 欧拉函数
	private long b = 0; // 公钥
	private long a = 0; // 密钥
	private long c = 0; // 密文
	private long t = 1;

	public void bigprimeRandom() {
		
			n=Long.parseLong("4294967297");
			for (long j = 2; j < 1000; j++) {
			      for (int i = 2; i <= (int)j/2; i++) {
			        if (j % i == 0) {
			          break;
			        }
			      }
			      if (j > (int)j/2 ) {
			    	  if(n%j==0){
			         p=j;
			         q=n/p;
			      }
			    }			
			}		
	}

	public void inputPQ() throws Exception {

		this.bigprimeRandom();
		System.out.println("素数:   p=" + this.p + "    q=" + this.q);

		this.n = (long) p * q;
		this.f = (long) (p - 1) * (q - 1);

		System.out.println("       n=p*q="+this.p+"*" +this.q+"="+ this.n);
		System.out.println("欧拉函数f=(p-1)*(q-1)=" + this.f);
	}

	// 生成公匙
	public void getPublic_key() throws Exception {	
			this.b =7;
		System.out.println("公钥为：" + "(" + this.n + "," + this.b + ")");
	}
	
	// 生成私钥 e*d=1(modψ(n))==> d = (kψ(n)+1) / e	
		public void getPrivate_key() {
			long value = 1; // value 是e和d的乘积
			outer: for (long k = 1;; k++) {
				value = k * this.f + 1;
				if ((value % this.b == 0)) {
					this.a = value / this.b;
					break outer;
				}
			}
			System.out.println("私钥a为：" + this.a);
			//加密
			 System.out.print("请输入明文M1：");
				Scanner scan = new Scanner(System.in);
		        int m = scan.nextInt(); 
		        long startTime=System.nanoTime(); 
		        this.c=(long) Math.pow(m, b)%n;
				System.out.println("密文C1：" + this.c);
				long endTime=System.nanoTime();
				System.out.println("加密时间： "+(endTime-startTime)+"ns");
			// 解密	
			
			System.out.print("请输入密文C2：");
			Scanner sc = new Scanner(System.in);
	        long c = sc.nextInt();
	       long c1 = 0;	
	       long startTime1=System.nanoTime(); 
	        for(long i=0;i<a;i++)
	        {
	        	
	        	c1=t*c;
	        	t=(long) (c1%n);
	          
	        }
	        System.out.println("明文M2：" +t);	    
	        long endTime1=System.nanoTime();
			System.out.println("解密时间： "+(endTime1-startTime1)+"ns");
			long n=(endTime1-startTime1)/(endTime-startTime);
			System.out.println("解密时间:加密时间N="+n);
		}

	public static void main(String[] args) {
		try {
			EnAndDe t = new EnAndDe();
			t.inputPQ();
			t.getPublic_key();
			t.getPrivate_key();
			//t.pascolum();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
