import java.rmi.RemoteException;
import java.util.HashMap;

public interface auctionItemInter extends java.rmi.Remote {
 
	public boolean CreateItem(auctionItem item) throws RemoteException;
	
	public HashMap<String, auctionItem> getAuctionList() throws RemoteException;
	
	public String bidItem(String item, double bidValue) throws RemoteException;
    
	public boolean saveState() throws RemoteException;
	
	public boolean loadState() throws RemoteException;
}
