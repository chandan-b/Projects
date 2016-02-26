#python3 inputconv_megam100.py  emailDataSVM emailmegam emailmegamtest
#python3 inputconv_megam100.py trainsh_megam75 trainsh_mega75 testsh_mega75
#python3 inputconv_megam100.py trainsh_megam25 trainsh_mega25 testsh_mega25
import sys
f = open(sys.argv[1], 'r')

f1 = open(sys.argv[2], 'w')
f2 = open(sys.argv[3], 'w')
for line in f:
	for word in line.split():
		if word == '+1':
			f1.write('1')
			f1.write(' ')
			f2.write('1')
			f2.write('\n')
		elif word == '-1':
			f1.write('0')
			f1.write(' ')
			f2.write('0')
			f2.write('\n')
		else:
			x = word.split(':')
			f1.write(x[0])
			f1.write(' ')
			f1.write(x[1])
			f1.write(' ')
	f1.write('\n')
