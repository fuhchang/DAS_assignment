import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class auctionServant implements auctionItemInter  {
   static HashMap<String,auctionItem> itemHash = new HashMap<String, auctionItem>();
   private boolean result = false;
   private String state = "state.csv";
   private auctionClientServant RMIclient;
   @Override
	public boolean CreateItem(auctionItem item, String name) throws RemoteException {
		// TODO Auto-generated method stub
	   itemHash.put(item.getName(), item);
		if(itemHash.get(item.getName()) != null){
			itemHash.get(item.getName()).getBidderList().add(name);
		    result = true;
		}
		saveState();
		return result;
	}
    
	public auctionServant() throws RemoteException{
		super();
	}

	@Override
	public HashMap<String, auctionItem> getAuctionList() throws RemoteException {
		// TODO Auto-generated method stub
		return this.itemHash;
	}

	@Override
	public String bidItem(String item, double bidValue, String bidder) throws RemoteException {
		// TODO Auto-generated method stub
		String result = null;
		auctionItem aitem = itemHash.get(item);
		if(aitem.checkBidClose(item)){
			if(aitem.getMinimumItemValue() < bidValue){
				aitem.setMinimumItemValue(bidValue);
				aitem.setBidderName(bidder);
				result = "auction successfull";
				saveState();
			}else{
				result = "value too low";
			}
		}else{
			result ="auction has expired";
		}
		return result;
	}

	@Override
	public boolean saveState() throws RemoteException {
		// TODO Auto-generated method stub
      boolean result = false;
		try {
			FileWriter write = new FileWriter(state);
			String header =" , name, minValue, closeTime, createdTime";
			  write.append(header);
			  for(auctionItem item: itemHash.values()){
				  write.append("\n");
				  write.append(" ");
				  write.append(",");
				  write.append(item.getName());
				  write.append(",");
				  write.append(Double.toString(item.getMinimumItemValue()));
				  write.append(",");
				  write.append(Long.toString(item.getCloseTime()));
				  write.append(",");
				  write.append(Long.toString(item.getCreatedTime()));
			  }
			  write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			result = true;
		}
		  
		return result;
	}

	@Override
	public boolean loadState() throws RemoteException {
		// TODO Auto-generated method stub
		itemHash.clear();
		boolean result = false;
		String line = "";
		try {
			BufferedReader buff = new BufferedReader(new FileReader(state));
			buff.readLine();
			while((line = buff.readLine()) != null){
				String [] item = line.split(",");
				itemHash.put(item[1], new auctionItem(item[1],Double.parseDouble(item[2]), Long.parseLong(item[3]), Long.parseLong(item[4])));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			result = true;
		}
		return result;
	}

	@Override
	public boolean checkExist(String item) throws RemoteException {
		return itemHash.containsKey(item);
	}

	@Override
	public void registerClient(auctionClientServant client, String name, String item) throws RemoteException {
		// TODO Auto-generated method stub

		    SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			try {
		
				Date date = f.parse(f.format(new Date()));
				long millis = date.getTime();
			    long timeToCall = itemHash.get(item).getCloseTime() - millis;
			    System.out.println("Current time in millis: " + f.format(millis) + " close time: " + f.format(itemHash.get(item).getCloseTime()));
			    try {
					Thread.sleep(timeToCall);
					RMIclient = client;
					System.out.println("waiting for thread to start!!");
					System.out.println();
					System.out.format("informing client %s Winner of the Bid for the item %s\n", itemHash.get(item).getBidderName(), item);
			   		try {
			   			RMIclient.callBack("Congrats " + itemHash.get(item).getBidderName() + " for winning the bid item " + item);
			   		} catch(RemoteException e) {
			         e.printStackTrace();
			   		}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    
					
			
	}

	@Override
	public void serverCheck(auctionClientServant client) throws RemoteException {
		// TODO Auto-generated method stub
		//RMIclient = client;
		//RMIclient.checkAlive("Server is alive");
	}

   

}
