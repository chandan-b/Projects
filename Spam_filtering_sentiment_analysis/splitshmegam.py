#Chandan - Script to split the data into 75% and 25% for development purpose
#python3 splitshmegam.py emailDataSVM trainsh_megam75 trainsh_megam25 testsh_megam75 testsh_megam25 ressh_megam75 ressh_megam25

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
			if (word == '+1') or (word == '-1'):
				if word == '+1':
					f5.write('1')
				else:
					f5.write('0')
				f5.write('\n')
			else: 
				f3.write(word)
				f3.write(' ')
		f3.write('\n')	
		count_pos = count_pos+1
	elif (count_pos >= 16653) and (count_pos <=22204):
		f2.write(line)	
		for word in line.split():
			if (word == '+1') or (word == '-1'):
				if word == '+1':
					f5.write('1')
				else:
					f5.write('0')
				f5.write('\n')
			else:
				f4.write(word)
				f4.write(' ')
		f4.write('\n')	
		count_pos = count_pos+1


	

