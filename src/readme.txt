How to run test performance
1.	Open 3 terminals and locate all the java file
2.	In the first terminal enter rmiregistry(linux or mac) start rmiregistry(window) to start the rmiregistry.
3.	In the 2nd terminal enter javac *.java to complie all the java file and enter java auctionServer to start the server.
4.	In the last terminal, enter java performanceTest to test the performance on the server.

How to run the system
1.	Open 4 terminals and locate all the java file
2.	In the first terminal enter rmiregistry(linux or mac) start rmiregistry(window) to start the rmiregistry.
3.	In the 2nd terminal enter javac *.java to complie all the java file 
4.  Enter java auctionServer to start the server.
5. 	In the 3rd terminal enter java auctionClient
6.  Enter your name follow by choosing option 1
7.  Enter item name
8.  Enter Value for the starting bid
9.  Enter duration(millisec for testing purpose) for the bid.
10. In the 4th terminal enter java auctionClient
11. Enter your name
12. Choosing option 3 to view the list of available bidding and once you decide the item to bid. 
13. Choose option 2
14. Enter the name of item you wish to bid
15. Enter the value you wish to bid
16. If your bid is higher than current value the system will inform you have successfull when you entered.