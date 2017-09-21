#!/bin/bash

#Bash script to determine maximum number of commits that touched a particular file in the three month intervals from some date(START_DATE): Date at which forking took place
#Author: Ajiinkkya Bhaalerao

#Usage: ./changesInIntervals.sh <File containing file names for which the analysis is to be performed>. This script does not yet check if the proper file was provided as the input

#Initialize the variables
START_DATE=2009-08-30 #Date of first commit
#CURRENT_DATE=$(date +%Y-%m-%d) # Can be fixed, as # of changes will be 0 in the intervals after the repo was pulled
CURRENT_DATE=2017-04-04
END_DATE=$(date +%Y-%m-%d -d "$START_DATE + 3 months")

#Populate the result file with appropriate columns
printf "Commit Hash," >> Clustering_Results.csv
printf "File Name," >> Clustering_Results.csv
while [[ $END_DATE < $CURRENT_DATE ]]
do
	printf "$START_DATE - $END_DATE," >> Clustering_Results.csv
	START_DATE=$(date +%Y-%m-%d -d "$START_DATE + 1 months")
	END_DATE=$(date +%Y-%m-%d -d "$START_DATE + 3 months")
done
printf "\n" >> Clustering_Results.csv

#Do the analysis
#while IFS='' read -r line || [[ -n "$line" ]]
while read -r line || [[ -n "$line" ]]
do
	START_DATE=2009-08-30
	COMMIT_HASH=$(echo $line | awk '{print $1}') 
	FILE_NAME=$(echo $line | awk '{print $2}')
	echo "File: $FILE_NAME"
	START_DATE=2009-08-30 #Change according to the project
	CURRENT_DATE=$(date +%Y-%m-%d) #Change according to the project
	MAX_NO_OF_CHANGES=0
	if [ ! -f $FILE_NAME ]
	then
		printf "$FILE_NAME could not be found.\n"
		printf "$FILE_NAME\n" >> Clustering_Results_files_do_not_exist_pass14
	else
		printf "$COMMIT_HASH ," >> Clustering_Results.csv
		printf "$FILE_NAME ," >> Clustering_Results.csv
		END_DATE=$(date +%Y-%m-%d -d "$START_DATE + 3 months")
		while [[ $END_DATE < $CURRENT_DATE ]]
		do
			COMMITS=$(git log --follow  --pretty=format:%H --after="$START_DATE" --before="$END_DATE" $FILE_NAME)
			NO_OF_CHANGES=$(echo "$COMMITS" | wc -w)
			printf "$NO_OF_CHANGES\n"
			if [[ $NO_OF_CHANGES -gt $MAX_NO_OF_CHANGES ]]
			then
				MAX_NO_OF_CHANGES=$NO_OF_CHANGES
				printf "New MAX VALUE: $MAX_NO_OF_CHANGES\n"
				MAX_CHANGE_INTERVAL_START=$START_DATE
				MAX_CHANGE_INTERVAL_END=$END_DATE
			fi
			printf "$NO_OF_CHANGES ," >> Clustering_Results.csv
			START_DATE=$(date +%Y-%m-%d -d "$START_DATE + 1 months") #Increase the interval start date by one month
			END_DATE=$(date +%Y-%m-%d -d "$START_DATE + 3 months")
		
		done
		printf "$file,$MAX_NO_OF_CHANGES,$MAX_CHANGE_INTERVAL_START,$MAX_CHANGE_INTERVAL_END\n" >> Results.csv
		printf "\n" >> Clustering_Results.csv
	fi
done < $1
exit 0
