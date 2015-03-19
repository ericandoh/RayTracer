package io;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import raytracer.WorldObject;
import math.BRDF;
import math.MeshShape;
import math.Point;
import math.Triangle;
import math.Vector3;

public class ObjReader {
	
	//generic reader for a file
	//parsing of this file (scene file for CS184 only) is handled in Main.java
	public static ArrayList<String> readFile(String fileName) 
			throws FileNotFoundException, IOException {
		ArrayList<String> contents = new ArrayList<String>();
		
		File file = new File(System.getProperty("user.dir") + "/" + fileName);
		
		if (!file.exists()) {
			System.out.println(fileName + " does not exist at path "+file.getAbsolutePath());
			return contents;
		}
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
			line = line.replaceAll("\t", " ");
			String[] args = line.trim().replaceAll(" +"," ").split(" ");
			for (String arg : args) {
				contents.add(arg);
			}
		}
		reader.close();
		
		return contents;
	}
	
	
	public static BufferedImage readImageFile(String fileName) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.err.printf("No resource file named "+fileName+": "+e.getMessage());
		}
		return img;
	}
	
	public static ArrayList<WorldObject> readObj(String fileName) {
		ArrayList<Point> vertices = new ArrayList<Point>();
		
		ArrayList<Vector3> vertexNormals = new ArrayList<Vector3>();
		ArrayList<Vector3> textureCoords = new ArrayList<Vector3>();
		
		ArrayList<MeshShape> meshes = new ArrayList<MeshShape>();
		ArrayList<WorldObject> wobjs = new ArrayList<WorldObject>();
		HashMap<String, BRDF> matLst = new HashMap<String, BRDF>();
		
		if (!fileName.endsWith(".obj")) {
			fileName = fileName + ".obj";
		}
		
		File objFile = new File(System.getProperty("user.dir") + "/" + fileName);
		
		if (!objFile.exists()) {
			System.out.println(fileName + " does not exist at path "+objFile.getAbsolutePath());
			return wobjs;
		}
		try {
			BufferedReader mapReader = new BufferedReader(new FileReader(objFile));
			String line;
			while ((line = mapReader.readLine()) != null) {
				processObjLine(line, vertices, vertexNormals, textureCoords, meshes, matLst, wobjs);
			}
			mapReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: "+objFile.getPath());
		} catch (IOException e) {
			System.out.println("IO Error!");
			e.printStackTrace();
		} catch (MalformedObjFileException e) {
			System.out.println("OBJ file is malformed: "+e.getMessage());
			e.printStackTrace();
		}
		return wobjs;
	}
	
	public static void processObjLine(String line, 
			ArrayList<Point> vertices, 
			ArrayList<Vector3> vertexNormals, 
			ArrayList<Vector3> textureCoords, 
			ArrayList<MeshShape> meshes,
			HashMap<String, BRDF> matLst,
			ArrayList<WorldObject> wobjs) throws MalformedObjFileException {
		line = line.replaceAll("\t", " ");
		String[] parts = line.trim().replaceAll(" +"," ").split(" ");
		if (parts.length == 0) {
			return;
		}
		String starter = parts[0];
		if (starter.equals("#")) {
			//this is a comment
			return;
		}
		else if (starter.equals("mtllib")) {
			//load a material
			if (parts.length < 2)
				return;
			readMtl(parts[1], matLst);
			return;
		}
		else if (starter.equals("o")) {
			/*MeshShape shape;
			if (parts.length < 2)
				shape = new MeshShape();
			else
				shape = new MeshShape(parts[1]);
			meshes.add(0, shape);
			wobjs.add(0, new WorldObject(shape, null));*/
		}
		else if (starter.equals("vn")) {
			//vertex normal
			if (parts.length < 4)
				throw new MalformedObjFileException("Need 3 points for vertex normal");
			Vector3 p = new Vector3(Float.parseFloat(parts[1]), 
					Float.parseFloat(parts[2]), Float.parseFloat(parts[3]));
			vertexNormals.add(p);
		}
		else if (starter.equals("vt")) {
			//vertex normal
			if (parts.length < 3)
				throw new MalformedObjFileException("Need 3 points for vertex normal");
			Vector3 p = new Vector3(Float.parseFloat(parts[1]), 
					Float.parseFloat(parts[2]), 0);
			if (parts.length == 4) {
				//w coordinate...?
				p.z = Float.parseFloat(parts[3]);
			}
			textureCoords.add(p);
		}
		else if (starter.equals("v")) {
			if (parts.length < 4)
				throw new MalformedObjFileException("Need 3 points for vertex");
			Point p = new Point(Float.parseFloat(parts[1]), 
					Float.parseFloat(parts[2]), Float.parseFloat(parts[3]));
			vertices.add(p);
		}
		else if (starter.equals("usemtl")) {
			BRDF bird = matLst.get(parts[1]);
			if (bird == null) {
				System.out.println("No material named " + parts[1]);
				MeshShape shape;
				shape = new MeshShape(parts[1]);
				meshes.add(0, shape);
				wobjs.add(0, new WorldObject(shape, BRDF.RED_DIFFUSE));
			}
			else {
				MeshShape shape;
				shape = new MeshShape(parts[1]);
				meshes.add(0, shape);
				wobjs.add(0, new WorldObject(shape, matLst.get(parts[1])));
			}
		}
		else if (starter.equals("f")) {
			
			if (meshes.size() == 0) {
				MeshShape shape;
				if (parts.length < 2)
					shape = new MeshShape();
				else
					shape = new MeshShape(parts[1]);
				meshes.add(0, shape);
				wobjs.add(0, new WorldObject(shape, null));
			}
			
			if (parts.length != 4)
				throw new MalformedObjFileException("Faces specified by more than 3 points not supported");
			
			String[] vdata0 = parts[1].split("/");
			String[] vdata1 = parts[2].split("/");
			String[] vdata2 = parts[3].split("/");
			
			boolean hasVNormals = vdata0.length > 2 && vdata1.length > 2 && vdata2.length > 2;
			boolean hasTexture = vdata0.length > 1 && vdata1.length > 1 && vdata1.length > 1
								&& !(vdata0[1].isEmpty())
								&& !(vdata1[1].isEmpty())
								&& !(vdata2[1].isEmpty());
			
			
			//triangle
			Point p0 = vertices.get(Integer.parseInt(vdata0[0]) - 1);
			Point p1 = vertices.get(Integer.parseInt(vdata1[0]) - 1);
			Point p2 = vertices.get(Integer.parseInt(vdata2[0]) - 1);
			
			Triangle t;
			if (hasVNormals) {
				Vector3 vn0 = vertexNormals.get(Integer.parseInt(vdata0[2]) - 1);
				Vector3 vn1 = vertexNormals.get(Integer.parseInt(vdata1[2]) - 1);
				Vector3 vn2 = vertexNormals.get(Integer.parseInt(vdata2[2]) - 1);
				t = new Triangle(p0, p1, p2, vn0, vn1, vn2);
			}
			else {
				t = new Triangle(p0, p1, p2);
			}
			if (hasTexture) {
				Vector3 vt0 = textureCoords.get(Integer.parseInt(vdata0[1]) - 1);
				Vector3 vt1 = textureCoords.get(Integer.parseInt(vdata1[1]) - 1);
				Vector3 vt2 = textureCoords.get(Integer.parseInt(vdata2[1]) - 1);
				t.addTextureCoordinates(vt0, vt1, vt2);
			}
			meshes.get(0).addTriangle(t);
		}
		else {
			//unknown - just skip
			//System.out.println("Unknown - " + starter);
		}
		
		
	}
	
	public static void readMtl(String fileName, HashMap<String, BRDF> matLst) {
		
		if (!fileName.endsWith(".mtl")) {
			fileName = fileName + ".mtl";
		}
		
		File matFile = new File(System.getProperty("user.dir") + "/" + fileName);
		
		if (!matFile.exists()) {
			System.out.println(fileName + " does not exist");
			return;
		}
		
		String last = null;
		
		try {
			BufferedReader mapReader = new BufferedReader(new FileReader(matFile));
			String line;
			while ((line = mapReader.readLine()) != null) {
				last = processMatLine(line, matLst, last);
			}
			mapReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: "+matFile.getAbsolutePath());
		} catch (IOException e) {
			System.out.println("IO Error!");
			e.printStackTrace();
		} catch (MalformedObjFileException e) {
			System.out.println("MTL file is malformed: "+e.getMessage());
			e.printStackTrace();
		}
		
		return;
	}
	
	public static String processMatLine(String line, HashMap<String, BRDF> matLst, String last) throws MalformedObjFileException {
		line = line.replaceAll("\t", " ");
		String[] parts = line.trim().replaceAll(" +"," ").split(" ");
		if (parts.length == 0) {
			return last;
		}
		String starter = parts[0];
		if (starter.equals("#")) {
			//this is a comment
		}
		else if (starter.equals("newmtl")) {
			if (parts.length < 2)
				throw new MalformedObjFileException("Materials need names");
			BRDF brdf = new BRDF();
			matLst.put(parts[1], brdf);
			return parts[1];
		}
		else if (starter.equals("Ns")) {
			if (parts.length < 2)
				throw new MalformedObjFileException("Ns invalid");
			matLst.get(last).ksp = Float.parseFloat(parts[1]);
		}
		else if (starter.equals("Ka")) {
			if (parts.length < 4)
				throw new MalformedObjFileException("Ns invalid");
			matLst.get(last).ka.r = Float.parseFloat(parts[1]);
			matLst.get(last).ka.g = Float.parseFloat(parts[2]);
			matLst.get(last).ka.b = Float.parseFloat(parts[3]);
		}
		else if (starter.equals("Kd")) {
			if (parts.length < 4)
				throw new MalformedObjFileException("Ns invalid");
			matLst.get(last).kd.r = Float.parseFloat(parts[1]);
			matLst.get(last).kd.g = Float.parseFloat(parts[2]);
			matLst.get(last).kd.b = Float.parseFloat(parts[3]);
		}
		else if (starter.equals("Ks")) {
			if (parts.length < 4)
				throw new MalformedObjFileException("Ns invalid");
			matLst.get(last).ks.r = Float.parseFloat(parts[1]);
			matLst.get(last).ks.g = Float.parseFloat(parts[2]);
			matLst.get(last).ks.b = Float.parseFloat(parts[3]);
		}
		else if (starter.equals("map_Kd")) {
			if (parts.length < 2)
				throw new MalformedObjFileException("Need resource name");
			BufferedImage texture = readImageFile(parts[1]);
			if (texture != null) {
				matLst.get(last).texture = texture;
				matLst.get(last).useTexture = true;
			}
		}
		else {
			//not supported:
			//ni - refraction index
			//d - dissolve factor (transprency)
			//illum - disable only diffuse/specular etc - dont 'need
		}
		
		return last;
	}
}

class MalformedObjFileException extends Exception {
	private static final long serialVersionUID = 1482925568742570298L;
	public MalformedObjFileException(String string) {
		super(string);
	}
}
