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
                
               
                while((fromSocket = scan.nextLine())!=null){
                        System.out.println("Decrypt BigInteger: " + fromSocket);
                }
	    }catch(IOException e) {System.err.println("Server crash");}
	    finally {st.stop();}
	}
	else if(args[0].equals("client")) {
            Random rnd = new Random(System.currentTimeMillis());
	    try{
		peerConnectionSocket = new Socket("localhost", Integer.parseInt(args[1]));

		st = new Thread(new StringSender(new PrintWriter(peerConnectionSocket.getOutputStream())));
		st.start();
		scan = new Scanner (peerConnectionSocket.getInputStream());
		String fromSocket;

		while((fromSocket = scan.nextLine())!=null)
			System.out.println("Received modulo: " + fromSocket);
	    }
      	    catch(Exception e) {System.err.println("Client crash");}
	    finally{st.stop();}
	}
    }
}

/*
Beskrivning av konstruktion av en RSA-instans


Välj två primtal, till exempel p = 2 och q = 5. 
Bilda produkten pq 10, detta är N, (det som vi sen tar modulo med). 
Det anger också hur många olika symboler vi kan kryptera. (Bara 10 här alltså.)

Välj krypteringstalet e > 1, så att e och (p - 1)(q - 1) är relativt prima. 
I vårt exempel är ( p - 1)(q - 1)  (2 - 1)(5 - 1) =  4 . 
Talet 7 är ett primtal och 7 och 4 är relativt prima. 
Vi väljer alltså e = 7 .

Välj dekrypteringstalet d så att de = 1(mod( p - 1)(q - 1)) . 
Det är lätt att göra med en dator. 
I vårt exempel gäller att ( p - 1)(q - 1) = 4 , som sagt, 
och vi behöver bara finna ett tal x som har egenskapen att 
( x( p - 1)(q - 1) + 1) / e  = (4x + 1) / 7 blir ett heltal.

Detta heltal duger sedan som d. 
I exemplet ser vi att d = 3 
och vad det svarar mot för x kan vi se 
genom att lösa ut x ur (4x + 1) / 7 = 3 ⇔  4x + 1 = 21 ⇔  x = 5 . 
En dator kan programmeras för att successivt finna x genom testning. 
Ger x = 1 att (4x + 1)/7 blir heltal? 
Om inte så fortsätt testa x = 2 osv. 
Vi beräknar produkten de för att kontrollera:
de = 3 x 7 = 21 =  20 + 1 =  5 x 4 + 1 = 1(mod 4) . 
Alltså gäller de = 1(mod( p - 1)(q - 1)) .

Krypteringsfunktionen blir nu C = encrypt(T) = T ^ e(mod N) , 
i vårt fall C = encrypt(T) = T ^7(mod10) .
*/

