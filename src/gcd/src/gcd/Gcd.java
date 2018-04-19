package gcd;

import java.math.BigInteger;

public class Gcd {
    public static void main(String[] args) {
        BigInteger a,b,c;
        try{
            a=new BigInteger(args[0]);
            b=new BigInteger(args[1]);
            c=findGCD(a,b);
            System.out.println(c.toString());
        }catch(NumberFormatException nfe){
            System.out.println("Wrong Format");
        }
    }
    
    // Methodos poy ypologizei ton Megisto Koino Diaireti
    // Methodos Eykleidi
    public static BigInteger findGCD(BigInteger x, BigInteger y){
        BigInteger z;
        // An x<y tote enallasoume x kai y
        if(x.compareTo(y)<0){
            z=x;
            x=y;
            y=z;
        }
        // briskoyme ypoloipa mexri y=0
        while(y.signum()>0){
            z=x.mod(y);
            x=y;
            y=z;
        }
        return x;
    }
}
