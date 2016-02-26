import sys

f = open(sys.argv[1],'r')
f2 = open(sys.argv[2],'w')

for line in f:
	if '+1' in line:
		f2.write('+1')
	elif '-1' in line:
		f2.write('-1')
	f2.write('\n')		
