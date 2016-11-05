import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class auctionItem implements Serializable {
  String name;
  private double minimumItemValue;
  private long closeTime;
  private long createdTime;
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
	  try {
		  createdTime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(df.format(date)).getTime() / 1000;
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	this.closeTime = closeTime + createdTime;
}

public boolean checkBidClose(String name){
	boolean result = true;
	long currentTime = 0;
	DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	Date date = new Date();
	 try {
		 currentTime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(df.format(date)).getTime() / 1000;
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 if(currentTime > closeTime){
		 result = false;
	 }
	return result;
}
  
  
}
