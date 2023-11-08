import java.io.IOException;

public class Maze {
	private int MAZE_HEIGHT;
	final private int DEFAULT_MAZE_HEIGHT = 15;
	private int MAZE_WIDTH;
	final private int DEFAULT_MAZE_WIDTH = 30;
	private int[][] maze;
	private int jonesPosition;
	private String lastFrame;

	public static void main(String[] args) throws IOException {
		Maze globalMaze = new Maze();
		globalMaze.configure(args);
		char consoleInput = 'q';

		while_loop:
		{
			while (true) {
				globalMaze.render();
				try {
					consoleInput = (char) System.in.read();
				} catch (Exception ignore) {
				}
				switch (consoleInput) {
					case 'h':
						System.out.println(
								"h print this message\n" +
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
		for (int row = 0; row < MAZE_HEIGHT; row++) {
			for (int column = 0; column <= MAZE_WIDTH; column++) {
				if (column == MAZE_WIDTH) {
					System.out.print('\n');
					continue;
				}
				switch (maze[row][column]) {
					case 0:
						System.out.print('#');
						break;
					case 1:
						System.out.print(' ');
						break;
					case 2:
						System.out.print('0');
				}
			}
		}
		System.out.println("end");
	}

	public void handleInput(char in) {

	}

	private void generate() {

	}
}
