def gcd(a,b):
    if a*b==0:
        return a+b
    return gcd(min(a,b), max(a,b)%min(a,b))

def product(l):
    p=1
    for x in l:
        p*=x
    return p

def partition(size, total):
    if size==1:
        yield [total]
        return
    for x in range(0,total+1):
        for y in partition(size-1,total-x):
            yield [x]+y
    

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
    g=gcd(total,count)
    print(total//g,count//g)
    print(total/count)
    print(M/N)

# 1,2 -> 2/1 -> 3
