import java.io.*;

public class Driver {
    public static void main(String[] args) throws IOException {
        double[] c1 = {6, 5};
        int[] p1 = {0, 3};
        Polynomial poly1 = new Polynomial(c1, p1);

        double[] c2 = {-2, -9};
        int[] p2 = {1, 4};
        Polynomial poly2 = new Polynomial(c2, p2);

        Polynomial sum = poly1.add(poly2);
        Polynomial prod = poly1.multiply(poly2);

        System.out.println("poly1 = " + poly1);
        System.out.println("poly2 = " + poly2);
        System.out.println("sum = " + sum);
        System.out.println("prod = " + prod);
        System.out.println("sum(0.1) = " + sum.evaluate(0.1));
        System.out.println("Has root at x=1? " + sum.hasRoot(1));

        poly1.saveToFile("poly1.txt");
        Polynomial fromFile = new Polynomial(new File("poly1.txt"));
        System.out.println("fromFile = " + fromFile);
    }
}