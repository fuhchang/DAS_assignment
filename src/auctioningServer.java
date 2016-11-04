import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class auctioningServer {
    private static int port = 1099;
    
    public auctioningServer(){
    	auctionImpl im = new auctionImpl();
        try {

  		auction a = (auction) UnicastRemoteObject.exportObject(im, port);
  		Naming.rebind("rmi://localhost:" + port + "/auctionService", a);
  		
  	} catch (RemoteException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	} catch (MalformedURLException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
  	

    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length == 1)
			port = Integer.parseInt(args[0]);
		
		new auctioningServer();
	}
}
