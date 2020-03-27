package genetic;

import java.util.*;

class chromosome
{
	int [] a;
	int [] group;
	int [] marks;
	int size;
	int sum;
	chromosome(int n)
	{
		a = new int[n];
		marks = new int[n];
		group = new int[3];
		size = n;
	}
}

class comp implements Comparator<chromosome>
{
	public int compare(chromosome c1, chromosome c2)
	{
		if(c1.sum > c2.sum)
			return 1;
		else if(c1.sum < c2.sum)
			return -1;
		else return 0;
	}
}

public class genetic
{
	static int initial_population = 20;
	static Random rand = new Random();
	static int generation = 1000;
	static int t = 0;
	static int max_population = 200;

	int calculate(chromosome c1, int i, int gp, int n)
	{
		int sum = 0, diff;
		for(int j=i;j<n;j++)
		{
			if(c1.a[j] == gp){
				diff = c1.marks[i] - c1.marks[j];
				sum = sum + Math.abs(diff);
			}
		}
		return sum;
	}
	
	static chromosome diverse(chromosome c1, int n)
	{
		genetic g = new genetic();
		for(int i=0;i<n;i++)
		{

			if(c1.a[i] == 1){
				c1.group[0] += g.calculate(c1,i,1,n);
			}
			else if(c1.a[i] == 2){
				c1.group[1] += g.calculate(c1,i,2,n);
			}
			else{
				c1.group[2] += g.calculate(c1,i,3,n);
			}
		}
		c1.sum = c1.group[0] + c1.group[1] + c1.group[2];
		return c1;
	}

	static Vector<chromosome> mating(Vector<chromosome> population ,int n)
	{
		while(true)
		{
			int i;
			chromosome one = population.get(rand.nextInt(initial_population));
			chromosome two = population.get(rand.nextInt(initial_population));
		
			if(one != two)
			{
				chromosome child1 = new chromosome(n);
				chromosome child2 = new chromosome(n);
				int p = rand.nextInt(n) + 1;
				
				for(i = 0; i<p;i++)
				{
					child1.a[i] = one.a[i];
					child1.marks[i] = one.marks[i];
					child2.a[i] = two.a[i];
					child2.marks[i] = two.marks[i];
				}
				for(i = p;i<n;i++)
				{	
					child1.a[i] = two.a[i];
					child1.marks[i] = two.marks[i];
					child2.a[i] = one.a[i];
					child2.marks[i] = one.marks[i];
				}
			
				if(rand.nextDouble() < 0.01)/*mutation*/
				{
					child1 = mutation(child1,n);
					child2 = mutation(child2,n);
				}
				
				/*System.out.print("child: ");
				print_chromosome(child1);
				System.out.print("child: ");
				print_chromosome(child2);
				System.out.println();*/
				child1 = diverse(child1,n);
				child2 = diverse(child2,n);
				if(initial_population <= max_population-2)
				{
					population.add(child1);
					population.add(child2);
					initial_population += 2;
				}
				else{
					if(initial_population == max_population-1){
						population.remove(ifpopulation_full(population));
						population.add(child1);
						population.add(child2);
					}
					else{
						population.remove(ifpopulation_full(population));
						population.remove(ifpopulation_full(population));
						population.add(child1);
						population.add(child2);
					}
				}
				break;
			}
		}
		return population;
	}

	static chromosome ifpopulation_full(Vector<chromosome> v1)
	{
		Collections.sort(v1,new comp());
		return v1.lastElement();
	}

	static chromosome mutation(chromosome child, int n)
	{
		int p = rand.nextInt(n);
		int q = rand.nextInt(3) + 1;
		child.a[p] = q;
		return child;
	}

	static void print_chromosome(chromosome chro)
	{
		int i;
		for(i = 0; i<chro.size;i++)
		{
			System.out.print(chro.a[i] + " ");
		}
		System.out.println();
		System.out.print("marks: ");
		for(i=0;i<chro.size;i++)
		{
			System.out.print(chro.marks[i] + " ");
		}
		System.out.println();
		System.out.print("diverse: ");
		for(i=0;i<3;i++)
		{
			System.out.print(chro.group[i] + " ");
		}
		System.out.println("\n");
		System.out.println("sum: " + chro.sum);
		System.out.println("\n");

	}

	static void fittest_parent(Vector<chromosome> v1)
	{
		Collections.sort(v1,new comp());
		print_chromosome(v1.firstElement());
	}
	static chromosome give_marks(chromosome c1, int[] mark)
	{
		for(int i=0;i<c1.size;i++){
			c1.marks[i] = mark[i];
		}
		return c1;
	}

	public static void main(String [] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number of students: ");
		int n = sc.nextInt();
		int[] mark = new int[n];
		int i;
		for(i=0;i<n;i++)
			mark[i] = rand.nextInt(101);

		
		Vector<chromosome> population = new Vector<chromosome>();
		
		for(int j=0;j<initial_population;j++)
		{
			chromosome cm = new chromosome(n);
			for(i=0;i<n;i++)
			{
				cm.a[i] = rand.nextInt(3) + 1;
			}
			cm = give_marks(cm,mark);
			cm = diverse(cm,n);
			population.add(cm);
		}
		int p = 1;
		while(generation != 0){
			population = mating(population,n);
			System.out.println("generation: " + p);
			p++;
			fittest_parent(population);
			generation--;
		}
		Collections.sort(population,new comp());
		System.out.println("\n\n");
		
	}
}