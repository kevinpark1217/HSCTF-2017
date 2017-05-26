from PIL import Image

orig = Image.open("doge.png")
origPix = orig.load()
print origPix[0,0]

edited = Image.open("edited.png")
editedPix = edited.load()
print editedPix[0,0]

text=""
isStop = False
for x in range(0,orig.size[0]):
    for y in range(0,orig.size[1]):
        r = editedPix[x,y][0] - origPix[x,y][0]
        g = editedPix[x,y][1] - origPix[x,y][1]
        b = editedPix[x,y][2] - origPix[x,y][2]
        diff = str(r)+str(g)+str(b)
        num = int(diff)
        if num > 255 or num < 0:
            break
        text += chr(num)
    if isStop:
        break

print text+'\n'

start = text.index("The flag is")
print text[start:start+45]
