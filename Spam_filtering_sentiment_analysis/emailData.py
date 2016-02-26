import sys
import os, os.path

f = open(sys.argv[1],'r', encoding = 'latin1')
fout = open(sys.argv[2],'w', encoding = 'latin1')
dic = {}
count = 1
spam = {}
ham = {}
 
for line in f:
	dic[line.rstrip()] = count
	count = count+1

for root, dirs, files in os.walk('/home/chandan/EData'):
	for f in files:
		fullpath = os.path.join(root, f)
		if 'spam' in fullpath:
			f1 = open(fullpath,'r',encoding = 'latin1')
			for line in f1:
				for word in line.split():
					#print(word)					
					if word in dic:
						line_no = str(dic.get(word));
						if line_no not in spam:
							spam[line_no] = 1
						else:						
							val = spam.get(line_no)	
							spam[line_no] = val+1
			fout.write('SPAM')
			fout.write(' ')
			for (key, val) in spam.items():
				fout.write(str(key))
				fout.write(':')
				fout.write(str(val))
				fout.write(' ')
			fout.write('\n')
			spam = {}
		elif 'ham' in fullpath:
			f1 = open(fullpath,'r',encoding = 'latin1')
			for line in f1:
				for word in line.split():
					#print(word)					
					if word in dic:
						line_no = str(dic.get(word));
						if line_no not in ham:
							ham[line_no] = 1
						else:						
							val = ham.get(line_no)	
							ham[line_no] = val+1
						
			fout.write('HAM')
			fout.write(' ')
			for (key, val) in ham.items():
				fout.write(str(key))
				fout.write(':')
				fout.write(str(val))
				fout.write(' ')
			fout.write('\n')
			ham = {}




					
				
