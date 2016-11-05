import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
@SuppressWarnings("serial")
public class auctionServant extends UnicastRemoteObject implements rmi_method  {
   static HashMap<String,auctionItem> itemHash = new HashMap<String, auctionItem>();
   boolean result = false;
   @Override
	public boolean CreateItem(auctionItem item) throws RemoteException {
		// TODO Auto-generated method stub
		if(itemHash.put(item.name, item) != null){
		 result = true;
		}	
		return result;
	}
    
	public auctionServant() throws RemoteException{
	}

	@SuppressWarnings("static-access")
	@Override
	public HashMap<String, auctionItem> getAuctionList() throws RemoteException {
		// TODO Auto-generated method stub
		return this.itemHash;
	}

	@Override
	public String bidItem(String item, double bidValue) throws RemoteException {
		// TODO Auto-generated method stub
		String result = null;
		auctionItem aitem = itemHash.get(item);
		if(aitem.checkBidClose(item)){
			if(aitem.minimumItemValue < bidValue){
				aitem.minimumItemValue = bidValue;
				result = "auction successfull";
			}else{
				result = "value too low";
			}
		}else{
			result ="auction has expired";
		}
		return result;
	}

	@Override
	public String sayHello() throws RemoteException {
		// TODO Auto-generated method stub
		return "hello world";
	}

	@Override
	public boolean saveState() throws RemoteException {
		// TODO Auto-generated method stub
		
		return false;
	}
   

}
