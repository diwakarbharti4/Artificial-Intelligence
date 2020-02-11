import java.util.*;

* State Space:- An 8X8 matrix with any arrangement of n<=8 queens
* Initial State:- No queens on the board(i.e., all entries are null)
*                               - - - - - - - -
*                               - - - - - - - -
*                               - - - - - - - -
*                               - - - - - - - -
*                               - - - - - - - -
*                               - - - - - - - -
*                               - - - - - - - -
*                               - - - - - - - -
* Transition Operator:- Add a new queen in an empty row
* Goal state:- 8 Queens placed on the board such that all queens are in non-attacking position
* For example,
*                               - - Q - - - - -
*                               - - - - - Q - -
*                               - - - - - - - Q
*                               Q - - - - - - -
*                               - - - Q - - - -
*                               - - - - - - Q -
*                               - - - - Q - - -
*                               - Q - - - - - -
* Cost:- One per queen placed
* Heuristic:- Number of pairs of Queens on board in attacking position
*
*           Submitted by:- Diwakar Bharti
*           Roll no:- 1801059
*           B.Tech 2nd Year CSE
*
public class EightQ
{
        int SIZE = 8;
        int opt_cost = SIZE;

        long factorial(int x)
        {
                long fact = 1;
                for(int i = 1;i<=x;i++)
                        fact*=x;
                return fact;
        }

        long comb(int a, int b)
        {
                return factorial(a)/(factorial(b)*factorial(a-b));
        }

        ChessBoard initialise()
        {
                String arr = "";
                for(int i=0;i<SIZE;i++)
                        for(int j=0;j<SIZE;j++)
                                arr+="0";
                return new ChessBoard(arr, 0, 0);
        }

        int find(String arr)
        {
                int i;
                for(i=0;i<SIZE;i++)
                {
                        int flag = 0;
                        for(int j=0;j<SIZE;j++)
                        {
                                if(arr.charAt(SIZE*i+j) == '1')
                                {
                                        flag = 1;
                                        break;
                                }
                        }
                        if(flag == 0)
                                return i;
                }
                return i;
        }

        long gen_heuristic(String arr)
        {
                long conf = 0;
                for(int i=0;i<SIZE;i++)
                {
                        int sum_row = 0;
                        int sum_col = 0;
                        for(int j=0;j<SIZE;j++)
                        {
                                sum_row+=(int)arr.charAt(SIZE*i+j)-48;
                                sum_col+=(int)arr.charAt(SIZE*j+i)-48;
                        }
                        conf+=comb(sum_row, 2) + comb(sum_col, 2);
                }
                for(int i=0;i<SIZE;i++)
                {
                        int sum_left = 0;
                        int sum_right = 0;
                        for(int j=0;j+i<SIZE;j++)
                        {
                                sum_left+=(int)arr.charAt(SIZE*j+j+i)-48;
                                sum_right+=(int)arr.charAt(SIZE*(j+i)+j)-48;
                        }
                        conf+=comb(sum_left, 2) + comb(sum_right, 2);
                }
                for(int i=0;i<2*SIZE-1;i++)
                {
                        int sum_left = 0;
                        int sum_right = 0;
                        if(i<SIZE)
                        {
                                for(int j=0;i-j>=0;j++)
                                {
                                        sum_left+=(int)arr.charAt(SIZE*j+i-j)-48;
                                }
                        }
                        else
                        {
                                for(int j=i-SIZE+1;j<SIZE;j++)
                                {
                                        sum_right+=(int)arr.charAt(SIZE*j+i-j)-48;
                                }
                        }
                        conf+=comb(sum_left, 2) + comb(sum_right, 2);
                }
                return conf;
        }

        void display(String arr)
        {
                System.out.print(" ");
                for(int i=0;i<SIZE;i++)
                        System.out.print("__ ");
                System.out.println();
                for(int i=0;i<SIZE;i++)
                {
                        System.out.print("|");
                        for(int j=0;j<SIZE;j++)
                        {
                                if(arr.charAt(SIZE*i+j)=='1')System.out.print("██|");
                                else System.out.print("__|");
                        }
                        System.out.println();
                }
        }

        public static void main(String args[])
        {
                EightQ obj = new EightQ();
                ChessBoard sol = obj.initialise();

                PriorityQueue<ChessBoard> ucs = new PriorityQueue<ChessBoard>(100, new BoardComparator());
                ucs.add(sol);

                int count = 1;

                System.out.println("//The black regions represent a queen at that place\n\n");

                while(!(ucs.isEmpty()))
                {

                        ChessBoard chs = ucs.poll();
                        String ans = chs.getBoard();


                        if(chs.getCost() + chs.getHeuristic() > obj.opt_cost)continue;


                        int i = obj.find(ans);


                        if(i==obj.SIZE)
                        {
                                System.out.println("========Solution "+count+"========");
                                obj.display(ans);
                                System.out.println("===========================\n");
                                count++;
                        }
                        else
                        {
                                //explore the current node and enqueue it
                                for(int j=0;j<obj.SIZE;j++)
                                {
                                        ans = ans.substring(0, obj.SIZE*i+j)+"1"+ans.substring(obj.SIZE*i+j+1);
                                        ucs.offer(new ChessBoard(ans, chs.getCost()+1, obj.gen_heuristic(ans)));
                                        ans = chs.getBoard();
                                }
                        }
                }
        }
}

class BoardComparator implements Comparator<ChessBoard>
{
        public int compare(ChessBoard c1, ChessBoard c2)
        {
                if (c1.getCost() + c1.getHeuristic() > c2.getCost() + c2.getHeuristic())
                        return 1;
                else if (c1.getCost() + c1.getHeuristic() < c2.getCost() + c2.getHeuristic())
                        return -1;
                return 0;
        }
}

class ChessBoard
{
        private String board;
        private int cost;
        private long heuristic;

        public ChessBoard(String board, int cost, long heuristic)
        {
                this.cost = cost;
                this.board = board;
                this.heuristic = heuristic;
        }

        int getCost()
        {
                return this.cost;
        }
        String getBoard()
        {
                return this.board;
        }
        long getHeuristic()
        {
                return this.heuristic;
        }

        void setCost(int cost)
        {
                this.cost = cost;
        }
        void setBoard(String board)
        {
                this.board = board;
        }
        void setHeuristic(long heuristic)
        {
                this.heuristic = heuristic;
        }
}
