import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Scanner;

public class auctionClient {
  static Scanner scan = new Scanner(System.in);
  static auctionItem item = new auctionItem();
  static HashMap<String, auctionItem> list = new HashMap<String, auctionItem>();
	public static void main(String args[]){
		Boolean quit = false;
		int port = 1099;
		String choice;
		try {
			rmi_method servant = (rmi_method) Naming.lookup("rmi://localhost:"+port+"/AuctionService");
			while(!quit){
				System.out.print("Choose option\n1) Create Auction Item\n2) Bid Item\n3) List Auction Items\n4) Exit\nInput choice: ");
				choice = scan.nextLine();
				switch(choice){
				case "1": 
					item = startAuction();
					 try {
						if(servant.CreateItem(item)){
							 System.out.println("Starting auctioning Item!!!");
						 }else{
							 System.out.println("Failed to create item");
						 }
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "2":
					boolean exit = true;
					do{
				    System.out.print("Please enter item name: ");
				    String itemName = scan.nextLine();
				    System.out.println();
				    System.out.print("Please enter your value: ");
				    String bid = scan.nextLine();
				    try {
				    	String result = servant.bidItem(itemName, Double.parseDouble(bid));
						if(result.equals("auction successfull")){
							System.out.println("bid succesfull");
							exit = false;
						}else{
							if(result.equals("value too low")){
								System.out.print("bid failed. Do you wish to enter another value? (Yes or No): ");
								String option = scan.nextLine().toLowerCase();
								if(option.equals("no") || option.equals("n")){
									exit = false;
								}else if(option.equals("yes") || option.equals("y")){
									
								}else{
									System.out.println("invalid input back to select bid item");
								}
							}else if(result.equals("auction has expired")){
								System.out.println(result);
							}
						}
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}while(exit);
					break;
				case "3":
					try {
						list = servant.getAuctionList();
						if(list.isEmpty()){
							System.out.println("No auction avaliable");
						}
						for(String item : list.keySet()){
							if(!list.get(item).checkBidClose(item)){
								System.out.println("Auction Item "+ item +" expired");
							}else{
								System.out.println("Item:"+item);
							}
							
						}
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "4":
					System.out.println("goodbye!!!");
					quit = true;
					break;
				default :
					System.out.println("incorrect input");
				}
			}
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
