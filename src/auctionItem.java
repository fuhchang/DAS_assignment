
public class auctionItem {
  int id;
  String name;
  double minimumItemValue;
  long closeTime;
  String owner;
  
  public auctionItem(){
	  this.name = "";
	  minimumItemValue = 0.0;
	  closeTime =0;
  }

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
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

public String getOwner() {
	return owner;
}

public void setOwner(String owner) {
	this.owner = owner;
}
  
  
}
