Implement Knuth's algorithms (or equivalent) for outputting information about an input BDD. 
The BDD is specified as a text file in the following format, where the first line specifies n, the number of variables, and s, the number of nodes in the BDD.
4 9
1 7 6
2 5 4
2 0 1
3 1 0
3 3 2
4 1 0
4 0 1
5 1 1
5 0 0

A simple run:
C:java BDD < bddKnuth8.txt
count = 8
max = 4 soln = 0 0 0 1
listing:
0000
0001
0100
0111
1100
1101
1110
1111
polynomial: 1 2 1 3 1
