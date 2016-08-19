t = input()
while t > 0 :
	a, b = map(int, raw_input().split())
	if a == 1 and b == 1 :
		print "No"
	else :
		if a % 2 == 0 or b % 2 == 0 :
			print "Yes"
		else :
			print "No"				
	t -= 1