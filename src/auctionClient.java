import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class auctionClient extends UnicastRemoteObject implements auctionClientServant , Runnable, Serializable  {
  static Scanner scan = new Scanner(System.in);
  static auctionItem item = new auctionItem();
  static String bidder;
  static auctionItemInter servant;
  static boolean check;
  static int port = 1099;
  static HashMap<String,Thread> tList = new HashMap<String,Thread>();
  static HashMap<String, auctionItem> list = new HashMap<String, auctionItem>();
	public static void main(String args[]){
		System.out.print("Please enter your name: ");
		bidder = scan.nextLine();
		System.out.println();
		
		
		String choice;
		Boolean quit = false;
	
		try {
			servant = (auctionItemInter) Naming.lookup("rmi://localhost:"+port+"/AuctionService");
			
			while(!quit){
				Timer timer = new Timer();
				timer.schedule(new TimerTask(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
							try {
								servant.serverCheck(new auctionClient());
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								System.out.println("server is offline");
							}
						
					}
					
				},0, 5000);
				System.out.print("Choose option\n1) Create Auction Item\n2) Bid Item\n3) List Auction Items\n4) Exit\nInput choice: ");
				choice = scan.nextLine();
				switch(choice){
				case "1": 
					item = startAuction();
					 try {
						if(servant.CreateItem(item,bidder)){
							 System.out.println("Starting auctioning Item!!!");
							 try {
								  
									tList.put(item.getName(),new Thread(new auctionClient(item.getName())));
									tList.get(item.getName()).start();
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
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
				    if(servant.checkExist(itemName)){
				    	boolean checkValue = false;
				    	do{
				    		
						    System.out.print("Please enter your value: ");
						    String bid = scan.nextLine();
						    try {
						    	String result = servant.bidItem(itemName, Double.parseDouble(bid),bidder);
								if(result.equals("auction successfull")){
									item = servant.getAuctionList().get(itemName);
									System.out.println("bid succesfull");
									try {
										if(tList.get(itemName) == null){
											
											tList.put(item.getName(),new Thread(new auctionClient(item.getName())));
											tList.get(item.getName()).start();
										}
									} catch (RemoteException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									exit = false;
									checkValue= true;
								}else{
									if(result.equals("value too low")){
										System.out.print("bid failed. Do you wish to enter another value? (Yes or No): ");
										String option = scan.nextLine().toLowerCase();
										if(option.equals("no") || option.equals("n")){
											exit = false;
											checkValue= true;
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
								System.out.println("Invalid input type");
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				    	}while(!checkValue);
				    }else{
				    	System.out.println("item don't exist");
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
	
	public auctionClient() throws RemoteException{
		super();
	}
	public auctionClient(String itemName) throws RemoteException{
		super();
	}
	public static auctionItem startAuction(){
		boolean checkValue = false;
		boolean checkTime = false;
		double min = 0;
		long close =0;
		auctionItem item = new auctionItem();
		System.out.print("Please enter your item name:");
		String itemName = scan.nextLine();
		System.out.println();
		do{
		System.out.print("Please enter value of your item:");
		String value = scan.nextLine();
		System.out.println();
		try{
		min = Double.parseDouble(value);
		checkValue= true;
		}catch(NumberFormatException e){
			System.out.println("Invalid input type");
		}
		}while(!checkValue);
		do{
		System.out.print("Please enter Closing time:");
		String closingTime = scan.nextLine();
		System.out.println();
		try{
			close = Long.parseLong(closingTime);
			checkTime = true;
		}catch(NumberFormatException e){
			System.out.println("Invalid input type");
		}
		}while(!checkTime);
		item.setName(itemName);
		item.setMinimumItemValue(min);
		item.setCloseTime(close);
		item.setBidderName(bidder);
		return item;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
				servant.registerClient(this, bidder, item.getName());
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void callBack(String s) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(s);
		
	}

	@Override
	public void checkAlive(String result) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(result);
	}
}
