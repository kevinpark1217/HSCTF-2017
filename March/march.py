from scipy.spatial import ConvexHull

pts=open('march.txt').read()
pts=pts.split('\n')
pts=[x.split() for x in pts]
pts=[[int(a) for a in x] for x in pts]

hull=ConvexHull(pts)
prod=1
base=10**9+7
for x in hull.vertices:
    prod*=hull.points[x][0]*hull.points[x][1]
    prod%=base

print(prod)
