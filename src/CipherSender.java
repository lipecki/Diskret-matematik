
import java.util.*;
import java.io.*;
import java.math.BigInteger;

class CipherSender implements Runnable {

    private Scanner scan;
    private PrintWriter out;
    private BigInteger e, N, encryptedSecret;
    private String secret;
    boolean cont = true;

    public CipherSender(PrintWriter out,String secret, BigInteger e, BigInteger N) {
        this.scan = new Scanner(System.in);
        this.out = out;
        this.secret = secret;
        this.e = e;
        this.N = N;
    }

    public void run() {
        encryptedSecret = Cryptographer.encrypt(secret, e, N);

        //Print to screen
        System.err.println("New secret: " + secret);
        System.err.println("Encrypted secret: " + encryptedSecret);
        System.err.flush();
        
        //Send cypher
        out.println(encryptedSecret);
        out.flush();
    }

}
