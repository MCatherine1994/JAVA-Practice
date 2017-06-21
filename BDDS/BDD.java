import java.util.*;
import java.lang.*;

class BDD {

	// YOUR OWN VARIABLES, CLASSES, ETC.
	int n;
	int s;
	
	int index;
	long count;
	int[] v;
	int[] l;
	int[] h;
	double[] c;
	int[] m;
	int[] p;
	int[] t;
	int[] x;
	int j;
	int num;
	int[] a;

	BDD ( Scanner in ) {
		n = in.nextInt();
		s = in.nextInt();
		j = 1;
		count = 0;
		num = 0;
		v = new int[s+1];
		l = new int[s+1];
		h = new int[s+1];
		c = new double[s+1];
		p = new int[n+2];
		a = new int[s];
   		m = new int[s+1];
   		t = new int[s+1];
   		x = new int[s+1];
		for(int i = s-1; i >=0; i--){
			v[i] = in.nextInt();
			l[i] = in.nextInt();
			h[i] = in.nextInt();
		}
  		// READS IN A BDD
  	}	

   	long countBDD () {
   		// FOR YOU TO CODE
   		// Counts the number of solutions
   		int k = 2;
   		double x = 2.0;
   		c[0] = 0;
   		c[1] = 1;
   		while(k < s){
   			int l1 = l[k];
   			int h1= h[k];   			
   			double y = v[l1]-v[k]-1;
			   double z = v[h1]-v[k]-1;
   			c[k] = Math.pow(x,y) * c[l1] + Math.pow(x,z) * c[h1];
   			k++;
   		}
   		double y = v[s-1]-1;
   		count = (long) (Math.pow(x,y) * c[s-1]);
   		return count;
   	}

   	void listBDD () {
   		// FOR YOU TO CODE
   		// Outputs all solutions, one per line, in format of example below		
   		List(1, s-1);		
   		
   	}

   	void List(int j, int p){
   		int sum = 0;
   		if(v[p] > j){
   			x[j] = 0;
   			List(j+1,p);
   			x[j] = 1;
   			List(j+1,p);
   		}else if(p == 1){
            for(int i = 1; i <=n; i++){
               System.out.print(x[i]);
               sum = sum+x[i];
            }
            a[num] = sum;
            System.out.print(a[num]);
            num++;           
            System.out.print("\n");
   			
   		}else{
   			x[j] = 0;
   			if(l[p] != 0){
   				List(j+1,l[p]);
   			}
   			x[j] = 1;
   			if(h[p] != 0){
   				List(j+1,h[p]);
   			}
   		}

   	}

   	int[] polyBDD () {
   		// FOR YOU TO CODE
   		// Outputs the coefficients of the generating function (a polynomial)
  	 	// E.g., (19) in the text.
  	 	int[] poly = new int[n+1];
        for(int i = 0; i < s-1;i++){
        	poly[a[i]]++;    	
        }
        return poly;
        
  	}

  	

   	int[] maxBDD ( int[] w ) {
   		// FOR YOU TO CODE
   		// Maximizes a linear function, put the max value at index location 0.	

   		p[n+1] = 0;
   		int j;
   		for(j = n; j >= 1; j--){
   			p[j] = p[j+1] + Math.max(w[j],0);   
         }
         int k;
   		m[1] = 0;
   		int m1 = 0;
         int v1;
         int l1;
         int h1;
   		for(k = 2; k < s; k++){
   			v1 = v[k];
   			l1 = l[k];
   			h1 = h[k];
   			t[k] = 0;
   			if(l1 != 0){
   				m[k] = m[l1] + p[v1+1] - p[v[l1]];
   			}
   			if(h1 != 0 && (l1 == 0) || (m1 > m[k]) ){
   				m1 = m[h1] + p[v1+1] - p[v[h1]] + w[v1];
               m[k] = m1;
               t[k] = 1;
   			}
   			
   		}

   		j = 0;
   		k = s - 1;
   		while(j != n){
   			while(j < v[k] - 1){
   				j++;
   				if(w[j] > 0){
   					x[j] = 1;
   				}   					
   				else{
   					x[j] = 0;
   				}
   				
   			}
   			if(k > 1){
   				j++;
   				x[j] = t[k];
   				if(t[k] == 0)
   					k = l[k];
   				else
   					k = h[k];
   			}
   		}

   		x[0] = m[s-1];
   		return x;
   	}

  	// YOU CAN OMIT THIS MAIN
   	// But illustrates how we might test your code
   	public static void main (String[] args) {
    	Scanner in = new Scanner( System.in );
      	BDD bdd = new BDD( in );

      	System.out.println( "count = "+bdd.countBDD() );

      	int[] w = {0,1,-2,-3,4};
      	int[] x = bdd.maxBDD( w );
      	System.out.print( "max = "+x[0]+" soln = " );
      	for (int i=1; i<=bdd.n; ++i) System.out.print( x[i]+" " );

      	System.out.println( "\nlisting: " );
      	bdd.listBDD();

      	System.out.print( "polynomial: " );
      	int[] a = bdd.polyBDD();
      	for (int i=0; i<=bdd.n; ++i) System.out.print( a[i]+" " );
      	System.out.println();
   	}

}