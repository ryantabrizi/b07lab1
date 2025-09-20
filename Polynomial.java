public class Polynomial {

	private double[] eq;

	public Polynomial() {
	
		eq = new double[]{0.0};
	}

	public Polynomial(double[] eq) {
	
		this.eq = eq;
	}

	public Polynomial add(Polynomial anothereq) {
		
		int maxLength = Math.max(this.eq.length, anothereq.eq.length);
		double[] added = new double[maxLength];
	
		for (int i = 0; i < maxLength; i++) {
			double x = (i < this.eq.length) ? this.eq[i] : 0.0;
			double y = (i < anothereq.eq.length) ? anothereq.eq[i] : 0.0;
			added[i] = x + y;
		}
		
		return new Polynomial(added);
	}
	
	public double evaluate(double x) {

		double evaluated = 0.0;
		
		for (int i = 0; i < eq.length; i++) {
			evaluated += eq[i] * Math.pow(x, i);
		}
		
		return evaluated;
	}

	public boolean hasRoot(double x) {
	
		double value = evaluate(x);
	
		return value == 0.0;
	}
}

