import sys

f1 = open(sys.argv[1],'r')
f2 = open(sys.argv[2],'r')


act_pos = 0
act_neg = 0
act_spam = 0
act_ham = 0

match_pos = 0
match_neg = 0
total_pos = 0
total_neg = 0

match_spam = 0
match_ham = 0
total_spam = 0
total_ham = 0

flag = 0

for line in f1:
	if 'POSITIVE' in line:
		flag = 1
		act_pos = act_pos + 1
		word = f2.readline()
		if 'POSITIVE' in word:
			match_pos = match_pos+1
			total_pos = total_pos +1
		else:
			total_neg = total_neg +1
	elif 'NEGATIVE' in line:
		act_neg = act_neg + 1
		word = f2.readline()
		if 'NEGATIVE' in word:
			match_neg = match_neg+1
			total_neg = total_neg +1
		else:
			total_pos = total_pos +1
	elif 'SPAM' in line:
		flag = 2
		act_spam = act_spam + 1
		word = f2.readline()
		if 'SPAM' in word:
			match_spam = match_spam+1
			total_spam = total_spam +1
		else:
			total_ham = total_ham +1
	elif 'HAM' in line:
		act_ham = act_ham + 1
		word = f2.readline()
		if 'HAM' in word:
			match_ham = match_ham+1
			total_ham = total_ham +1
		else:
			total_spam = total_spam +1

if flag == 1:
	pres_pos = match_pos/total_pos
	pres_neg = match_neg/total_neg
	recall_pos = match_pos/act_pos
	recall_neg = match_neg/act_neg
	f1_pos = (2*pres_pos*recall_pos)/(pres_pos+recall_pos)
	f1_neg = (2*pres_neg*recall_neg)/(pres_neg+recall_neg)
	
	print('POSITIVE precision: ',pres_pos)
	print('POSITIVE recall: ',recall_pos)
	print('POSITIVE F1 score: ',f1_pos)

	print('NEGATIVE precision: ',pres_neg)
	print('NEGATIVE recall: ',recall_neg)
	print('NEGATIVE F1 score: ',f1_neg)


if flag == 2:
	pres_spam = match_spam/total_spam
	pres_ham = match_ham/total_ham
	recall_spam = match_spam/act_spam
	recall_ham = match_ham/act_ham
	f1_spam = (2*pres_spam*recall_spam)/(pres_spam+recall_spam)
	f1_ham = (2*pres_ham*recall_ham)/(pres_ham+recall_ham)
	print('SPAM precision: ',pres_spam)
	print('SPAM recall: ',recall_spam)
	print('SPAM F1 score: ',f1_spam)

	print('HAM precision: ',pres_ham)
	print('HAM recall: ',recall_ham)
	print('HAM F1 score: ',f1_ham)
