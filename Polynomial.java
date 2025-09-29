import java.io.*;

public class Polynomial {

    private double[] coeffs;  
    private int[] powers;      
    private int termCount;   

//constructors below

    public Polynomial() {
        coeffs = new double[]{0.0};
        powers = new int[]{0};
        termCount = 1;
    }

    public Polynomial(double[] c, int[] p) {
        termCount = c.length;
        coeffs = new double[termCount];
        powers = new int[termCount];
        for (int i = 0; i < termCount; i++) {
            coeffs[i] = c[i];
            powers[i] = p[i];
        }
        compress();
    }

    public Polynomial(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        br.close();

        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (i == 0 || line.charAt(i) == '+' || line.charAt(i) == '-') count++;
        }

        coeffs = new double[count];
        powers = new int[count];
        termCount = 0;

        int i = 0;
        while (i < line.length()) {
            int sign = 1;
            if (line.charAt(i) == '+') { i++; }
            else if (line.charAt(i) == '-') { sign = -1; i++; }

            String coefTxt = "";
            while (i < line.length() && (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.')) {
                coefTxt += line.charAt(i);
                i++;
            }
            double coef = coefTxt.equals("") ? 1.0 : Double.parseDouble(coefTxt);
            coef *= sign;

            int exp = 0;
            if (i < line.length() && line.charAt(i) == 'x') {
                i++;
                String expTxt = "";
                while (i < line.length() && Character.isDigit(line.charAt(i))) {
                    expTxt += line.charAt(i);
                    i++;
                }
                exp = expTxt.equals("") ? 1 : Integer.parseInt(expTxt);
            }

            coeffs[termCount] = coef;
            powers[termCount] = exp;
            termCount++;
        }
        compress();
    }

//methods below

    public Polynomial add(Polynomial other) {
        double[] nc = new double[this.termCount + other.termCount];
        int[] np = new int[this.termCount + other.termCount];

        for (int i = 0; i < this.termCount; i++) {
            nc[i] = this.coeffs[i];
            np[i] = this.powers[i];
        }
        for (int j = 0; j < other.termCount; j++) {
            nc[this.termCount + j] = other.coeffs[j];
            np[this.termCount + j] = other.powers[j];
        }
        return new Polynomial(nc, np);
    }

    public Polynomial multiply(Polynomial other) {
        double[] nc = new double[this.termCount * other.termCount];
        int[] np = new int[this.termCount * other.termCount];
        int k = 0;

        for (int i = 0; i < this.termCount; i++) {
            for (int j = 0; j < other.termCount; j++) {
                nc[k] = this.coeffs[i] * other.coeffs[j];
                np[k] = this.powers[i] + other.powers[j];
                k++;
            }
        }
        return new Polynomial(nc, np);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < termCount; i++) {
            double term = coeffs[i];
            for (int k = 0; k < powers[i]; k++) {
                term *= x; // multiply manually instead of Math.pow
            }
            result += term;
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0.0;
    }

    public void saveToFile(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        fw.write(this.toString());
        fw.close();
    }

    // so to merge like terms and to remove zero coefficients from resulting polynomials
    private void compress() {
        for (int i = 0; i < termCount; i++) {
            for (int j = i + 1; j < termCount; j++) {
                if (powers[i] == powers[j]) {
                    coeffs[i] += coeffs[j];
                    coeffs[j] = 0;
                }
            }
        }
        int valid = 0;
        for (int i = 0; i < termCount; i++) if (coeffs[i] != 0) valid++;

        double[] c2 = new double[valid];
        int[] p2 = new int[valid];
        int k = 0;
        for (int i = 0; i < termCount; i++) {
            if (coeffs[i] != 0) {
                c2[k] = coeffs[i];
                p2[k] = powers[i];
                k++;
            }
        }
        coeffs = c2;
        powers = p2;
        termCount = valid;
    }

    @Override
    public String toString() {
        if (termCount == 0) return "0";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < termCount; i++) {
            double c = coeffs[i];
            int e = powers[i];
            if (c > 0 && i > 0) sb.append("+");
            if (e == 0) sb.append(c);
            else if (e == 1) sb.append(c + "x");
            else sb.append(c + "x" + e);
        }
        return sb.toString();
    }
}


