
def gcd(a,b):
    if min(a,b)==0:
        return a+b
    return gcd(max(a,b)%min(a,b), min(a,b))

def prod(n):
    count=0
    for a in range(1,n):
        for b in range(1,n):
            top=a*b
            bottom=(n-a)*(n-b)
            g=gcd(top, bottom)
            s=top/g+bottom/g
            if n%s==0:
                #print(a,b,bottom/g*n/s)
                count+=1
    return count

def func(n):
    return 3*(n-1)**2+3*(n-1)+1
