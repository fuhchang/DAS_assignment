import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class auctionServer {
 static int port = 1099;
 
  public auctionServer(){
	  try{
		  Runtime.getRuntime().exec("rmiregistry 1099");
		  auctionServant servant = new auctionServant();
		  rmi_method rmi = (rmi_method) UnicastRemoteObject.exportObject(servant, 0);
		  Naming.rebind("rmi://localhost:"+port+"/AuctionService", rmi);
         System.out.println("starting server");
	  }catch(Exception e){
		  System.out.println("Server Error: " + e);
	  }
  }
  
  public static void main(String args[]){
	  new auctionServer();
  }
}
