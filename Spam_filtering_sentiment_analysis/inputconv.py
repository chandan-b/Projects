#Chandan - Script to convert the labels in labeledBow.feat to POSITIVE and NEGATIVE lables  

import sys

f = open(sys.argv[1], 'r')
a =[]
x = []
flag = 0
for line in f:
	for word in line.split():
		a.append(word.split(":"))

for list in a:
	if len(list) == 1:
		x = int(list.pop(0))
		if x >= 7:
			list.insert(0,'POSITIVE')			
		else:
			list.insert(0,'NEGATIVE')

f1 = open(sys.argv[2],'w')

for list in a:
	if len(list) == 1:
		x = str(list.pop(0))
		if flag == 1:
			f1.write('\n')
		flag = 1
		f1.write(x)
		f1.write(' ')
	else:
		f1.write(str(list.pop(0)))
		f1.write(':')
		f1.write(str(list.pop()))
		f1.write(' ')


