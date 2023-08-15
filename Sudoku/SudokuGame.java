import java.util.*;

public class SudokuGame 
{
    public static void main(String[] arg)
    {
        System.out.println("Please enter the row and column of the sudoku: ");
        Scanner input = new Scanner(System.in);
        int row = input.nextInt();
        int column = input.nextInt();

        if(row != column)
        {
            System.out.println("row and column must be equal!");
            while(row != column)
            {
                row = input.nextInt();
                column = input.nextInt();
            }
        }

        int mat[][] = new int[row][column];
        Random random = new Random(); 

        for(int i = 0 ; i < row ; i++)
            for(int j = 0 ; j < column ; j++)
                mat[i][j] = 0;
        
        int tempMat[][] = new int[row][column];

        for(int i = 0 ; i < row ; i++)
        {
            for(int j = 0 ; j < column ; j++)
            {
                int value = ((int)(Math.random()*10));
                if(isSafe(value, tempMat, row))
                    mat[random.nextInt(row)][random.nextInt(row)] = value;
                tempMat[i][j] = value;
            }
        }
 
        System.out.println("Unsolved Sudoko: ");
        for(int i = 0 ; i < row ; i++)
        {
            for(int j = 0 ; j < column ; j++)   
                System.out.print(mat[i][j] + "  ");
            System.out.println();
        }
         
        System.out.println("Solved Sudoku: ");
        if (solveSudoku(mat, row))
        {
            for (int i = 0 ; i < row ; i++)
            {
                for (int j = 0 ; j < column ; j++)
                    System.out.print(mat[i][j] + "  ");
                System.out.println();
            }
        }
    }
    public static boolean solveSudoku(int matrix[][], int n) 
    {
        int subMatRow = 0;
        if(n % 2 == 0)
        {
            if(n % 8 == 0)
                subMatRow = 4;

            else if(n % 4 == 0)
                subMatRow = 2;

            else if(n % 2 == 0)
                subMatRow = 0;
        }

        else if(n % 2 == 1)
        {
            if(n % 3 == 0)
                subMatRow = 3;
            else if(n % 5 == 0)
                subMatRow = 5;
            else if(n % 7 == 0)
                subMatRow = 7;
        }

        int rowIndex = -1, columnIndex = -1;
        int i = 0, j = 0;
        for (i = 0 ; i < n ; i++)
        {
            for (j = 0 ; j < n ; j++)
            {
                if(matrix[i][j] == 0)
                {
                    rowIndex = i;
                    columnIndex = j;
                    break;
                }
            }
            if (rowIndex != -1)
                break;
        }
        if (i == n && j == n)
            return true;
        else
        {
            for (int value = 1 ; value < 10 ; value++)
            {
                if (isSafe(matrix, rowIndex, columnIndex, value, n, subMatRow))
                {
                    matrix[rowIndex][columnIndex] = value;
                    if(!solveSudoku(matrix,n))
                        matrix[rowIndex][columnIndex] = 0;
                    else
                        return true;
                }
            }
            return false;
        }
    }
    public static boolean isSafe(int matrix[][], int rowIndex, int columnIndex, int value, int n, int k)
    {
        //checking rows 
        for (int j = 0 ; j < n ; j++)
        {
            if (matrix[rowIndex][j] == value)
                return false;
        }

        //checking columns
        for (int i = 0 ; i < n ; i++)
        {
            if (matrix[i][columnIndex] == value)
                return false;
        }

        if(n % 2 == 1)
        {
            //checking subMatrix 
            int subRowIndex = rowIndex - (rowIndex % k);
            int subColumnIndex = columnIndex - (columnIndex % k);
            for (int i = subRowIndex ; i < subRowIndex + k ; i++)
            {
                for (int j = subColumnIndex ; j < subColumnIndex + k ; j++)
                {
                    if (matrix[i][j] == value)
                        return false;
                }
            }
        }
        
        return true;
    }

    //Overload method isSafe
    public static boolean isSafe(int value, int[][] mat, int n)
    {
        for(int i = 0 ; i < n ; i++)
        {
            for(int j = 0 ; j < n ; j++)
            {
                if(mat[i][j] == value)
                    return false;
            }
        }
        return true;
    }
}
