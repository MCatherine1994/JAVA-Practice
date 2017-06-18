Implements Knuth's Algorithm C for finding the core of a set of non-definite Horn clauses
The input format is the "DIMACS cnf" format，for example,
x1 ∧ x3 ∧ x5 ⇒ x2
x1 ∧ x4 ∧ x7 ⇒ x3
x2 ∧ x4 ∧ x7 ⇒ x1
             ⇒ x4
             ⇒ x7
             ⇒ x1
(with core {x1,x3,x4,x7})could be input as
p cnf 7 6
-1 -3 -5 2 0
-1 -4 -7 3 0
1 -2 -4 -7 0
4 0
7 0
1 0
The ouput shows a string with the variables in the core in increasing order or "UNSAT" if the clauses are unsatisfiable, or "EMPTY" if the core is empty

PS C:\CSC322> cat horn2.txt,horn3.txt | java HornCore
