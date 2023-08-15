package dataStructure.array;

import java.util.Scanner;

public class DieGame
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        int beginner = 85, step = 5, remain = 10, deathCount = 1, stepCount = 0;
        int n = input.nextInt();
        boolean player[] = new boolean[n];
        for (int i = 0; i < n; i++)
            player[i] = true;

        for(int i = beginner; n - deathCount > remain; i = (i+1) % n)
        {
            if (player[i])
            {
                stepCount++;
                if(stepCount == step)
                {
                    player[i] = false;
                    stepCount = 0;
                    deathCount++;
                }
            }
        }

        System.out.println("Remaining player numbers are: ");
        for (int i = 0; i < n; i++)
            if (player[i])
                System.out.print(i+" ");
    }
}