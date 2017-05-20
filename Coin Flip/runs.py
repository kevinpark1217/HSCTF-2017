import sys

file = open("results.txt", "r")
s = file.read()

lst = []

prev = s[0]
count = 1
for i in range(1, len(s)):
  if s[i] == prev:
    count += 1
  else:
    while count > len(lst):
      lst.append(0)
    #print count-1
    lst[count-1] += 1
    count = 1
    prev = s[i]
  #print c

for n in lst:
  sys.stdout.write(str(n) + ', ')
sys.stdout.write('\n')
sys.stdout.flush()
