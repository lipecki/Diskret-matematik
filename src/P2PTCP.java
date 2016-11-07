import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class P2PTCP {

    

    
    public static void main(String[] args) {
        BigInteger modulo;
        
	Scanner scan; Thread st=null;
	Socket peerConnectionSocket=null;
	if(args[0].equals("server")){
            
            modulo = new BigInteger(args[2]);
	    try{
                
		ServerSocket ss = new ServerSocket(Integer.parseInt(args[1]));
		System.out.println("Waiting for connection...");
		peerConnectionSocket = ss.accept();
                PrintWriter pw = new PrintWriter(peerConnectionSocket.getOutputStream());
                pw.println(modulo);
                pw.flush();
                

		st = new Thread(new StringSender(new PrintWriter(peerConnectionSocket.getOutputStream())));
		st.start();
                
                scan = new Scanner (peerConnectionSocket.getInputStream());
		String fromSocket;
                
               
                while((fromSocket = scan.nextLine())!=null)
			System.out.println(fromSocket);
	    }catch(IOException e) {System.err.println("Server crash");}
	    finally {st.stop();}
	}
	else if(args[0].equals("client")) {
	    try{
		peerConnectionSocket = new Socket("localhost", Integer.parseInt(args[1]));

		st = new Thread(new StringSender(new PrintWriter(peerConnectionSocket.getOutputStream())));
		st.start();
		scan = new Scanner (peerConnectionSocket.getInputStream());
		String fromSocket;

		while((fromSocket = scan.nextLine())!=null)
			System.out.println(fromSocket);
	    }
      	    catch(Exception e) {System.err.println("Client crash");}
	    finally{st.stop();}
	}
    }
}

