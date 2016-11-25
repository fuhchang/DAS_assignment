import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class auctionServer {
 static int port = 1099;
 static boolean exit = false;
  public auctionServer() throws RemoteException{
	  super();
  }
  
  public static void main(String args[]){
	  Scanner scan = new Scanner(System.in);
	  boolean result;
	 while(!exit){
		 try{
			  auctionServant servant = new auctionServant();
			  auctionItemInter rmi = (auctionItemInter) UnicastRemoteObject.exportObject(servant, 0);
			 
			  Naming.rebind("rmi://localhost:"+port+"/AuctionService", rmi);
	          System.out.println("starting server");
	          result = servant.loadState();
      		 if(result){
      			 System.out.println("successfully load state");
      		 }else{
      			 System.out.println("No state available");
      		 }
	        	 System.out.print("Choose option\n1) load state\n2) save state\n3) exit\nInput choice: ");
	        	 String choice = scan.nextLine();
	        	 switch(choice){
	        	  case "1":
	        		  result = servant.loadState();
	         		 if(result){
	         			 System.out.println("successfully load state");
	         		 }else{
	         			 System.out.println("failed load state");
	         		 }
	        		  break;
	        	  case "2":
	        		 result = servant.saveState();
	        		 if(result){
	        			 System.out.println("successfully saved state");
	        		 }else{
	        			 System.out.println("failed saved state");
	        		 }
	        	  break;
	        	  case "3":
	        		  exit = true;
	        		  result = servant.saveState();
		        		 if(result){
		        			 System.out.println("successfully saved state");
		        		 }else{
		        			 System.out.println("failed saved state");
		        		 }
		        		 System.exit(0);
	        		  break;
	        	  default:
	        		  System.out.println("Invalid option Please repick");
	        	 }
	       
		  }catch(Exception e){
			  System.out.println("Server Error: " + e);
		  }
	 }
	 
  }
}
