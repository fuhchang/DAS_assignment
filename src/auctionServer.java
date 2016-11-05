import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class auctionServer {
 static int port = 1099;
 
  public auctionServer(){
	  try{
		  auctionServant servant = new auctionServant();
		  Naming.rebind("rmi://localhost:"+port+"/AuctionService", servant);
	  }catch(Exception e){
		  System.out.println("Server Error: " + e);
	  }
  }
  
  public static void main(String args[]){
	  new auctionServer();
  }
}
