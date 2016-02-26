import sys

f = open(sys.argv[1],'r')
f1 = open(sys.argv[2],'w')

for line in f:
	word = line.split()
	if word[0] == '0':
		f1.write('HAM')
		f1.write('\n')
	elif word[0] == '1':
		f1.write('SPAM')
		f1.write('\n')
	
