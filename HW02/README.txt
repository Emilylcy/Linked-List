Linked List 

For this homework, it asks to work for a social research institution assigned with the responsibility of examining the trends in baby names throughout the years in the United States. The institution's objective is to offer perspectives on the development of popular names and naming trends, serving multiple purposes including deciphering cultural changes, forecasting upcoming naming patterns, and guiding marketing approaches for products related to babies.
I implemented doubly linkedlist to store names of both genders in alphabetical order. The program allows people to look up a name and report the following:
Linked list index - just an integer indicating the position of the name in the alphabetical linked list
For each year:
rank - the rank of the name that year (for that gender)
number - the number of babies given that name that year (for that gender)
percentage - the percentage of babies given that name that year (for that gender)
Total:
rank - the rank of the name among all years (for that gender)
number - the number of babies given that name among all years (for that gender)
percentage - the percentage of babies given that name among all years (for that gender)
For example, for the name “Mary” (female), the following statistics should be printed for all 28 files:
1424

1990
Mary: 35, 8666, 0.005432

1991
Mary: 38, 8760, 0.005596

...

2017
Mary: 126, 2381, 0.001877

Total
Mary: 51, 142630, 0.003630

It also allows searching for multiple names, or one name and searching for multiple files, or just one file. For example, below should all work:
java DriverHW02 -f Dianna -m Adam names1990.csv names2000.csv
java DriverHW02 -f Dianna -f Aline -f Eliz names1990.csv names2000.csv 
java DriverHW02 -f Dianna names2000.csv names2001.csv names2002.csv names2003.csv names2004.csv
