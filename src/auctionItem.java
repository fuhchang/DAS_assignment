import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class auctionItem implements Serializable {
  private String name;
  private String bidderName;
  private double minimumItemValue;
  private long closeTime;
  private long createdTime;
  private Set<String> bidderList = new HashSet<String>();
  public auctionItem(){
	  this.name = "";
	  this.minimumItemValue = 0.0;
	  this.closeTime =0;
  }
  public auctionItem(String name, double value , long close, long create){
	  this.name = name;
	  this.minimumItemValue = value;
	  this.closeTime = close;
	  this.createdTime = create;
  }


public long getCreatedTime() {
	return createdTime;
}



public void setCreatedTime(long createdTime) {
	this.createdTime = createdTime;
}



public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public double getMinimumItemValue() {
	return minimumItemValue;
}

public void setMinimumItemValue(double minimumItemValue) {
	this.minimumItemValue = minimumItemValue;
}

public long getCloseTime() {
	return closeTime;
}

public void setCloseTime(long closeTime) {
	 DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	  Date date = new Date();
	  this.closeTime = closeTime + System.currentTimeMillis() % 1000;
}

public boolean checkBidClose(String name){
	boolean result = true;
	long currentTime = 0;
	DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	Date date = new Date();
	 //currentTime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(df.format(date)).getTime() / 1000;
	 currentTime  = System.currentTimeMillis() % 1000;
	 if(currentTime > closeTime){
		 result = false;
	 }
	return result;
}
public String getBidderName() {
	return bidderName;
}
public void setBidderName(String bidderName) {
	this.bidderName = bidderName;
}
public Set<String> getBidderList() {
	return bidderList;
}
public void setBidderList(String name) {
	this.bidderList.add(name);
}
  
  
}
