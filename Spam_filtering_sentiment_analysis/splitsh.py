#Chandan - Script to split the data into 75% and 25% for development purpose
#python3 splitsh.py emailData trainsh75 trainsh25 testsh75 testsh25 ressh75 ressh25

import sys
f = open(sys.argv[1], 'r')


count_pos = 0
flag = 0

f1 = open(sys.argv[2], 'w')
f2 = open(sys.argv[3], 'w')
f3 = open(sys.argv[4], 'w')
f4 = open(sys.argv[5], 'w')
f5 = open(sys.argv[6], 'w')
f6 = open(sys.argv[7], 'w')
flag = 0
for line in f:
	if count_pos < 16653:
		f1.write(line)	
		for word in line.split():
			if (word == 'HAM') or (word == 'SPAM'):
				f5.write(word)
				f5.write('\n')
			else: 
				f3.write(word)
				f3.write(' ')
		f3.write('\n')	
		count_pos = count_pos+1
	elif (count_pos >= 16653) and (count_pos <=22204):
		f2.write(line)	
		for word in line.split():
			if (word == 'HAM') or (word == 'SPAM'):
				f6.write(word)
				f6.write('\n')
			else:
				f4.write(word)
				f4.write(' ')
		f4.write('\n')	
		count_pos = count_pos+1


	

