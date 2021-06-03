G(S,T,N,P)

T={'SWITCH' , '(' , ')' , 'CASE' , ':' , '}' , '{' } 
S= {switchCase}
N = {switchCase, caseBlock, atomicExpr, statement}

switchCase-> SWITCH(atomicExpr){ caseBlock }

caseBlock-> CASE atomicExpr: statement caseBlock | CASE atomicExpr: statement

atomicExpr-> 
statement-> 

FIRST(switchCase) = {SWITCH}
FIRST(caseBlock) = {CASE}
FOLLOW(switchCase) = {#}
FOLLOW(caseBlock) = { '}' }

SELECT(switchCase) = {SWITCH}
SELECT(caseBlock) = {CASE}



