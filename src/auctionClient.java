import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class auctionClient {
  static Scanner scan = new Scanner(System.in);
  static auctionItem item = new auctionItem();
	public static void main(String args[]){
		Boolean quit = false;
		int port = 1099;
		String choice;
		auctionServant ser = null;
		
		try {
			
			ser = (auctionServant) Naming.lookup("rmi://localhost:"+port+"/auctionService");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			while(!quit){
				System.out.print("Choose option\n1) Create Auction Item\n2) Bid Item\n3) List Auction Items\n4) Save State\n5) Exit\nInput choice: ");
				choice = scan.nextLine();
				switch(choice){
				case "1": 
					item = startAuction();
					 try {
						 System.out.println(item.name+ " " + item.minimumItemValue + " " + item.closeTime);
						if(!ser.CreateItem(item)){
							 System.out.println("Starting auctioning Item!!!");
						 }else{
							 System.out.println("Fail to create youe item");
						 }
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "2":
					System.out.println("building service in process!!!");
					break;
				case "3":
					System.out.println("building service in process!!!");
					break;
				case "4":
					System.out.println("building service in process!!!");
					break;
				case "5":
					quit = true;
					break;
				default :
					System.out.println("incorrect input");
				}
			}
		}
		
	}
	
	public static auctionItem startAuction(){
		auctionItem item = new auctionItem();
		System.out.print("Please enter your item name:");
		String itemName = scan.nextLine();
		System.out.println();
		System.out.print("Please enter value of your item:");
		String value = scan.nextLine();
		System.out.println();
		System.out.print("Please enter Closing time:");
		String closingTime = scan.nextLine();
		item.setName(itemName);
		item.setMinimumItemValue(Double.parseDouble(value));
		item.setCloseTime(Long.parseLong(closingTime));
		return item;
	}
}
