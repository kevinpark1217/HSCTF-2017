import sys

file = open("msg.txt", "r")
s = file.read()

n = int(s, 4)
s = hex(n)
s = s[2:-1]
print s

for i in range(0, len(s), 2):
  #sys.stdout.write(s[i:i+2])
  n = int(s[i:i+2], 16)
  sys.stdout.write(chr(n))

sys.stdout.flush()
print
