import java.io.FileWriter;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class auctionServer {
 static int port = 1099;

  public auctionServer(){
	  Scanner scan = new Scanner(System.in);
	  boolean result;
	  try{
		  auctionServant servant = new auctionServant();
		  rmi_method rmi = (rmi_method) UnicastRemoteObject.exportObject(servant, 0);
		  Naming.rebind("rmi://localhost:"+port+"/AuctionService", rmi);
         System.out.println("starting server");
         do{
        	 System.out.print("Choose option\n1) load state\n2) save state\nInput choice: ");
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
        	  
        	  default:
        		  System.out.println("Invalid option Please repick");
        	 }
         }while(true);
	  }catch(Exception e){
		  System.out.println("Server Error: " + e);
	  }
  }
  
  public static void main(String args[]){
	  new auctionServer();
  }
}
