
public class auctioningClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       String host = "localhost";
       int port = 1099;
       if(args.length == 1){
    	   port = Integer.parseInt(args[0]); 
       }else if(args.length == 2){
    	   host = args[0];
    	   port = Integer.parseInt(args[1]); 
       }
       
	}

}
