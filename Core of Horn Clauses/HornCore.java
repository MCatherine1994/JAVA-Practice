import java.util.*;  // For scanner

// Any additional classes can go here.

public class HornCore {

	// Variables, methods of your own can be added.
	int j = 0;
	int[][] last;
	int[] conclusion;
	int[] truth;
	int[] string;
	int[] count;
	int s;
	int k;
	int x;
	int y;
	int n;
	int line;
	String error;
	HornCore (Scanner in) {	
		String s1 = in.next();
		String s2 = in.next();
		n = in.nextInt()+1;
		line = in.nextInt()+1;
		last = new int[n][line];
		conclusion = new int[line];
		truth = new int[n];
		string = new int[n];
		count = new int[n];
		s = 0;
		error = "0";
		for(int i = 0; i < n; i++){
			truth[i] = 0;
		}
		for(int i = 0; i < n; i++){
			string[i] = 0;
		}
		for(int i = 1; i < line; i++){
			int number1 = 0; 
			k = 0;
			while((x = in.nextInt()) != 0){
				if(x < 0)
				{
					y = (-1) * x;
					k++;
					if(y > n-1)
						break;
					last[y][j] = i;										
				}
				if(x > 0){
					number1++;
					conclusion[j] = x;
					y = x;
				}
			}
			if(number1 == 0){
				if(y > n-1)
					break;
				if(truth[y] == 1)
					error = "-1";
			} 

			j++;
			count[i] = k;

			if(k == 0 && truth[y] == 0){
				truth[y] = 1;
				string[s] = y;
				s++;
			}

		}

		while(s != 0){
			s = s - 1;
			
			int p = string[s];
			for(int i = 0; i < line; i++){
				int c = last[p][i];
				if(c != 0){
					count[c]--;
					if(count[c] == 0){
						p = conclusion[c];
						if(truth[p] == 0){
							truth[p] = 1;
							string[s] = p;
							s = s + 1; 
						}

					}
				}

			}

		}

	}

	public String getCore() {
		if(error.equals("-1")){
			return "UNSAT";
		}
		String str = "";
		int i = 0;
		while(string[i] != 0){
			str = str + " " + Integer.toString(string[i]);
			i++;
		}
		if(i == 0)
			return "Empty";
		return str;
	}

	// MAIN CAN BE OMITTED.
	public static void main ( String[] none ) {
		Scanner in = new Scanner( System.in );
   		HornCore hc1 = new HornCore( in );
   		System.out.println( "Core = "+hc1.getCore() );
   		HornCore hc2 = new HornCore( in );
   		System.out.println( "Core = "+hc2.getCore() );
	}


}
