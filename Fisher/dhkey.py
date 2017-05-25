def bruteLog(b, c, m):
    s = 1
    for i in range(m):
        s = (s * b) % m
        if s == c:
            return i + 1
    return -1

g = 987
p = 8911991767204557841

bpub = 1317032838957486192
apub = 731665363559374475

bsec = 1201905
asec = 1213832

s = pow(bpub,asec,p)
print(s)