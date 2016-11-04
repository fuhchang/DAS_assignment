import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;

public class auctionServer {
 static int port = 1099;
 
  public auctionServer(){
	  try{
		  
		  auctionServant servant = new auctionServant();
		  rmi_method rmi = (rmi_method) UnicastRemoteObject.exportObject(servant, 0); 
		  Naming.rebind("rmi://localhost:"+ port + "/Auction", rmi);
	  }catch(Exception e){
		  System.out.println("Server Error: " + e);
	  }
  }
  
  public static void main(String args[]){
	  new auctionServer();
  }
}
