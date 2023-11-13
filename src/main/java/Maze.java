import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Maze {
	private int MAZE_HEIGHT;
	final private int DEFAULT_MAZE_HEIGHT = 15;
	private int MAZE_WIDTH;
	final private int DEFAULT_MAZE_WIDTH = 30;
	private int[][] maze;
	private int[] jonesPosition;
	private String lastFrame = "";

	public static void main(String[] args) throws IOException {
		Maze globalMaze = new Maze();
		globalMaze.configure(args);
		char consoleInput = 'q';

		while_loop:
		{
			while (true) {
				globalMaze.render();

				try {
					consoleInput = (char) System.in.readNBytes(2)[0];
				} catch (Exception ignore) {}

				switch (consoleInput) {
					case 'm':
						System.out.println(
								"m print this message\n" +
								"q quit\n" +
								"s or j go down\n" +
								"w or k go up\n" +
								"a or h go left\n" +
								"d or l go right"
						);
						break;
					case 'q':
						break while_loop;
					default:
						globalMaze.handleInput(consoleInput);
						break;
				}
			}
		}
	}

	public void configure(String[] args) {
		if (args != null && args.length == 2) {
			MAZE_HEIGHT = Integer.getInteger(args[0]);
			MAZE_WIDTH = Integer.getInteger(args[1]);

			if (MAZE_WIDTH >= 15 && MAZE_WIDTH <= 60 && MAZE_HEIGHT >= 15 && MAZE_HEIGHT <= 25) {
				generate();
				return;
			}
		}

		MAZE_HEIGHT = DEFAULT_MAZE_HEIGHT;
		MAZE_WIDTH = DEFAULT_MAZE_WIDTH;
		generate();
	}

	public void render() {
		lastFrame = "";
		for (int row = 0; row < MAZE_HEIGHT; row++) {
			for (int column = 0; column <= MAZE_WIDTH; column++) {
				if (column == MAZE_WIDTH) {
					lastFrame += '\n';
					continue;
				}

				if (column == jonesPosition[1] && row == jonesPosition[0]) {
					lastFrame += '0';
					continue;
				}

				switch (maze[row][column]) {
					case 0:
						lastFrame += '#';
						break;
					case 1:
						lastFrame += ' ';
						break;
				}
			}
		}
		System.out.println(lastFrame);
	}

	public void handleInput(char in) {
		int []allowed_chars = {'a', 'd', 'h', 'j', 'k', 'l', 's', 'w'};
		if (Arrays.binarySearch(allowed_chars, (int)in) < 0) {
			System.out.println(lastFrame);
			return;
		}
		boolean isPositionChanged;
		switch (in){
			case 'w':
			case 'k':
				isPositionChanged = moveUP();
				break;
			case 'd':
			case 'l':
				isPositionChanged = moveRight();
				break;
			case 's':
			case 'j':
				isPositionChanged = moveDown();
				break;
			default:
				isPositionChanged = moveLeft();
				break;
		}

		if (isPositionChanged) render(); else System.out.println(lastFrame);
	}

	private void generate() {
		maze = new int[MAZE_HEIGHT][MAZE_WIDTH];
		int currentPosition[] = jonesPosition = buildEntrance();
		int nextPosition[];

		do {
			do {
				nextPosition = generateNextPassagePosition(currentPosition);
			}while (isCorner(nextPosition));

			currentPosition = nextPosition;
			maze[currentPosition[0]][currentPosition[1]] = 1;
		}while (!isBorder(currentPosition));

		buildExit(currentPosition);
	}

	private int[] buildEntrance(){
		maze[MAZE_HEIGHT / 2][MAZE_WIDTH / 2] = 1;
		return new int[] {MAZE_HEIGHT / 2, MAZE_WIDTH / 2};
	}

	private boolean isBorder(int currentPosition[]){
		return currentPosition[0] == 1 || currentPosition[0] == MAZE_HEIGHT - 2 ||
				currentPosition[1] == 1 || currentPosition[1] == MAZE_WIDTH - 2;
	}

	private int[] generateNextPassagePosition(int[] currentPosition) {
		Random random = new Random();
		int position[] = new int[2];
		do {
			switch (random.nextInt(4)) {
				case 0:
					position[0] = currentPosition[0] - 1;
					position[1] = currentPosition[1];
					break;
				case 1:
					position[0] = currentPosition[0];
					position[1] = currentPosition[1] + 1;
					break;
				case 2:
					position[0] = currentPosition[0] + 1;
					position[1] = currentPosition[1];
					break;
				case 3:
					position[0] = currentPosition[0];
					position[1] = currentPosition[1] - 1;
					break;
			}
		} while (maze[position[0]][position[1]] == 1);
		return position;
	}

	private boolean isCorner(int[] position){
		return
				maze[position[0] - 1][position[1]] +
				maze[position[0]][position[1] + 1] +
				maze[position[0] + 1][position[1]] +
				maze[position[0]][position[1] - 1] > 1;
	}

	private void buildExit(int[] position) {
		if (position[0] == 1) maze[0][position[1]] = 1;
		else if (position[0] == MAZE_HEIGHT - 2) maze[MAZE_HEIGHT - 1][position[1]] = 1;
		else if (position[1] == 1) maze[position[0]][0] = 1;
		else maze[position[0]][MAZE_WIDTH - 1] = 1;

	}

	private boolean moveUP() {
		if (jonesPosition[0] == 0 || maze[jonesPosition[0] - 1][jonesPosition[1]] == 0) return false;
		jonesPosition[0]--;
		return true;
	}

	private boolean moveRight() {
		if (jonesPosition[1] == MAZE_WIDTH - 1 || maze[jonesPosition[0]][jonesPosition[1] + 1] == 0) return false;
		jonesPosition[1]++;
		return true;
	}

	private boolean moveDown() {
		if (jonesPosition[0] == MAZE_HEIGHT - 1 || maze[jonesPosition[0] + 1][jonesPosition[1]] == 0) return false;
		jonesPosition[0]++;
		return true;
	}

	private boolean moveLeft() {
		if (jonesPosition[1] == 0 || maze[jonesPosition[0]][jonesPosition[1] - 1] == 0) return false;
		jonesPosition[1]--;
		return true;
	}
}
