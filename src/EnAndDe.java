/**��֤RSA�㷨�ļ��ܺͽ���ԭ��**/

import java.util.Scanner;
public class EnAndDe {
	private long p = 0;
	private long q = 0;
	private long n = 0;
	private long f = 0; // ŷ������
	private long b = 0; // ��Կ
	private long a = 0; // ��Կ
	private long c = 0; // ����
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
		System.out.println("����:   p=" + this.p + "    q=" + this.q);

		this.n = (long) p * q;
		this.f = (long) (p - 1) * (q - 1);

		System.out.println("       n=p*q="+this.p+"*" +this.q+"="+ this.n);
		System.out.println("ŷ������f=(p-1)*(q-1)=" + this.f);
	}

	// ���ɹ���
	public void getPublic_key() throws Exception {	
			this.b =7;
		System.out.println("��ԿΪ��" + "(" + this.n + "," + this.b + ")");
	}
	
	// ����˽Կ e*d=1(mod��(n))==> d = (k��(n)+1) / e	
		public void getPrivate_key() {
			long value = 1; // value ��e��d�ĳ˻�
			outer: for (long k = 1;; k++) {
				value = k * this.f + 1;
				if ((value % this.b == 0)) {
					this.a = value / this.b;
					break outer;
				}
			}
			System.out.println("˽ԿaΪ��" + this.a);
			//����
			 System.out.print("����������M1��");
				Scanner scan = new Scanner(System.in);
		        int m = scan.nextInt(); 
		        long startTime=System.nanoTime(); 
		        this.c=(long) Math.pow(m, b)%n;
				System.out.println("����C1��" + this.c);
				long endTime=System.nanoTime();
				System.out.println("����ʱ�䣺 "+(endTime-startTime)+"ns");
			// ����	
			
			System.out.print("����������C2��");
			Scanner sc = new Scanner(System.in);
	        long c = sc.nextInt();
	       long c1 = 0;	
	       long startTime1=System.nanoTime(); 
	        for(long i=0;i<a;i++)
	        {
	        	
	        	c1=t*c;
	        	t=(long) (c1%n);
	          
	        }
	        System.out.println("����M2��" +t);	    
	        long endTime1=System.nanoTime();
			System.out.println("����ʱ�䣺 "+(endTime1-startTime1)+"ns");
			long n=(endTime1-startTime1)/(endTime-startTime);
			System.out.println("����ʱ��:����ʱ��N="+n);
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
