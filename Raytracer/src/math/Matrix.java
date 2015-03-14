package math;

public class Matrix {
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
		matrix = new float[4][4];
	}
	// Determinant of 3x3 matrix
	public float determinant() {
		if(matrix.length == 3) {
			float det = matrix[0][0] * (matrix[1][1]*matrix[2][2] - matrix[1][2]*matrix[2][1]);
			det -= matrix[0][1] * (matrix[1][0]*matrix[2][2] - matrix[1][2]*matrix[2][0]);
			det += matrix[0][2] * (matrix[1][0]*matrix[2][1] - matrix[1][1]*matrix[2][0]);
			return det;
		}
		else {
			System.out.println("not a 3x3 matrix");
			return 1.0f;
		}
	}
	public static void main(String[]args) {
		float[][]mat = {{1f, 2f, 3f}, {0f, -4f, 1f}, {0f, 3f, -1f}};
		Matrix matrix = new Matrix(mat);
		System.out.print(matrix.determinant());
	}
}
