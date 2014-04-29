LiwcExStarParser.java is for getting rid of all star from original LIWC catalogue.
LiwcToCatalogue.java include two functions of generating Hashmap of LIWC and Big 5 grade.
WriteMultiTimeline.java is for twitterAPI to get tweets.
Run.java include tokenize and run my program.

Step of using.
1. use LiwcExStarParser.java on mapFromLiwcToFive.txt to get LIWC2001_English_noStar.txt.
2. use WriteMultiTimeline.java to get tweets.
3. use Run.java with mapFromLiwcToFive.txt and LIWC2001_English_noStar.txt to get outcome.
