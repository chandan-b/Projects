#python3 input100.py labeledBowPOSNEG.feat imdb100test imdb100res

import sys

f = open(sys.argv[1],'r')
f2 = open(sys.argv[2],'w')
f3 = open(sys.argv[3],'w')

for line in f:
	for word in line.split():
		if word == 'POSITIVE':
			f3.write(word)
			f3.write('\n')
		elif word == 'NEGATIVE':
			f3.write(word)
			f3.write('\n')
		elif (word != 'POSITIVE') and (word != 'NEGATIVE'):
			f2.write(word)
			f2.write(' ')
	f2.write('\n')
