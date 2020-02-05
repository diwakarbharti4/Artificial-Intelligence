import java.util.*;

 // Submitted by ---> Diwakar Bharti
 // Roll number---> 1801059

 //  State Space:- An 8X8 matrix with any arrangement of n<=8 queens

 // Initial State:- No queens on the board(i.e., all entries are null)
 //                              1-- 0 0 0 0 0 0 0 0
 //                              2-- 0 0 0 0 0 0 0 0
 //                              3-- 0 0 0 0 0 0 0 0
 //                              4-- 0 0 0 0 0 0 0 0
 //                              5-- 0 0 0 0 0 0 0 0
 //                              6-- 0 0 0 0 0 0 0 0
 //                              7-- 0 0 0 0 0 0 0 0
 //                              8-- 0 0 0 0 0 0 0 0
 //                                  | | | | | | | |
 //                                  a b c d e f g h

 // Transition Operator:- Add a new queen in an empty row, such that it will not attack anyother queens

 // Goal state:- 8 Queens placed on the board such that all queens are in non-attacking position
 // For example,
 //                              1-- 0 0 Q 0 0 0 0 0
 //                              2-- 0 0 0 0 0 0 Q 0
 //                              3-- 0 0 0 0 0 0 0 Q
 //                              4-- Q 0 0 0 0 0 0 0
 //                              5-- 0 0 0 Q 0 0 0 0
 //                              6-- 0 0 0 0 0 0 Q 0
 //                              7-- 0 0 0 0 Q 0 0 0
 //                              8-- 0 Q 0 0 0 0 0 0
 //                                  | | | | | | | |
 //                                  a b c d e f g h

 // Cost:- One per move

public class EightQueens
{
        int i,j,x=8;
        void display(String A)
        {
                for(i=0;i<8;i++)
                {
                        System.out.print(i+1+" ");
                        for(j=0;j<8;j++)
                        {
                                if(A.charAt(8*i+j)=='1')
                                {
                                        System.out.print("|Q|");
                                }
                                else
                                {
                                        System.out.print("|_|");
                                }
                        }
                        System.out.println();
                }
                System.out.println("   1  2  3  4  5  6  7  8");
        }




        //Creating initial state
        Function Create()
        {
                String A="";

                for(i=0;i<8;i++)
                {
                        for(j=0;j<8;j++)
                        {
                                A=A+"0";
                        }
                }
                return new Function(A,0);
        }

        //Find the row where to insert the queen
        int find(String A)
        {
                int i,j;
                for(i=0;i<8;i++)
                {
                        int f=0;
                        for(j=0;j<8;j++)
                        {
                                if(A.charAt(8*i+j)=='1')
                                {
                                        f=1;
                                        break;
                                }
                        }
                        if(f==0)
                                return i;
                }
                return i;
        }

        //check if current state is valid
        boolean Error(String A)
        {
                int left,right;
                for(i=0;i<8;i++)
                {
                        int r=0,c=0;

                        for(j=0;j<8;j++)
                        {
                                r=r+(int)A.charAt(8*i+j)-48;
                                c=c+(int)A.charAt(8*j+i)-48;
                        }
                        if(r>1||c>1)
                                return false;
                }
                for(i=0;i<8;i++)
                {
                        left=0;
                        right=0;

                        for(j=0;j+i<8;j++)
                        {
                                left=left+(int)A.charAt(8*j+j+i)-48;
                                right=right+(int)A.charAt(8*(j+i)+j)-48;
                        }
                        if(left>1||right>1)
                                return false;
                }
                for(i=0;i<2*8-1;i++)
                {
                        left=0;
                        right=0;
                        if(i<8)
                        {
                                for(j=0;i-j>=0;j++)
                                {
                                        left=left+(int)A.charAt(8*j+i-j)-48;
                                }
                        }
                        else
                        {
                                for(j=i-7;j<8;j++)
                                {
                                        right=right+(int)A.charAt(8*j+i-j)-48;
                                }
                        }
                        if(left>1||right>1)
                                return false;
                }
                return true;
        }

        //driver function
        public static void main(String args[])
        {
                EightQueens obj = new EightQueens();
                Function sol = obj.Create();
                int i,j;
                PriorityQueue<Function> ucs = new PriorityQueue<Function>(100, new BoardComparator());
                ucs.add(sol);

                int count=1;

                while(!(ucs.isEmpty()))
                {
                        //dequeue head of the queue
                        Function chs=ucs.poll();
                        String ans=chs.getBoard();

                        //check if the current state is valid
                        if(!(obj.Error(ans)))continue;

                        //find where to insert the queen
                        i =obj.find(ans);

                        //if the state is valid and solution is complete
                        if(i==obj.x)
                        {
                                System.out.println("----Solution "+count+"----");
                                obj.display(ans);
                                System.out.println("-------------------\n");
                                count++;
                        }
                        else
                        {
                                //explore the current node and enqueue it
                                for(j=0;j<obj.x;j++)
                                {
                                        ans = ans.substring(0, obj.x*i+j)+"1"+ans.substring(obj.x*i+j+1);
                                        ucs.offer(new Function(ans, chs.getCost()+1));
                                        //ans = ans.substring(0, obj.SIZE*i+j)+"0"+ans.substring(obj.SIZE*i+j+1);
                                        ans=chs.getBoard();
                                }
                        }

                }
        }
}

class BoardComparator implements Comparator<Function>
{
        public int compare(Function c1, Function c2)
        {
                if (c1.getCost()>c2.getCost())
                        return 1;
                else if (c1.getCost()<c2.getCost())
                        return -1;
                return 0;
        }
}

class Function
{
        private String board;
        private int cost;

        public Function(String board, int cost)
        {
                this.cost = cost;
                this.board = board;
        }

        int getCost()
        {
                return this.cost;
        }
        String getBoard()
        {
                return this.board;
        }

        void setCost(int cost)
        {
                this.cost = cost;
        }
        void setBoard(String board)
        {
                this.board = board;
        }
}