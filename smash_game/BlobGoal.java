package assignment3;

import java.awt.Color;

public class BlobGoal extends Goal{

	public BlobGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {

		Color[][] board_colors = board.flatten();
		int count = 0;
		boolean[][] visited = new boolean[board_colors.length][board_colors[0].length];
		for(int i = 0; i < board_colors.length; i++){
			for(int j = 0; j < board_colors[0].length; j++){
				if(!visited[i][j]){
					int blobSize = this.undiscoveredBlobSize(i, j, board_colors, visited);
					count = Math.max(count, blobSize);
				}
			}
		}

		return count;
	}

	@Override
	public String description() {
		return "Create the largest connected blob of " + GameColors.colorToString(targetGoal) 
		+ " blocks, anywhere within the block";
	}


	public int undiscoveredBlobSize(int i, int j, Color[][] unitCells, boolean[][] visited) {
		int count = 0;
		int[][] queue = new int[unitCells.length * unitCells[0].length][2];
		int front = 0, rear = 0;

		if (visited[i][j] || unitCells[i][j] != this.targetGoal) {
			return count;
		}

		queue[rear][0] = i;
		queue[rear][1] = j;
		rear++;
		visited[i][j] = true;

		while (front != rear) {
			int[] current = queue[front];
			front++;
			count++;

			for (int[] direction : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
				int x = current[0] + direction[0];
				int y = current[1] + direction[1];

				if (x < 0 || x >= unitCells.length || y < 0 || y >= unitCells[0].length) {
					continue;
				}

				if (!visited[x][y] && unitCells[x][y] == this.targetGoal) {
					visited[x][y] = true;
					queue[rear][0] = x;
					queue[rear][1] = y;
					rear++;
				}
			}
		}

		return count;
	}

}

