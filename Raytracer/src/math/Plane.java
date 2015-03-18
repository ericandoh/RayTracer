package math;

public class Plane {
	public Point ul, ll, ur, lr;
	
	public Plane(Point ul, Point ll, Point ur, Point lr) {
		this.ul = ul;
		this.ll = ll;
		this.ur = ur;
		this.lr = lr;
	}
	public Plane() {
		this.ul = new Point();
		this.ll = new Point();
		this.ur = new Point();
		this.lr = new Point();
	}
	public Point interpolate(float x, float y) {
		Point pt = new Point();
		Vector3 temp11 = new Vector3();
		Vector3 temp12 = new Vector3();
		Vector3 temp1 = new Vector3();
		ll.scale(temp11, y);
		ul.scale(temp12, 1 - y);
		temp11.add(temp1, temp12);
		temp1.scale(temp1, 1 - x);
		
		Vector3 temp21 = new Vector3();
		Vector3 temp22 = new Vector3();
		Vector3 temp2 = new Vector3();
		lr.scale(temp21, y);
		ur.scale(temp22, 1 - y);
		temp21.add(temp2, temp22);
		temp2.scale(temp2, x);
		
		temp2.add(temp2, temp1);
		pt.x = temp2.x;
		pt.y = temp2.y;
		pt.z = temp2.z;
		return pt;
	}
	public String toString() {
		return ""+this.ul+this.ll+this.ur + this.lr;
	}
}
