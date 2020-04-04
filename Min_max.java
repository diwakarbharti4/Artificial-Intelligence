import java.util.* ;

public class Min_max {
	static Scanner sc = new Scanner(System. in );
	static char start;
	static int choose;

	static int mini(int m, int sticks_choose, int alpha, int beta, char turn) {
		if (m <= 0 && turn == 'A') {
			if (start == 'A') {
				choose = sticks_choose;
			}
			return 10;
		}

		if (m <= 0 && turn == 'B') {
			if (start == 'B') {
				choose = sticks_choose;
			}
			return - 10;
		}

		if (turn == 'A') {
			int value = -100;
			int eval;
			for (int i = 1; i <= 3; i++) {
				eval = mini(m - i, sticks_choose, alpha, beta, 'B');
				value = Math.max(value, eval);
				alpha = Math.max(alpha, eval);
				if (beta <= alpha) break;
			}
			return value;
		}
		else {
			int value1 = 100;
			int eval1;
			for (int j = 1; j <= 3; j++) {
				eval1 = mini(m - j, sticks_choose, alpha, beta, 'A');
				value1 = Math.min(value1, eval1);
				beta = Math.min(beta, eval1);
				if (beta <= alpha) break;
			}
			return value1;
		}
	}

	static char evaluate_AI_AI(int n, char first_turn) {
		int value = 0;
		int x = 0;
		char curr_turn = first_turn;
		System.out.println("(Remaining sticks: " + n + ")");
		while (n > 0) {
			if (curr_turn == 'A') {
				value = -100;
				for (int i = 1; i <= 3; i++) {
					if (n == 1) {
						choose = 1;
						break;
					}
					start = 'A';
					x = mini(n - i, i, -100, 100, 'B');
					if (x > value) {
						value = x;
					}
				}
				n = n - choose;
				System.out.println("Player A chooses: " + choose + "\n");
				System.out.println("(Remaining sticks: " + n + ")");
				curr_turn = 'B';
			}
			else {
				value = 100;
				for (int i = 1; i <= 3; i++) {
					if (n == 1) {
						choose = 1;
						break;
					}
					start = 'B';
					x = mini(n - i, i, -100, 100, 'A');
					if (x < value) {
						value = x;
					}
				}
				n = n - choose;
				System.out.println("Player B chooses: " + choose + "\n");
				System.out.println("(Remaining sticks: " + n + ")");
				curr_turn = 'A';
			}
			choose = 0;
			try {
				Thread.sleep(2000);
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		return curr_turn;
	}

	static char evaluate_AI_human(int n, char first_turn) {
		int value = 0;
		int x = 0;
		char curr_turn = first_turn;
		System.out.println("(Remaining sticks: " + n + ")");
		while (n > 0) {
			if (curr_turn == 'A') {
				value = -100;
				for (int i = 1; i <= 3; i++) {
					if (n == 1) {
						choose = 1;
						break;
					}
					start = 'A';
					x = mini(n - i, i, -100, 100, 'B');
					if (x > value) {
						value = x;
					}
				}
				n = n - choose;
				System.out.println("Player A chooses: " + choose + "\n");
				System.out.println("(Remaining sticks: " + n + ")");
				curr_turn = 'B';
			}
			else {
				System.out.print("Player B chooses: ");
				choose = sc.nextInt();
				if (choose > 3) {
					while (choose > 3) {
						System.out.println("INVALID MOVE\nCHOOSE AGAIN\n");
						System.out.print("Player B chooses: ");
						choose = sc.nextInt();
					}
				}
				start = 'B';
				n = n - choose;
				System.out.println("(Remaining sticks: " + n + ")");
				curr_turn = 'A';
			}
			choose = 0;
			try {
				Thread.sleep(2000);
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		return curr_turn;
	}

	public static void main(String[] args) {
		System.out.println("\n\n********Game Begins********\n\n");
		System.out.println("***Enter the mode.***\n1. AI vs AI\n2. AI vs Human\n\n");
		System.out.print("MODE: ");
		int k = sc.nextInt();

		System.out.print("\nEnter the no. of sticks: ");
		int n = sc.nextInt();
		Random rand = new Random();

		int turn = rand.nextInt(2);
		try {
			if (k == 1) {
				System.out.println("\n\nPlayer A is AI and Player B is AI\n\n");
				if (turn == 1) {
					System.out.println("\n\n--------Player A's turn first--------\n\n");
					System.out.println("----------------------------------------------\n");
					System.out.println("----------------------------------------------\n");
					Thread.sleep(2000);
					System.out.println("\n\n*******Player " + evaluate_AI_AI(n, 'A') + " wins*******\n\n");
				}
				if (turn == 0) {
					System.out.println("\n\n--------Player B's turn first--------\n\n");
					System.out.println("-------------------------------------\n");
					System.out.println("----------------------------------------------\n");
					Thread.sleep(2000);
					System.out.println("\n\n*******Player " + evaluate_AI_AI(n, 'B') + " wins*******\n\n");
				}
			}
			if (k == 2) {
				System.out.println("\n\nPlayer A is AI and Player B is Human\n\n");
				if (turn == 1) {
					System.out.println("\n\n--------Player A's turn first--------\n\n");
					Thread.sleep(2000);
					System.out.println("\n\n*******Player " + evaluate_AI_human(n, 'A') + " wins*******\n\n");
				}
				if (turn == 0) {
					System.out.println("\n\n--------Player B's turn first--------\n\n");
					System.out.println("\n\n*******Player " + evaluate_AI_human(n, 'B') + " wins*******\n\n");
				}
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
}
