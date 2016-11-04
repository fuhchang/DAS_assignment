
public class auctionItem {
  String name;
  double minimumItemValue;
  long closeTime;
  
  public auctionItem(){
	  this.name = "";
	  this.minimumItemValue = 0.0;
	  this.closeTime =0;
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
	this.closeTime = closeTime;
}


  
  
}
