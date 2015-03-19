package math;

import main.Main;

public class Triangle extends Shape {
	
	public Point p0;
	public Point p1;
	public Point p2;
	public Vector3 normal;
	
	public Vector3 p1p0, p2p0;
	
	//temp
	private Vector3 ppp0 = new Vector3();
	private Vector3 uvtemp = new Vector3();
	
	public boolean singleNormal;
	public boolean hasTexture;
	public Vector3 normal0, normal1, normal2;
	public Vector3 uv0, uv1, uv2;
	
	public MeshShape owner;
	
	public Triangle(Point p0, Point p1, Point p2) {
		//three points define a triangle
		this.p0 = new Point();
		this.p1 = new Point();
		this.p2 = new Point();
		normal = new Vector3();
		this.p0.set(p0);
		this.p1.set(p1);
		this.p2.set(p2);
		p1p0 = new Vector3();
		p2p0 = new Vector3();
		p1.subtract(p1p0, p0);
		p2.subtract(p2p0, p0);
		p1p0.crossProd(this.normal, p2p0);
		this.normal.normalize(this.normal);
		this.singleNormal = true;
		
		hasTexture = false;
	}
	public Triangle(Point p0, Point p1, Point p2, Vector3 vn0, Vector3 vn1, Vector3 vn2) {
		this.p0 = new Point();
		this.p1 = new Point();
		this.p2 = new Point();
		normal = new Vector3();
		this.p0.set(p0);
		this.p1.set(p1);
		this.p2.set(p2);
		
		p1p0 = new Vector3();
		p2p0 = new Vector3();
		p1.subtract(p1p0, p0);
		p2.subtract(p2p0, p0);
		
		//for now average vn's
		normal.add(normal, vn0);
		normal.add(normal, vn1);
		normal.add(normal, vn2);
		normal.scale(normal, 1.0f/3.0f);
		
		this.singleNormal = false;
		this.normal0 = new Vector3();
		this.normal1 = new Vector3();
		this.normal2 = new Vector3();
		this.normal0.set(vn0);
		this.normal1.set(vn1);
		this.normal2.set(vn2);
		this.normal0.normalize(this.normal0);
		this.normal1.normalize(this.normal1);
		this.normal2.normalize(this.normal2);
		
		hasTexture = false;
	}
	public void addTextureCoordinates(Vector3 vt0, Vector3 vt1, Vector3 vt2) {
		//handle texture coordinates
		uv0 = new Vector3();
		uv1 = new Vector3();
		uv2 = new Vector3();
		this.uv0.set(vt0);
		this.uv1.set(vt1);
		this.uv2.set(vt2);
		hasTexture = true;
	}
	
	@Override
	public Intersection getIntersection(Intersection src, Ray eye) {
		
		//no need for AABB calculation...or should we?
		//nah not worth the time
		
		float a = p0.x - p1.x;
		float b = p0.y - p1.y;
		float c = p0.z - p1.z;
		float d = p0.x - p2.x;
		float e = p0.y - p2.y;
		float f = p0.z - p2.z;
		float g = eye.direction.x;
		float h = eye.direction.y;
		float i = eye.direction.z;
		float j = p0.x - eye.point.x;
		float k = p0.y - eye.point.y;
		float l = p0.z - eye.point.z;
		float m = a*(e*i - h*f) + b*(g*f - d*i) + c*(d*h - e*g);
		if(m == 0) { // ray is parallel to the plane containing the triangle
			return src.invalidate();
		}
		float t = -1/m * (f*(a*k-j*b) + e*(j*c - a*l) + d*(b*l - k*c));
		if(t < eye.tmin || t > eye.tmax) { // intersection isn't within the bounds
			return src.invalidate();
		}
		float gamma = (i*(a*k - j*b) + h*(j*c - a*l) + g*(b*l - k*c)) * 1/m;
		if(gamma < 0 || gamma > 1) { // ray doesn't intersect inside the triangle
			return src.invalidate();
		}
		float beta = (j*(e*i - h*f) + k*(g*f - d*i) + l*(d*h - e*g)) * 1/m;
		if(beta < 0 || beta > 1 - gamma) { // ray doesn't intersect inside the triangle
			return src.invalidate();
		}
		else {
			eye.getPointAt(src.intersection, t);
			src.intersects = true;
			
			if (singleNormal || Main.simpleShading) {
				src.normal.set(this.normal);
			}
			else {
				//find barycentric coords
				
				//sauce: http://gamedev.stackexchange.com/questions/23743/whats-the-most-efficient-way-to-find-barycentric-coordinates
				
				src.intersection.subtract(ppp0, p0);
				float d00 = Vector3.dot(p1p0, p1p0);
				float d01 = Vector3.dot(p1p0, p2p0);
				float d11 = Vector3.dot(p2p0, p2p0);
				float d20 = Vector3.dot(ppp0, p1p0);
				float d21 = Vector3.dot(ppp0, p2p0);
				float denom = d00 * d11 - d01 * d01;
				
				float l2 = (d11*d20 - d01*d21) / denom;
				float l3 = (d00*d21 - d01*d20)/ denom;
				float l1 = 1 - l2 - l3;
				src.normal.set(0, 0, 0);
				src.normal.addScaled(normal0, l1);
				src.normal.addScaled(normal1, l2);
				src.normal.addScaled(normal2, l3);
				src.normal.normalize(src.normal);
				
			}
			src.t = t;
			
			if (hasTexture) {
				src.useUV = true;
				src.intersection.subtract(ppp0, p0);
				float d00 = Vector3.dot(p1p0, p1p0);
				float d01 = Vector3.dot(p1p0, p2p0);
				float d11 = Vector3.dot(p2p0, p2p0);
				float d20 = Vector3.dot(ppp0, p1p0);
				float d21 = Vector3.dot(ppp0, p2p0);
				float denom = d00 * d11 - d01 * d01;
				
				float l2 = (d11*d20 - d01*d21) / denom;
				float l3 = (d00*d21 - d01*d20)/ denom;
				float l1 = 1 - l2 - l3;
				
				uvtemp.set(0, 0, 0);
				uvtemp.addScaled(uv0, l1);
				uvtemp.addScaled(uv1, l2);
				uvtemp.addScaled(uv2, l3);
				src.u = uvtemp.x;
				src.v = uvtemp.y;
				//w?
			}
			
			return src;
		}
	}
	
	public static void main(String[] args) {
		Point p0 = new Point(0.0f, 0.0f, 0.0f);
		Point p1 = new Point(1.0f, 0.0f, 0.0f);
		Point p2 = new Point(0.0f, 1.0f, 0.0f);
		Triangle t = new Triangle(p0, p1, p2);
		
		Point start = new Point(0.5f, 0.0f, 0.5f);
		Vector3 dir = new Vector3(0f, 0f, -1f);
		Ray r = new Ray(start, dir);
		System.out.println(t.getIntersection(new Intersection(), r));
	}
}
