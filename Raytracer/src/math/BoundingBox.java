package math;

public class BoundingBox {

	public Point a;
	public Point b;
	
	public BoundingBox() {
		a = new Point(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
		b = new Point(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
	}
	
	public void update(Triangle t) {
		update(t.p0);
		update(t.p1);
		update(t.p2);
	}
	public void update(Point p) {
		a.x = Math.min(a.x, p.x);
		b.x = Math.max(b.x, p.x);
		
		a.y = Math.min(a.y, p.y);
		b.y = Math.max(b.y, p.y);
		
		a.z = Math.min(a.z, p.z);
		b.z = Math.max(b.z, p.z);
	}

	//AABB bounding box equation taken shamelessly from:
	//http://tavianator.com/2011/05/fast-branchless-raybounding-box-intersections/
	public boolean intersects(Ray eye) {
		float tmin = Float.NEGATIVE_INFINITY;
		float tmax = Float.POSITIVE_INFINITY;
		
		if (eye.direction.x != 0) {
			float tx1 = (a.x - eye.point.x) / eye.direction.x;
			float tx2 = (b.x - eye.point.x) / eye.direction.x;
			
			tmin = Math.max(tmin, Math.min(tx1, tx2));
			tmax = Math.min(tmax, Math.max(tx1, tx2));
		}
		if (eye.direction.y != 0) {
			float ty1 = (a.y - eye.point.y) / eye.direction.y;
			float ty2 = (b.y - eye.point.y) / eye.direction.y;
			
			tmin = Math.max(tmin, Math.min(ty1, ty2));
			tmax = Math.min(tmax, Math.max(ty1, ty2));
		}
		if (eye.direction.z != 0) {
			float tz1 = (a.z - eye.point.z) / eye.direction.z;
			float tz2 = (b.z - eye.point.z) / eye.direction.z;
			
			tmin = Math.max(tmin, Math.min(tz1, tz2));
			tmax = Math.min(tmax, Math.max(tz1, tz2));
		}
		
		return tmax >= tmin;
	}
}
