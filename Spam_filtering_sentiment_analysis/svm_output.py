import sys

f = open(sys.argv[1],'r')
f1 = open(sys.argv[2],'w')

for line in f:
	if float(line) > 0:
		f1.write('SPAM')
		f1.write('\n')
	elif float(line) < 0:
		f1.write('HAM')
		f1.write('\n')
	
