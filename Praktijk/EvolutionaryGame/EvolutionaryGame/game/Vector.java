
public class Vector {

	double x;
	double y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector add(Vector v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}

	public Vector mult(double n) {
		this.x *= n;
		this.y *= n;
		return this;
	}

	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + "]";
	}
	
	

}
