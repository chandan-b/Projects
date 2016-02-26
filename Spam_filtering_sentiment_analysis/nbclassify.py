import sys
import math

# pyhton3 nbclassify.py sentiment.nb.out75 test25 sentiment.nb.out25


f = open(sys.argv[1],'r')

V = 0.0
model_pos = {}
model_neg = {}
model_spam = {}
model_ham = {}
res = []

flag = 0

for line in f:
	for word in line.split():
		v= word.split(':')
		if v[0] == 'VOCABSIZE':
			V = float(v[1])
		elif v[0] == 'TOTAL_POS':
			v_poslen = float(v[1])
		elif v[0] == 'TOTAL_NEG':
			v_neglen = float(v[1])
		elif v[0] == 'TOTAL_SPAM':
			v_spamlen = float(v[1])
		elif v[0] == 'TOTAL_HAM':
			v_hamlen = float(v[1])
		elif v[0] == 'PROBPOS':
			flag = 1
			probpos = math.log10(float(v[1]))
			smooth_pos = math.log10(1/(v_poslen+V))
		elif v[0] == 'PROBNEG':
			flag = 2
			probneg = math.log10(float(v[1]))
			smooth_neg = math.log10(1/(v_neglen+V))
		elif v[0] == 'PROBSPAM':
			flag = 3
			probspam = math.log10(float(v[1]))
			smooth_spam = math.log10(1/(v_spamlen+V))
		elif v[0] == 'PROBHAM':
			flag = 4
			probham = math.log10(float(v[1]))
			smooth_ham = math.log10(1/(v_hamlen+V))
		elif flag == 1:
			model_pos[v[0]] = float(v[1])
		elif flag == 2:
			model_neg[v[0]] = float(v[1])
		elif flag == 3:
			model_spam[v[0]] = float(v[1])
		elif flag == 4:
			model_ham[v[0]] = float(v[1])

f1 = open(sys.argv[2],'r')

if (flag == 1) or (flag == 2):
	prob_pos = probpos
	prob_neg = probneg	
	for line in f1:
		for word in line.split():
			x = word.split(':')
			if x[0] in model_pos:
				val = model_pos[x[0]]
				prob_pos = (float(x[1])*math.log10(val)) + prob_pos
			else:
				prob_pos = (float(x[1])*smooth_pos) + prob_pos

			if x[0] in model_neg:
				val = model_neg[x[0]]
				prob_neg = (float(x[1])*math.log10(val)) + prob_neg
			else:
				prob_neg = (float(x[1])*smooth_neg) + prob_neg
		if prob_pos > prob_neg:
			res.append('POSITIVE')
		elif prob_neg > prob_pos:
			res.append('NEGATIVE')
		elif prob_pos == prob_neg:
			res.append('NONDETERMINANT')
		prob_pos = probpos
		prob_neg = probneg 		

if (flag == 3) or (flag == 4):
	prob_spam = probspam
	prob_ham = probham
	for line in f1:
		for word in line.split():
			x = word.split(':')
			if x[0] in model_spam:
				val = model_spam[x[0]]
				prob_spam = (float(x[1])*math.log10(val)) + prob_spam
			else:
				prob_spam = (float(x[1])*smooth_spam) + prob_spam

			if x[0] in model_ham:
				val = model_ham[x[0]]
				prob_ham = (float(x[1])*math.log10(val)) + prob_ham
			else:
				prob_ham = (float(x[1])*smooth_ham) + prob_ham
		if prob_spam > prob_ham:
			res.append('SPAM')
		elif prob_ham > prob_spam:
			res.append('HAM')
		elif prob_spam == prob_ham:
			res.append('NONDETERMINANT')
		prob_spam = probspam
		prob_ham = probham 
	


for word in res:
	print(word)	


