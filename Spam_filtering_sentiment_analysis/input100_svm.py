#python3 input100.py inputDataSVM imdb100svmtest imdb100svmres

import sys

f = open(sys.argv[1],'r')
f2 = open(sys.argv[2],'w')
f3 = open(sys.argv[3],'w')

for line in f:
	for word in line.split():
		if word == '+1':
			f3.write(word)
			f3.write('\n')
		elif word == '-1':
			f3.write(word)
			f3.write('\n')
		elif (word != '+1') and (word != '-1'):
			f2.write(word)
			f2.write(' ')
	f2.write('\n')
