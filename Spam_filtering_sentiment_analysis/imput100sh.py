#python3 input100sh.py emailData email100test email100res

import sys

f = open(sys.argv[1],'r')
f2 = open(sys.argv[2],'w')
f3 = open(sys.argv[3],'w')

for line in f:
	for word in line.split():
		if word == 'SPAM':
			f3.write(word)
			f3.write('\n')
		elif word == 'HAM':
			f3.write(word)
			f3.write('\n')
		elif (word != 'SPAM') and (word != 'HAM'):
			f2.write(word)
			f2.write(' ')
	f2.write('\n')
