import java.util.Scanner;

public class DemoPoly {
    public static void main(String[] args) {
        System.out.println("Enter first polynomial");
        Polynomial polynomial = Polynomial.readPoly(new Scanner(System.in));
        System.out.println("Enter second polynomial");
        Polynomial polynomial2 = Polynomial.readPoly(new Scanner(System.in));
        System.out.println("\n(" + polynomial +  ") + (" + polynomial2 + ") =");
        System.out.println(polynomial.addPoly(polynomial2));
        System.out.println("\n(" + polynomial +  ") * (" + polynomial2 + ") =");
        System.out.println(polynomial.multiplyPoly(polynomial2));
    }
}