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
}
