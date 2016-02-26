#Chandan - Script to split the data into 75% and 25% for development purpose
#python3 input_svm.py inputDataSVM train_svm75 train_svm25 test_svm75 test_svm25 res_svm75 res_svm25

import sys
f = open(sys.argv[1], 'r')

count = 25000
sp = int(0.75*count/2)
tp = int(0.25*count/2)
half = int(count/2)
count_pos = 0
count_neg = half
sp_neg = half+sp

print('seventyfive %', sp)
print('twentyfive %', tp)
print(half)


f1 = open(sys.argv[2], 'w')
f2 = open(sys.argv[3], 'w')
f3 = open(sys.argv[4], 'w')
f4 = open(sys.argv[5], 'w')
f5 = open(sys.argv[6], 'w')
f6 = open(sys.argv[7], 'w')
flag = 0
for line in f:
	if count_pos < sp:
		f1.write(line)	
		for word in line.split():
			if word != '+1':
				f3.write(word)
				f3.write(' ')
			elif word == '+1':
				f5.write(word)
				f5.write('\n')
		f3.write('\n')		
		count_pos = count_pos+1
	elif (count_pos >= sp) & (count_pos <half):
		f2.write(line)
		for word in line.split():
			if word != '+1':
				f4.write(word)
				f4.write(' ')
			elif word == '+1':
				f6.write(word)
				f6.write('\n')
		f4.write('\n')		
		count_pos = count_pos+1
	elif (count_neg == half) or (count_neg < sp_neg):
		f1.write(line)
		for word in line.split():
			if word != '-1':
				f3.write(word)
				f3.write(' ')
			elif word == '-1':
				f5.write(word)
				f5.write('\n')
		f3.write('\n')		
		count_neg = count_neg+1
	elif (count_neg >= sp_neg) & (count_neg <= count):
		f2.write(line)
		for word in line.split():
			if word != '-1':
				f4.write(word)
				f4.write(' ')
			elif word == '-1':
				f6.write(word)
				f6.write('\n')
		f4.write('\n')
		count_neg = count_neg+1


	

