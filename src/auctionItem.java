
public class auctionItem {
	  String name;
	  String bidder;
	  int minItemValue;
	  long closingTime;
	  int id;
	  
	  public auctionItem(String name,String bidder,int minItemValue, long closingTime,int id){
		  this.name = name;
		  this.bidder = bidder;
		  this.minItemValue = minItemValue;
		  this.closingTime = closingTime;
		  this.id = id;
	  }
}
