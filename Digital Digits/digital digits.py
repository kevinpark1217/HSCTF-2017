data=open('digital digits.txt').read()
data=[x for x in data if x in '0123456789']
data=[int(x) for x in data]

track=[1,0,0,0,0,0,0,0,0]
copy =[0,0,0,0,0,0,0,0,0]

for x in data:
    for y in range(9):
        copy[(x+y)%9]+=track[y]
        copy[y]+=track[y]
    track=[x%(1000000003) for x in copy]
    copy=[0]*9
print(track[5])
