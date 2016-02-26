from collections import namedtuple
import os, os.path
import sys
import csv
import random
import string

random_list = ['sd','b','sv','aa','ba','qy','x','ny','fc','b^r','sd^e','qw','bk','nn','qy^d','h','bh','bf','sd^t','aa^t','o','na']
len_rl = len(random_list)-1

for root, dirs, files in os.walk('/home/chandan/csci544_hw3_data/data/test'):
	for f in files:
		fullpath = os.path.join(root, f)
		with open(fullpath,'r') as csvfile:
			reader = csv.DictReader(csvfile)
			flag_sent = 1		
			prev_speaker = 'NAME'	
			for du_dict in reader:
				DialogUtterance = namedtuple(
				    "DialogUtterance", ("act_tag", "speaker", "pos", "text"))
				PosTag = namedtuple("PosTag", ("token", "pos"))

				for k, v in du_dict.items():
					if len(v.strip()) == 0:
					    du_dict[k] = None

				# Extract tokens and POS tags
				if du_dict["pos"]:
				    du_dict["pos"] = [
					PosTag(*token_pos_pair.split("/"))
					for token_pos_pair in du_dict["pos"].split()]
				DialogUtterance(**du_dict)

				dialouge_list = []
				list2 = []
				list_token =[]
				list_text = []
				list_pos =[]
				dict_token_pos = {}
				flag = 0
				for key,val in du_dict.items():
					if key == 'act_tag':
						r = random.randint(0, len_rl)
						dialouge_list.insert(0, random_list[r] )
					elif key == 'speaker':
						if prev_speaker == 'NAME':
							dialouge_list.insert(1,'_BOS_')
						elif prev_speaker != val:
							dialouge_list.insert(1,'change_in_speaker')
						prev_speaker = val
					elif key == 'pos':
						if val != None:
							for item in val:
								list2 = item
								if list2[1] != ':':
									list_token.append(list2[0])
									list_pos.append(list2[1])
						else:
							list_token.append(val)
							list_pos.append(val)		
			
		
				for elements in dialouge_list:
					if elements == '_BOS_':
						flag = 1
					else:	
						sys.stdout.write(str(elements))
						sys.stdout.write('\t')

				for item in list_token:
					s = 'token_'+str(item)
					sys.stdout.write(s)
					sys.stdout.write('\t')
				
				for item in list_pos:
					s = 'pos_'+str(item)
					sys.stdout.write(s)
					sys.stdout.write('\t')
				if flag == 1:
					sys.stdout.write('_BOS_'); flag = 0
				sys.stdout.write('\n')
			sys.stdout.write('\n')
