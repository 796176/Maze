import java.io.IOException;

public class Maze {
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

	}

	public void render() {

	}

	public void handleInput(char in) {

	}
}
