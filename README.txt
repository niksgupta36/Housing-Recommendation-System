Housing Recommendation System (HRS) v0.5
*************************************************************************************
SUMMARY
*************************************************************************************
New to town? Looking for a place to live?
Renting or buying a house/apartment must be hassle-free and transparent but it is often very confusing. 
There are many websites which display housing options and reviews, but it is very difficult to narrow down your search because 
Disparate websites list options based on different criteria. You end up scouring website after website looking for that perfect home. 
To address this problem, our solution will use multiple parameters like ZipCode, proximity to amenities, and bus stop locations to 
provide relevant recommendations. 
This is not just a solution for new residents, but also for companies looking to capture new markets and increase relevance in their 
business domain.
*************************************************************************************
CHANGES
*************************************************************************************
Version 0.1
- proof of architecture
Version 0.2
- alpha release 
- completed first pass of primary use case : Recommend apartments to the user 
- naive implementation of recommendations (filter the apartments based on user preferences)
- implemented the UI for user to enter his preferences
- included input validations for the zipcode 
Version 0.3
- beta release
- this build includes validations on zipcode field based on following conditions:
	1. The zipcode field cannot be blank
	2. Zipcode field should have only integers(0-9)
	3. The field should have 5 digits
If the above conditions are not met, a pop-up window will alert the user with the error message.
- refined and implemented the recommendations by adding 'advanced search' option  based on two criteria:
	1. Proximity to bus stop(within 2 miles)
	2. Distance from grocery stores(within 5 miles)
Version 0.4
- release candidate
- completed second use case - help users decide the type of businesses that are in demand in the chosen zipcode
- implemented a bar graph depicting the number of hospitals and restaurants of different types
- automated the transfer of data file from workspace to the import folder of home directory of Neo4j
- implemented login functionality for premium users
Version 0.5
- Final Release
- Added a price check option for advance Search 
- cleaned up code and refactored it
*************************************************************************************
SETUP
*************************************************************************************
1. Neo4j needs to be installed. The data will be loaded automatically once you run the project. 
This has been tested on Windows machines only for lack of MAC availability :) 
If the loading fails, please copy the file from the resources folder of the project to the import folder of Neo4j.
*************************************************************************************
OTHER NOTES
*************************************************************************************
1. The Apartment data file has details about the Apartments along with their corresponding area zip codes. Each zipcode is also associated with different restaurants and hospitals in that locality.  
2. To search apartments based on zipcode, enter 5-digit valid zip codes. You may try 76202, 74495, 75767, 73091. Top most suitable housing recommendations with their addresses will be displayed in the console.
3. Agencies, real estate owners or government bodies can search for business recommendations by entering valid zipcodes. The system will portray a bar graph depicting the relative count of different types of hospitals and restaurants in the area.
4. Use username as admin and password as admin for viewing the improvement suggestions
5. To view the CSV file containing the data, check the resources folder 
6. Code for importing is not tested for MAC but works well on Windows default Neo4j installation