package assignment3;

import java.awt.Color;

public class PerimeterGoal extends Goal{

	public PerimeterGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {


		Color[][] colors = board.flatten();
		int result = 0;
		int row = colors.length;
		int column = colors[0].length;
		for (Color[] color : colors) {
			if (color[0] == this.targetGoal) result++;
			if (color[column - 1] == this.targetGoal) result++;
		}
		for(int j = 0; j < column; j++) {
			if(colors[0][j] == this.targetGoal) result++;
			if(colors[row-1][j] == this.targetGoal) result++;
		}
		
		return result;
	}

	@Override
	public String description() {
		return "Place the highest number of " + GameColors.colorToString(targetGoal) 
		+ " unit cells along the outer perimeter of the board. Corner cell result twice toward the final score!";
	}

}

