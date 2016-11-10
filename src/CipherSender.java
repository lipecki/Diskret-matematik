
import java.util.*;
import java.io.*;
import java.math.BigInteger;

class CipherSender implements Runnable {

    private Scanner scan;
    private PrintWriter out;
    private BigInteger e, N, encryptedSecret;
    private String secret;
    boolean cont = true;

    public CipherSender(PrintWriter out, BigInteger e, BigInteger N) {
        this.out = out;
        scan = new Scanner(System.in);
        this.e = e;
        this.N = N;
    }

    public void run() {

        System.out.print("Please enter a number between 0 and 100: ");
        secret = scan.nextLine();
        encryptedSecret = Cryptographer.encrypt(secret, e, N);

        //Print to screen
        System.err.println("Secret: " + secret);
        System.err.println("Encrypted secret: " + encryptedSecret);
        System.err.flush();
        
        //Send cypher
        out.println(encryptedSecret);
        out.flush();

    }

    public void stop() {
        cont = false;
    }

}
