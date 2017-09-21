#!/bin/bash

#Bash script to download Github Archives
#Author: Ajiinkkya Bhaalerao

#START_DATE=2011-07-02
#END_DATE=2015-04-02
START_DATE=2015-09-02
END_DATE=2017-04-02

CMD=wget

while [[ $START_DATE < $(date +%Y-%m-%d -d "$END_DATE + 1 days") ]]
do
	#printf "$START_DATE \n"
	name=( $(printf "http://data.githubarchive.org/$(date +%Y-%m-%d -d "$START_DATE")-%d.json.gz\n" {0..23} ) )
	START_DATE=$(date +%Y-%m-%d -d "$START_DATE + 1 days")

	for i in "${name[@]}"
	do
        	#printf "$CMD $i\n"
        	`$CMD $i`
		if [[ $? -ne 0 ]]
		then
			printf "$CMD $i" >> wget_failed.txt
		fi
	done
done
