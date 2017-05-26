from math import factorial as fact

#tries out different combinations

def gcd(a,b):
    if a*b==0:
        return a+b
    return gcd(min(a,b), max(a,b)%min(a,b))

def product(l):
    p=1
    for x in l:
        p*=x
    return p

def partition(size, count):
    if count==0:
        yield [0]*size
        return
    for x in partition(size, count-1):
        for y in range(size):
            x[y]+=1
            yield x
            x[y]-=1
    
def func(m, n):
    return fact(m)*(n**(m-n))/fact(m-n)

while True:
    line=input('Data: ').split(',')
    N=int(line[0])
    M=int(line[1])

    count=0
    total=0
    for x in partition(N,M):
        #print(x)
        count+=1
        total+=product(x)
    g=1#gcd(total,count)
    print(total//g,count//g)
    print(func(M,N))
    print(N**M)
    #print(total/count)
    #print(M/N)

# 1,2 -> 2/1 -> 3
