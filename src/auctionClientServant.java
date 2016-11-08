import java.rmi.*;
public interface auctionClientServant extends Remote{
	public void callBack(String s) throws java.rmi.RemoteException;
}
