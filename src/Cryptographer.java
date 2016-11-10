
import java.math.BigInteger;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * [Project] license
 * 
 * Copyright © 2016 Johan Lipecki
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
/**
 *
 * @author Johan Lipecki <lipecki@kth.se>, Viggo Lundén <vlunden@kth.se>
 */
class Cryptographer {

    static BigInteger encrypt(String secret, BigInteger e, BigInteger N) {
        BigInteger s = new BigInteger(secret);
        return s.modPow(e, N);
    }

    static BigInteger decrypt(BigInteger bigSecret, BigInteger d, BigInteger N) {
        return bigSecret.modPow(d, N);
    }

    public static BigInteger[][] generateKeys(int keyLength, Random rnd) {
        BigInteger a, d, e, N, p, q, x;
        do {
            do {

                p = BigInteger.probablePrime(keyLength / 2, rnd).abs();
                q = BigInteger.probablePrime(keyLength / 2, rnd).abs();
            } while (p.equals(BigInteger.ONE) || q.equals(BigInteger.ONE) || p.equals(q)); // a får inte bli 0

            System.out.println("p & q: " + p + " & " + q);

            //Längden av N blir inte längre än keyLenght eftersom 2 bitar x 2 bitar inte blir längre än 4 bitar, osv.
            N = p.multiply(q);

            System.out.println("N length: " + N.bitLength());

            BigInteger ett = BigInteger.ONE;

            BigInteger pMinEtt = p.subtract(ett);
            BigInteger qMinEtt = q.subtract(ett);

            a = pMinEtt.multiply(qMinEtt);

            double dbl = rnd.nextDouble() * 10;
            while (dbl <= 1.0) {
                dbl = rnd.nextDouble() * 10;
                if (dbl < 0) {
                    dbl = -(dbl);
                }
            }

            e = BigInteger.valueOf((long) Math.pow(Math.E, dbl));

            System.out.println("e: " + e);

            while (!a.gcd(e).equals(ett)) {
                e = e.add(BigInteger.valueOf(rnd.nextLong())).abs();
                System.out.println("e: " + e);
            }

            x = BigInteger.ONE;

            while (!(a.multiply(x).add(BigInteger.ONE)).remainder(e).equals(BigInteger.ZERO) && (x.bitLength() <= (int) Math.sqrt(keyLength))) {
                x = x.add(BigInteger.ONE);

                System.out.println("x: " + x);
            }

            d = (a.multiply(x).add(BigInteger.ONE)).divide(e);
        } while (x.bitLength() >= Math.sqrt(keyLength));

        System.out.println("a: " + a);
        System.out.println("d: " + d);

        return new BigInteger[][]{{e, N}, {d, N}};
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

Välj dekrypteringstalet x så att de = 1(mod( p - 1)(q - 1)) . 
Det är lätt att göra med en dator. 
I vårt exempel gäller att ( p - 1)(q - 1) = 4 , som sagt, 
och vi behöver bara finna ett tal x som har egenskapen att 
( x( p - 1)(q - 1) + 1) / e  = (4x + 1) / 7 blir ett heltal.

Detta heltal duger sedan som x. 
I exemplet ser vi att x = 3 
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
