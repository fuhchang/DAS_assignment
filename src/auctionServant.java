import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class auctionServant implements auctionItemInter  {
   static HashMap<String,auctionItem> itemHash = new HashMap<String, auctionItem>();
   private boolean result = false;
   private String state = "state.csv";
   private auctionClientServant RMIclient;
   @Override
	public synchronized boolean CreateItem(auctionItem item, String name) throws RemoteException {
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
	public synchronized String bidItem(String item, double bidValue, String bidder) throws RemoteException {
		// TODO Auto-generated method stub
		String result = null;
		auctionItem aitem = itemHash.get(item);
		if(aitem.checkBidClose(item)){
			if(aitem.getMinimumItemValue() < bidValue){
				aitem.setMinimumItemValue(bidValue);
				aitem.setBidderName(bidder);
				result = "succesful";
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
			String header =" , name, minValue, closeTime, createdTime, bidder,expired";
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
				  write.append(",");
				  write.append(item.getBidderName());
				  write.append(",");
				  write.append(Boolean.toString(item.isExpired()));
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
		File check = new File(state);
		if(check.exists()){
			try {
				BufferedReader buff = new BufferedReader(new FileReader(state));
				buff.readLine();
				while((line = buff.readLine()) != null){
					String [] item = line.split(",");
					itemHash.put(item[1], new auctionItem(item[1],Double.parseDouble(item[2]), Long.parseLong(item[3]), Long.parseLong(item[4]),item[5],Boolean.parseBoolean(item[6])));
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
		}
		return result;
		
	}

	@Override
	public boolean checkExist(String item) throws RemoteException {
		if(itemHash.containsKey(item) && itemHash.get(item).isExpired() != true){
			return true;
		}else{
			return false;
		}
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
					
					String result = null;
					if(itemHash.get(item).getBidderName() == null){
						result = "I am sorry. No bid for " + item + " auction ended: " + f.format(date.getTime());
						System.out.println("no Winner of the Bid for the item " + item);
					}else if(itemHash.get(item).getBidderName().equals(name)){
						result = "Congrats for winning the bid for item: " + item;
					}else{
						result = "Congrats " + itemHash.get(item).getBidderName() + " for winning the bid item " + item + " auction ended: " + f.format(date.getTime());
						System.out.println("informing client "+ itemHash.get(item).getBidderName() +" Winner of the Bid for the item "+ item);
					}
			   		try {
			   			
			   			RMIclient.callBack(result);
			   			itemHash.get(item).setExpired();
			   			saveState();
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
