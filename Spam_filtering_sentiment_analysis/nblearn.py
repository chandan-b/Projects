import sys

#example: python3 nblearn.py train75 sentiment.nb.model75

f = open(sys.argv[1],'r')

v_pos = {}
v_neg = {}
v_spam = {}
v_ham = {}
v = {}
flag =0
pcount = 0
ncount = 0
scount = 0
hcount = 0

# read the input file into a Dictionary
for line in f:
	for word in line.split():
		if word == 'POSITIVE':
			flag = 1
			pcount = pcount+1
		elif word == 'NEGATIVE':
			flag = 2
			ncount = ncount+1
		elif word == 'SPAM':
			flag = 3
			scount = scount+1
		elif word == 'HAM':
			flag = 4
			hcount = hcount+1
		elif flag == 1:
			x = word.split(':')
			if x[0] in v_pos:
				v_pos[x[0]] = v_pos[x[0]]+int(x[1])
			else:
				v_pos[x[0]] = int(x[1])
				v[x[0]] = int(x[1])
		elif flag == 2:
			x = word.split(':')
			if x[0] in v_neg:
				v_neg[x[0]] = v_neg[x[0]]+int(x[1])
			else:
				v_neg[x[0]] = int(x[1])
				v[x[0]] = int(x[1])
		elif flag == 3:
			x = word.split(':')
			if x[0] in v_spam:
				v_spam[x[0]] = v_spam[x[0]]+int(x[1])
			else:
				v_spam[x[0]] = int(x[1])
				v[x[0]] = int(x[1])
		elif flag == 4:
			x = word.split(':')
			if x[0] in v_ham:
				v_ham[x[0]] = v_ham[x[0]]+int(x[1])
			else:
				v_ham[x[0]] = int(x[1])
				v[x[0]] = int(x[1])

v_poslen = len(v_pos)
v_neglen = len(v_neg)
v_spamlen = len(v_spam)
v_hamlen  = len(v_ham)
V = len(v)

count = 0

if (pcount!= 0) or (ncount != 0):
	prob_pos = pcount/(pcount+ncount)
	prob_neg = ncount/(pcount+ncount)
if (scount != 0) or (hcount != 0):
	prob_spam = scount/(scount+hcount)
	prob_ham = hcount/(scount+hcount)

total_pos = 0
total_neg = 0
total_spam = 0
total_ham = 0

f1 = open(sys.argv[2],'w')

f1.write('VOCABSIZE')
f1.write(':')
f1.write(str(V))
f1.write('\n')

if v_poslen != 0:
	for (key,val) in v_pos.items():
		total_pos = total_pos+ int(val)

	f1.write('TOTAL_POS')
	f1.write(':')
	f1.write(str(total_pos))
	f1.write('\n')

	for (key,val) in v_pos.items():
		v_pos[key] = (int(val)+1)/(total_pos+V)

if v_neglen != 0:
	for (key,val) in v_neg.items():
		total_neg = total_neg + int(val)

	f1.write('TOTAL_NEG')
	f1.write(':')
	f1.write(str(total_neg))
	f1.write('\n')		
	
	for (key,val) in v_neg.items():
		v_neg[key] = (int(val)+1)/(total_neg+V)

if v_spamlen != 0:
	for (key,val) in v_spam.items():
		total_spam = total_spam+ int(val)	

	f1.write('TOTAL_SPAM')
	f1.write(':')
	f1.write(str(total_spam))
	f1.write('\n')

	for (key,val) in v_spam.items():
		v_spam[key] = (int(val)+1)/(total_spam+V)

if v_hamlen != 0:
	for (key,val) in v_ham.items():
		total_ham = total_ham+ int(val)
	
	f1.write('TOTAL_HAM')
	f1.write(':')
	f1.write(str(total_ham))
	f1.write('\n')

	for (key,val) in v_ham.items():
		v_ham[key] = (int(val)+1)/(total_ham+V)

 
if v_poslen != 0:
	f1.write('PROBPOS')
	f1.write(':')
	f1.write(str(prob_pos))
	f1.write('\n')
	for (key,val) in v_pos.items():
		f1.write(str(key))
		f1.write(':')
		f1.write(str(val))
		f1.write(' ')
	f1.write('\n')

if v_neglen != 0:
	f1.write('PROBNEG')
	f1.write(':')
	f1.write(str(prob_neg))
	f1.write('\n')
	for (key,val) in v_neg.items():
		f1.write(str(key))
		f1.write(':')
		f1.write(str(val))
		f1.write(' ')
	f1.write('\n')


if v_spamlen != 0:
	f1.write('PROBSPAM')	
	f1.write(':')
	f1.write(str(prob_spam))
	f1.write('\n')
	for (key,val) in v_spam.items():
		f1.write(str(key))
		f1.write(':')
		f1.write(str(val))
		f1.write(' ')
	f1.write('\n')


if v_hamlen != 0:
	f1.write('PROBHAM')
	f1.write(':')
	f1.write(str(prob_ham))
	f1.write('\n')
	for (key,val) in v_ham.items():
		f1.write(str(key))
		f1.write(':')
		f1.write(str(val))
		f1.write(' ')
	f1.write('\n')

		
			
