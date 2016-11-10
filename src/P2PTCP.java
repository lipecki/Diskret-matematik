
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class P2PTCP {

    public static void main(String[] args) {

        Scanner scan, keyboard;
        Thread st = null;
        Socket peerConnectionSocket = null;
        Random rnd = new Random(System.currentTimeMillis());
        BigInteger e, N, krypto;
        BigInteger[][] serverKeys, clientKeys;

        keyboard = new Scanner(System.in);
        String fromSocket, in = null;

        if (args[0].equals("server")) {
            e = N = null;

            try {
                serverKeys = Cryptographer.generateKeys(Integer.valueOf(args[2]), rnd);
                ServerSocket ss = new ServerSocket(Integer.parseInt(args[1]));

                System.out.println("Waiting for connection...");

                peerConnectionSocket = ss.accept();
                
                PrintWriter pw = new PrintWriter(peerConnectionSocket.getOutputStream());
                scan = new Scanner(peerConnectionSocket.getInputStream());
                
                pw.println(serverKeys[0][0] + ";" + serverKeys[0][1]);
                pw.flush();

                while ((fromSocket = scan.nextLine()) != null) {
                    String[] keyStrings = fromSocket.split(";");

                    //public key received => generate secret and send
                    if (keyStrings.length == 2) {
                        e = new BigInteger(keyStrings[0]);
                        N = new BigInteger(keyStrings[1]);

                        int secret = rnd.nextInt(100) + 1;

                        BigInteger secretEncrypted = Cryptographer.encrypt(Integer.toString(secret), e, N);

                        //Print to screen
                        System.err.println("Secret: " + secret);
                        System.err.println("Encrypted secret: " + secretEncrypted);
                        System.err.flush();

                        //Write to socket
                        pw.println(secretEncrypted);
                        pw.flush();

                        fromSocket = null;

                    } //secret received => Decrypt and display
                    else if ((krypto = new BigInteger(keyStrings[0])) instanceof BigInteger) {

                        //Print to screen
                        System.err.println("Decrypt BigInteger: " + krypto);
                        System.err.println("Secret: " + Cryptographer.decrypt(krypto, serverKeys[1][0], serverKeys[1][1]));
                        System.err.flush();
                    } else {
                        System.err.println("The End!");
                    }
                }
            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                st.stop();
            }
        } else if (args[0].equals("client")) {
            e = N = null;

            try {
                clientKeys = Cryptographer.generateKeys(Integer.valueOf(args[2]), rnd);
                peerConnectionSocket = new Socket("localhost", Integer.parseInt(args[1]));
                PrintWriter out = new PrintWriter(peerConnectionSocket.getOutputStream());
                
                scan = new Scanner(peerConnectionSocket.getInputStream());
                System.out.println("Client thinks it's connected");

                while ((fromSocket = scan.nextLine()) != null){
                    String[] keyStrings = fromSocket.split(";");

                    //public key received => generate secret and send
                    if (keyStrings.length == 2) {
                        e = new BigInteger(keyStrings[0]);
                        N = new BigInteger(keyStrings[1]);
                        
                        st = new Thread(new CipherSender(out, e, N));
                        st.start();

                    } //secret received => Decrypt and display
                    else if ((krypto = new BigInteger(keyStrings[0])) instanceof BigInteger) {

                        //Print to screen
                        System.err.println("Decrypt BigInteger: " + krypto);
                        System.err.println("Secret: " + Cryptographer.decrypt(krypto, clientKeys[1][0], clientKeys[1][1]));
                        System.err.flush();
                    } 
                }
            } catch (Exception io) {
                io.printStackTrace();
            } finally {
                st.stop();
            }
        }
    }

}
