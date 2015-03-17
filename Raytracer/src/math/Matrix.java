package math;

public class Matrix {
	
	private static float[][] tempMatrix = new float[4][4];
	private static float[] tempVectorA = new float[4];
	private static float[] tempVectorB = new float[4];
	
	public float[][]matrix;
	
	
	public Matrix(float[][]matrix) {
		this.matrix = new float[matrix.length][matrix[0].length];
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				this.matrix[i][j] = matrix[i][j];
			}
		}
	}
	public Matrix() {
		this(4);
	}
	public Matrix(int dim) {
		matrix = new float[dim][dim];
	}
	public void set(float [][] matrix) {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				this.matrix[i][j] = matrix[i][j];
			}
		}
	}
	public void clear() {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				this.matrix[i][j] = 0;
			}
		}
	}
	public void setIdentity() {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				this.matrix[i][j] = (i == j) ? 1 : 0;
			}
		}
	}
	
	public Matrix add(Matrix src, float b) {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				src.matrix[i][j] = this.matrix[i][j] + b;
			}
		}
		return src;
	}
	public Matrix add(Matrix src, Matrix b) {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				src.matrix[i][j] = this.matrix[i][j] + b.matrix[i][j];
			}
		}
		return src;
	}
	public Matrix scale(Matrix src, float b) {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				src.matrix[i][j] = this.matrix[i][j] * b;
			}
		}
		return src;
	}
	
	public Point multiply(Point src, Point a) {
		//c_i1 = A_ij*x_j1
		tempVectorA[0] = a.x;
		tempVectorA[1] = a.y;
		tempVectorA[2] = a.z;
		tempVectorA[3] = 1;
		
		for (int i = 0; i < this.matrix.length; i++) {
			tempVectorB[i] = 0;
			for (int j = 0; j < tempVectorA.length; j++) {
				tempVectorB[i] += this.matrix[i][j] * tempVectorA[j];
			}
		}
		src.x = tempVectorB[0] / tempVectorB[3];
		src.y = tempVectorB[1] / tempVectorB[3];
		src.z = tempVectorB[2] / tempVectorB[3];
		return src;
	}
	
	public void multiply(Matrix src, Matrix b) {
		//c_np = a_n1 b_1p + a_n2 b_2p + ... +a_nm b_mp
		for (int n = 0; n < this.matrix.length; n++) {
			for (int p = 0; p < b.matrix[0].length; p++) {
				tempMatrix[n][p] = 0;
				for (int j = 0; j < b.matrix.length; j++) {
					tempMatrix[n][p] += this.matrix[n][j] * b.matrix[j][p];
				}
			}
		}
		src.set(tempMatrix);
	}
	
	// Determinant of 3x3 matrix
	public float determinant() {
		if(matrix.length == 3) {
			float det = matrix[0][0] * (matrix[1][1]*matrix[2][2] - matrix[1][2]*matrix[2][1]);
			det -= matrix[0][1] * (matrix[1][0]*matrix[2][2] - matrix[1][2]*matrix[2][0]);
			det += matrix[0][2] * (matrix[1][0]*matrix[2][1] - matrix[1][1]*matrix[2][0]);
			return det;
		}
		else if(matrix.length == 4) {
			float det = matrix[0][0]*matrix[1][1]*matrix[2][2]*matrix[3][3];
			det += matrix[0][0]*matrix[1][2]*matrix[2][3]*matrix[3][1];
			det += matrix[0][0]*matrix[1][3]*matrix[2][1]*matrix[3][2];
			det += matrix[0][1]*matrix[1][0]*matrix[2][3]*matrix[3][2];
			det += matrix[0][1]*matrix[1][2]*matrix[2][0]*matrix[3][3];
			det += matrix[0][1]*matrix[1][3]*matrix[2][2]*matrix[3][0];
			det += matrix[0][2]*matrix[1][0]*matrix[2][1]*matrix[3][3];
			det += matrix[0][2]*matrix[1][1]*matrix[2][3]*matrix[3][0];
			det += matrix[0][2]*matrix[1][3]*matrix[2][0]*matrix[3][1];
			det += matrix[0][3]*matrix[1][0]*matrix[2][2]*matrix[3][1];
			det += matrix[0][3]*matrix[1][1]*matrix[2][0]*matrix[3][2];
			det += matrix[0][3]*matrix[1][2]*matrix[2][1]*matrix[3][0];
			det -= matrix[0][0]*matrix[1][1]*matrix[2][3]*matrix[3][2];
			det -= matrix[0][0]*matrix[1][2]*matrix[2][1]*matrix[3][3];
			det -= matrix[0][0]*matrix[1][3]*matrix[2][2]*matrix[3][1];
			det -= matrix[0][1]*matrix[1][0]*matrix[2][2]*matrix[3][3];
			det -= matrix[0][1]*matrix[1][2]*matrix[2][3]*matrix[3][0];
			det -= matrix[0][1]*matrix[1][3]*matrix[2][0]*matrix[3][2];
			det -= matrix[0][2]*matrix[1][0]*matrix[2][3]*matrix[3][1];
			det -= matrix[0][2]*matrix[1][1]*matrix[2][0]*matrix[3][3];
			det -= matrix[0][2]*matrix[1][3]*matrix[2][1]*matrix[3][0];
			det -= matrix[0][3]*matrix[1][0]*matrix[2][1]*matrix[3][2];
			det -= matrix[0][3]*matrix[1][1]*matrix[2][2]*matrix[3][1];
			det -= matrix[0][3]*matrix[1][2]*matrix[2][0]*matrix[3][1];
			return det;
		}
		else {
			System.out.println("not a good matrix");
			return 0.0f;
		}
	}
	
	public String toString() {
		String mat = "[";
		for(int i = 0; i < matrix.length; i++) {
			mat += "[";
			for(int j = 0; j < matrix[0].length; j++) {
				mat += this.matrix[i][j]+",";
			}
			if (i < matrix.length - 1)
				mat += "]\n";
		}
		return mat + "]]";
	}
	
	public static void main(String[]args) {
		
		//test determinant
		float[][]mat = {{1f, 2f, 3f}, {0f, -4f, 1f}, {0f, 3f, -1f}};
		Matrix matrix = new Matrix(mat);
		System.out.println(matrix.determinant());
		
		//test setIdentity
		Matrix a = new Matrix(4);
		a.setIdentity();
		System.out.println(a);
		
		Matrix test = new Matrix(4);
		float[][]temp = {{3f, 0f, 1f, 5f}, {2f, 0f, 1f, 4f}, {0f, 6f, 9f, 0f}, {4f, 2f, 0f, 5f}};
		test.set(temp);
		//test determinant()
		System.out.println(test.determinant());
		//should be -42
		
		//test matrix multiplication
		Matrix b = new Matrix(4);
		float[][]matA = {{1, 2, 3, 4}, {0, -4, 1, 2}, {0, 3, 2, 6}, {7, 2, -4, 8}};
		float[][]matB = {{6, 3, -2, 3}, {8, 1, 0, 6}, {-2, 4, 3, 1}, {0, -5, 7, 6}};
		a.set(matA);
		b.set(matB);
		
		Matrix c = new Matrix(4);
		a.multiply(c, b);
		System.out.println("Multiplying AB = ");
		System.out.println(c);
		//should equal {{16, -3, 35, 42}, {-34, -10, 17, -11}, {20, -19, 48, 56}, {66, -33, 30, 77}}
		
	}
}
