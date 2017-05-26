from sympy.ntheory import factorint as fact
from sympy import sieve

#does the calculation

primes={}
m=37030203
n=1299721
count=0
for x in sieve.primerange(1, m):
    count+=1
    if count%1000==0:
        print(count)
    primes[x]=False

for x in range(m-n+1, m+1):
    if x%10000==0:
        print(x)
    f=fact(x)
    for p in f.keys():  
        primes[p]=True

total=0
for x in primes:
    if primes[x]:
        total+=x
        
print(total)
