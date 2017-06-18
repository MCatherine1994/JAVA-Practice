import java.util.Random;

public class MyMaze {

   private int[][] m;   // maze representation
   private int rows;    // number of rows in the maze
   private int cols;    // number of columns in the maze
   private final static byte[] TWO = { 1, 2, 4, 8, 16};
   private final static byte[] DX  = { 0,+1, 0,-1};
   private final static byte[] DY  = {-1, 0,+1, 0};
   private boolean done;  // used in finding a single solution.
   private long   count;  // used in finding the number of solutions.
   private Random r;      // for generating random integers.
   private int solutionCount;
   boolean[][] countSolutionVisited;
   boolean[][] visited;

   public int getRows() { return( rows ); }
   public int getCols() { return( cols ); }

   public MyMaze ( int nr, int nc, int seed ) {
      r = new Random( seed );
      rows = nr;  cols = nc;
      m = new int[nr+2][nc+2];
      for (int r=1; r<=nr; ++r )
         for (int c=1; c<=nc; ++c )
            m[r][c] = 15;
      for (int r=0; r<nr+2; ++r )
            m[r][0] = m[r][nc+1] = 16;
      for (int c=0; c<nc+2; ++c )
         m[0][c] = m[nr+1][c] = 16;
      Create( nr/2+1, nc/2+1, 0 );
   }

   // Wall in direction p?
   public boolean ok ( int x, int y, int p ) {
      return( (m[x][y] & TWO[p]) == TWO[p] );
   }

   private boolean downWall( int x, int y, int p ) {
      if (ok(x,y,p) && m[x+DX[p]][y+DY[p]] != 16) {
         m[x][y] ^= TWO[p];  m[x+DX[p]][y+DY[p]] ^= TWO[p^2];
         return true;
      }
      return false;
   }

   private void knockDown( int count ) {
      // Caution: make sure there are at least count walls!
      for (int i=0; i<count; ++i) {
         int x = 1+r.nextInt(rows);
         int y = 1+r.nextInt(cols);
         if (!downWall( x, y, r.nextInt(4))) --i;
      }
   }

   private void Create ( int x, int y, int val ) {
      int[] perm = randPerm( 4 );
      m[x][y] ^= val;
      for (int i=0; i<4; ++i) {
         int p = perm[i];
         if (m[x+DX[p]][y+DY[p]] == 15) {
            m[x][y] ^= TWO[p];
            Create( x+DX[p], y+DY[p], TWO[p^2] );
         }
      }
   }

   private int[] randPerm( int n ) {
      // This algorithm should look familiar!
      int[] perm = new int[n];
      for (int k=0; k<n; ++k) perm[k] = k;
      for (int k=n; k>0; --k) {
         int rand = r.nextInt(k);
         int t = perm[rand];  perm[rand] = perm[k-1];  perm[k-1] = t;
      }
      return( perm );
   }

   public String toString() {
      // FOR YOU TO FILL IN.  MUST FOLLOW CORRECT FORMAT.
      String s = "";
      for(int i = 1; i <= rows; i++){
         for(int j = 1; j <= cols; j++){     
            s = s + String.format("%2d",m[i][j]) + " ";
         }
         s += "\n";
      }
      return s;
   }

   public void solveMaze( int x0, int y0, int x1, int y1 ) {
      // FOR YOU TO CODE.
      visited = new boolean[rows+1][cols+1];
      solve(x0, y0, x1, y1);
   }

   public boolean solve(int x0, int y0, int x1, int y1){
      if(x0 == rows && y0 == cols){
         m[x0][y0] += 16;
         count++;
         return true;
      }else if(x0 > 0 && x0 <= x1 && y0 > 0 && y0 <= y1){
         if(!ok(x0,y0,3) && !visited[x0-1][y0]){
            //move up
            visited[x0][y0] = true;
            done = solve(x0-1,y0,x1,y1);
            if(done){
               m[x0][y0] += 16;
               return true;
            }
            visited[x0][y0] = false;
         }
         if(!ok(x0,y0,2) && !visited[x0][y0+1]){
            //move right
            visited[x0][y0] = true;
            done = solve(x0, y0+1,x1,y1);
            if(done){
               m[x0][y0] += 16;
               return true;
            }
            visited[x0][y0] = false;
         }
         if(!ok(x0,y0,1) && !visited[x0+1][y0]){
            //move left
            visited[x0][y0] = true;
            done = solve(x0+1, y0,x1,y1);
            if(done){
               m[x0][y0] += 16;
               return true;
            }
            visited[x0][y0] = false;
         }
         if(!ok(x0,y0,0) && !visited[x0][y0-1]){
            //move left
            visited[x0][y0] = true;
            done = solve(x0, y0-1,x1,y1);
            if(done){
               m[x0][y0] += 16;
               return true;
            }
            visited[x0][y0] = false;
         }
      }
      return false;
   }

   public long numSolutions( int x0, int y0, int x1, int y1 ) {
      // FOR YOU TO CODE.
      solutionCount = 0;
      countSolutionVisited = new boolean[rows+1][cols+1];
      countSolution(x0,y0,x1,y1);
      return solutionCount;
   }

   public void countSolution(int x0, int y0, int x1, int y1){
      if(x0 == rows && y0 == cols){
         m[x0][y0] += 16;
         solutionCount++;
      }else if(x0 > 0 && x0 <= x1 && y0 > 0 && y0 <= y1){
         if(!ok(x0,y0,3) && !countSolutionVisited[x0-1][y0]){
            //move up
            countSolutionVisited[x0][y0] = true;
            countSolution(x0-1,y0,x1,y1);
            countSolutionVisited[x0][y0] = false;
         }
         if(!ok(x0,y0,2) && !countSolutionVisited[x0][y0+1]){
            //move right
            countSolutionVisited[x0][y0] = true;
            countSolution(x0, y0+1,x1,y1);
            countSolutionVisited[x0][y0] = false;
         }
         if(!ok(x0,y0,1) && !countSolutionVisited[x0+1][y0]){
            //move left
            countSolutionVisited[x0][y0] = true;
            countSolution(x0+1, y0,x1,y1);
            countSolutionVisited[x0][y0] = false;
         }
         if(!ok(x0,y0,0) && !countSolutionVisited[x0][y0-1]){
            //move left
            countSolutionVisited[x0][y0] = true;
            countSolution(x0, y0-1,x1,y1);
            countSolutionVisited[x0][y0] = false;
         }
      }      
   }

   public static void main ( String[] args ) {
      int row = Integer.parseInt( args[0] );
      int col = Integer.parseInt( args[1] );
      int sx = Integer.parseInt( args[2] );
      int sy = Integer.parseInt( args[3] );
      int fx = Integer.parseInt( args[4] );
      int fy = Integer.parseInt( args[5] );
      MyMaze maz = new MyMaze( row, col, 9999 );
      System.out.print( maz );
      System.out.println( "Solutions = "+maz.numSolutions( sx, sy, fx, fy ) );
      maz.knockDown( (row+col)/4 );
      System.out.print( maz );
      System.out.println( "Solutions = "+maz.numSolutions( sx, sy, fx, fy ) );
      maz = new MyMaze( row, col, 9999 );  // creates the same maze anew.
      maz.solveMaze( sx, sy, fx, fy );
      System.out.print( maz );
   }
}