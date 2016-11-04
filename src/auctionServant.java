import java.util.HashMap;

public class auctionServant implements rmi_method {
   static HashMap<String,auctionItem> itemHash = new HashMap<String, auctionItem>();
	@Override
	public boolean CreateItem(auctionItem item) {
		// TODO Auto-generated method stub
		boolean result = false;
		if(itemHash.put(item.name, item) != null){
			result = true;
		}
		return result;
	}
    
	public auctionServant(){
		
	}
}
