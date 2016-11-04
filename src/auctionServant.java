import java.util.HashMap;

public class auctionServant implements rmi_method {
   static HashMap<String,auctionItem> itemHash = new HashMap<String, auctionItem>();
	@Override
	public boolean CreateItem(auctionItem item) throws java.rmi.RemoteException {
		// TODO Auto-generated method stub
		boolean result = false;
		itemHash.put(item.name, item);
		result = true;
		
		System.out.println(result);
		return result;
	}
    
	public auctionServant(){
		super();
	}
}
