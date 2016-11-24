import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class performanceTest extends UnicastRemoteObject implements auctionClientServant , Runnable, Serializable{
	 static auctionItemInter servant;
	 static int port = 1099;
	 private static long start;
	 private static int counter =0;
	protected performanceTest() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("testing performance");
		try {
			servant = (auctionItemInter) Naming.lookup("rmi://localhost:"+port+"/AuctionService");
			testCreateMethod(servant);
			//testReadAuctionList(servant);
			//testBidItem(servant);
			//testCheckExist(servant);
			Thread thread = null;
			start = Stopwatch();
			for(int i=0;i<10000;i++){
				counter = i+1;
				thread  = new Thread(new performanceTest());
				thread.start();
				thread.join();
			}
			
			double elapsedTime= Stopwatch()- start;
			System.out.println("10000 calls of createItem in " + elapsedTime + "ms - " + elapsedTime/10000 + "."+ (elapsedTime%10000)/10 + "ms per call");
		} catch (RemoteException | NotBoundException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("tested end");
		System.exit(0);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		auctionItem item = new auctionItem();
		item.setMinimumItemValue(100);
		item.setBidderName("hello");
		item.setBidderList("hello");
		item.setCloseTime(1);
		item.setName("test"+counter);
		try {
			servant.CreateItem(item, "hello");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void callBack(String s) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkAlive(String result) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	public static long Stopwatch() {
        return System.currentTimeMillis();
    }
	
	
	
	public static void testCreateMethod(auctionItemInter servant){
		auctionItem item = new auctionItem();
		
		item.setMinimumItemValue(100);
		item.setBidderName("hello");
		item.setBidderList("hello");
		item.setCloseTime(1);
		start = Stopwatch();
		for(int i=0;i<10000;i++){
			try {
				item.setName("test"+i);
				servant.CreateItem(item, "hello");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		double elapsedTime = Stopwatch()- start;
		System.out.println("10000 calls of createItem in " + elapsedTime + "ms - " + elapsedTime/10000 + "."+ (elapsedTime%10000)/10 + "ms per call");
	}
	
	public static void testReadAuctionList(auctionItemInter servant){
		try {
			start = Stopwatch();
			servant.getAuctionList();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double elapsedTime= Stopwatch()- start;
		try {
			System.out.println("Get "+ servant.getAuctionList().size()+" item in " + elapsedTime + "ms - " + elapsedTime/10000 + "."+ (elapsedTime%10000)/10 + "ms per call");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testBidItem(auctionItemInter servant){
		start = Stopwatch();
		for(int i=0;i<10000;i++){
			try {
				servant.bidItem("test1", i, "hello");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		double elapsedTime = Stopwatch()- start;
		System.out.println("10000 calls of bidItem in " + elapsedTime + "ms - " + elapsedTime/10000 + "."+ (elapsedTime%10000)/10 + "ms per call");
	}
	
	public static void testCheckExist(auctionItemInter servant){
		try {
			HashMap<String, auctionItem> list = servant.getAuctionList();
			start = Stopwatch();
			for(String key : list.keySet()){
				servant.checkExist(key);
			}
			double elapsedTime= Stopwatch()- start;
			System.out.println("10000 calls of checkExist in " + elapsedTime + "ms - " + elapsedTime/10000 + "."+ (elapsedTime%10000)/10 + "ms per call");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
