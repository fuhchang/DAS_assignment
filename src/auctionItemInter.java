import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.HashMap;

public interface auctionItemInter extends java.rmi.Remote {
 
	public boolean CreateItem(auctionItem item, String name) throws RemoteException;
	
	public HashMap<String, auctionItem> getAuctionList() throws RemoteException;
	
	public String bidItem(String item, double bidValue,String bidder) throws RemoteException;
	
	public boolean checkExist(String item) throws RemoteException;
	
	public void registerClient( auctionClientServant client, String name, String item) throws RemoteException;
	
	public void serverCheck( auctionClientServant client) throws RemoteException;
    
	public boolean saveState() throws RemoteException;
	
	public boolean loadState() throws RemoteException;
}
