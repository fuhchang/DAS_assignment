import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class auctionClient {
  static Scanner scan = new Scanner(System.in);
  
	public static void main(String args[]){
		String host = "localhost";
		Boolean quit = false;
		int port = 1099;
		String choice;
		try {
			auctionServant ser = (auctionServant) Naming.lookup("rmi://"+host+":"+port+"/auctionService");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			auctionItem item = new auctionItem();
			while(!quit){
				System.out.print("Choose option\n1) Create Auction Item\n2) Bid Item\n3) List Auction Items\n4) Save State\n5) Exit\nInput choice: ");
				choice = scan.next();
				switch(choice){
				case "1": 
					item = startAuction();
					
				}
			}
		}
		
	}
	
	public static auctionItem startAuction(){
		auctionItem item = new auctionItem();
		System.out.print("Please enter your item name:");
		String itemName = scan.next();
		System.out.println();
		System.out.print("Please enter value of your item:");
		String value = scan.next();
		System.out.println();
		System.out.print("Please enter Closing time:");
		String closingTime = scan.next();
		item.setName(itemName);
		item.setMinimumItemValue(Double.parseDouble(value));
		item.setCloseTime(Long.parseLong(closingTime));
		return item;
	}
}
