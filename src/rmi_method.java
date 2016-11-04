import java.rmi.RemoteException;

public interface rmi_method extends java.rmi.Remote {
 
	public boolean CreateItem(auctionItem item) throws RemoteException;
}
