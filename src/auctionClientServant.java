import java.rmi.*;
public interface auctionClientServant extends Remote{
	public void callBack(String s) throws java.rmi.RemoteException;
	
	public void checkAlive(String result) throws java.rmi.RemoteException;

}
