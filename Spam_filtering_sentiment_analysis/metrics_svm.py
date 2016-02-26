import sys

f1 = open(sys.argv[1],'r')
f2 = open(sys.argv[2],'r')


act_pos = 0
act_neg = 0

match_pos = 0
match_neg = 0
total_pos = 0
total_neg = 0

flag = 0

for line in f1:
	if '+1' in line:
		act_pos = act_pos + 1
		word = f2.readline()
		if '+1' in word:
			match_pos = match_pos+1
			total_pos = total_pos +1
		else:
			total_neg = total_neg +1
	elif '-1' in line:
		act_neg = act_neg + 1
		word = f2.readline()
		if '-1' in word:
			match_neg = match_neg+1
			total_neg = total_neg +1
		else:
			total_pos = total_pos +1

pres_pos = match_pos/total_pos
pres_neg = match_neg/total_neg
recall_pos = match_pos/act_pos
recall_neg = match_neg/act_neg
print('POSITIVE precision: ',pres_pos)
print('POSITIVE recall: ',recall_pos)
print('NEGATIVE precision: ',pres_neg)
print('NEGATIVE recall: ',recall_neg)



f1_pos = (2*pres_pos*recall_pos)/(pres_pos+recall_pos)
f1_neg = (2*pres_neg*recall_neg)/(pres_neg+recall_neg)
	

print('POSITIVE F1 score: ',f1_pos)

print('NEGATIVE F1 score: ',f1_neg)
