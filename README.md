The Android App for DTU Times!

The structure for the app is as follows:
	There are going to be 5 Fragments attached to the main activity:
		- FacebookFeedFragment (Created, slightly bugged)
		- OurTeamFragment (Created, non-functional_
		- EditionsFragment (not created)
		- ContactUsFragment (not created)
		- BlogFragment (not created)
	other than that, the main launching activity is FbLoginActivity which displays FbLoginFragment, which is needed to sign in via facebook
	
	The main jobs of the fragments are as follows-
	1- FacebookFeedFragment-
		This will display all the facebook feed coming from www.facebook.com/dtutimes. This includes photos, status' and links.
		As of now, it is being used with the cardslib library from gabrielemariotti (https://github.com/gabrielemariotti/cardslib).
	
	2- OurTeamFragment-
		This will display all the team members of DTU Times. The data will be pulled in the form of a JSONObject from a server, and will be parsed to have the following data:
			- Image URL
			- Name
			- Post
		
		If anyone can either create a php interface for the same, or attach it to a BaaS(Backend as a service) platform, it will be very much appreciated.
	
	3- EditionsFragment-
		This will have links to download the various editions of DTU Times, the data of which will also be recieved in JSON form, with the following data:
			- PDF Download Link
			- Edition Number
			- Image Thumbnail
		
		Php interface/ BaaS is needed for this as well. There is a preference if this can be implemented with GridView
	
	4- ContactUsFragment-
		Similar to OurTeamFragment and EditionsFragment, except that the JSON object will have the following data:
			- Name
			- Contact Number
			- Email Address
			- Post
			- Image URL
			
	5- BlogFragment-
		This Fragment will host a webview which will simply open http://dtutimes.wordpress.com/ and show it.
		
		
		
	Any further queries regarding the structure and requirements can be mailed to me at dashu2410@gmail.com
	
	
	Thanks for taking the time to read through the repository! :)
