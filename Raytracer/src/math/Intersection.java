package math;

public class Intersection {
	
	//actual intersection point
	public Point intersection;
	//where on ray, in terms of the t
	public float t;
	
	//normal to surface at said intersection point
	public Vector3 normal;
	
	public boolean intersects;
	
	public boolean useUV = false;
	public float u, v;
	
	public Intersection() {
		this.intersection = new Point();
		this.t = 0;
		this.normal = new Vector3();
		this.intersects = false;
	}
	public Intersection(Point i, float t, Vector3 norm) {
		this.intersection = i;
		this.t = t;
		this.normal = norm;
		this.intersects = true;
	}
	public void set(Point i, float t, Vector3 norm) {
		this.intersection = i;
		this.t = t;
		this.normal = norm;
		this.intersects = true;
	}
	public void set(Ray eye, float t, Vector3 norm) {
		//get point of eye, set it to point
		//this.intersection = ...
		this.t = t;
		this.intersects = true;
		this.normal = norm;
	}
	public void set(Intersection i) {
		this.intersection.set(i.intersection);
		this.t = i.t;
		this.normal.set(i.normal);
		this.intersects = i.intersects;
		
		this.useUV = i.useUV;
		this.u = i.u;
		this.v = i.v;
	}
	public float getT() {
		return t;
	}
	public boolean intersects() {
		return this.intersects;
	}
	public Intersection invalidate() {
		this.intersects = false;
		return this;
	}
	
	@Override
	public String toString() {
		if (this.intersects) {
			return "Point:"+this.intersection + "/Normal:"+this.normal + "/Time:" + this.t;
		}
		return "None";
	}
}
