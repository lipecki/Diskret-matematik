
import java.util.*;
import java.io.*;
import java.math.BigInteger;

class CipherSender implements Runnable {

    private Scanner scan;
    private PrintWriter out;
    private BigInteger e, N, encryptedSecret;
    private String secret;
    boolean cont = true;
    private BigInteger keyLength;

    public CipherSender(PrintWriter out, String secret, BigInteger e, BigInteger N, String keyLength) {
        this.scan = new Scanner(System.in);
        this.out = out;
        this.secret = secret;
        this.e = e;
        this.N = N;
        this.keyLength = new BigInteger(keyLength);
    }

    private BigInteger listSum(List<BigInteger> list)
    {
        BigInteger val = BigInteger.ZERO;
        for (int i = 0; i < list.size(); i++)
        {
            val = val.add(list.get(i));
        }
        return val;
    }
    
    private BigInteger setAllBits(BigInteger keyLength)
    {
        BigInteger val = BigInteger.ZERO;
        for (int i = 0; keyLength.compareTo(new BigInteger(String.valueOf(i))) > 0; i++)
        {
            val = val.setBit(i);
        }
        return val;
    }
    
    private List<BigInteger> splitToSmaller(String secret)
    {
        BigInteger biSecret = new BigInteger(secret);
        BigInteger secretBitLength = new BigInteger(String.valueOf(biSecret.bitLength()));
        
        List<BigInteger> secrets = new ArrayList<>();
        
        while (biSecret.bitLength() > listSum(secrets).bitLength())
        {
           
            secrets.add(setAllBits(keyLength.subtract(BigInteger.ONE)));
            int sumListLength = listSum(secrets).bitLength();
            int bb = 0;
            
        }
        secrets.add(biSecret.subtract(listSum(secrets)));
        System.out.println("Split To Smaller Sum: " + listSum(secrets).toString());
        return secrets;
    }
    
    public void run() {
        
        List<BigInteger> secrets = splitToSmaller(secret);
        System.out.println("Split secrets:");
        for (int i = 0; i < secrets.size(); i++) System.out.println(secrets.get(i).toString());
        
        
        List<BigInteger> encryptedSecrets = new ArrayList<>();
        for (int i = 0; i < secrets.size(); i++)
        {
            encryptedSecrets.add(Cryptographer.encrypt(secrets.get(i).toString(), e, N));
        }
        
        //encryptedSecret = Cryptographer.encrypt(secret, e, N);
        
        
        
        //Print to screen
        System.err.println("New secret: " + secret);
        System.err.println("Encrypted secrets: ");
        for (int i = 0; i < encryptedSecrets.size(); i++)
        {
            System.out.println(encryptedSecrets.get(i).toString());
        }
        System.err.flush();
        
        //Send cypher
        for (int i = 0; i < encryptedSecrets.size(); i++) {
            out.println(encryptedSecrets.get(i).toString());
            out.flush();
        }
           
    }

}
