#include <stdio.h>
#include <windows.h>
#define N 3
int i, j, n = 3;
char s[N][3];
void display();
int isAvailable(int, int);

main()
{
	system("Color 84");
	int counter = 1;
	for (i = 0 ; i < n ; i++)
		for (j = 0 ; j < n ; j++)
			s[i][j] = '_';
	display();
	while(1)
	{
		int x, y,  flag = 0;
		if(counter % 2 != 0)
			printf("\nPlayer1 --> Enter the x and y: ");
		else
			printf("\nPlayer2 --> Enter the x and y: ");
		scanf("%d %d",&x,&y);
		while(isAvailable(x,y) != 1)
		{
			printf("\nEnter x and y: ");
			scanf("%d %d",&x,&y);
		}
		if(counter % 2 != 0)
			s[x-1][y-1] = 'X';
		else
			s[x-1][y-1] = 'O';
		
		for (i = 0; i < n ; i++)
		{
			if (s[i][0] == s[i][1] && s[i][0] == s[i][2] && s[i][0] != '_')
			{
				flag = 1;
				break;
			}
			if (s[0][i] == s[1][i] && s[0][i] == s[2][i] && s[0][i] != '_')
			{
				flag = 1;
				break;
			}
			if (s[i][i] == s[i+1][i+1] && s[i+1][i+1] == s[i+2][i+2] && s[i][i] != '_')
			{
				flag = 1;
				break;
			}
		}
		if (s[0][2] == s[1][1] && s[1][1] == s[2][0] && s[2][0] != '_')
		{
			flag = 1;
			break;
		}
		if (flag)
		{
			system("cls");
			display();
			if (counter % 2 != 0)
				printf("\nPlayer1 won the game.");
			else
				printf("\nPlayer2 won the game.");
			break;
		}
		system("cls");
		display();
		if (counter == 9)
		{
			printf("\nIt's a draw");
			break;
		}
		counter++;
	}
}

void display()
{
	printf("\n\n\t\t");
	for (i = 0 ; i < n ; i++)
		printf("\t %d ",i+1);

	for (i = 0 ; i < n ; i++)
	{
		printf("\n\n\t\t ");
		printf("%d",i+1);
		for (j = 0 ; j < n ; j++)
			printf("\t %c ",s[i][j]);
		printf("\n");
	}
	printf("\n");
}

int isAvailable(int x, int y)
{
	if (x > 3 || x < 1 || y > 3 || y < 1)
	{
		printf("\n%cx or y is out of range!\n",7);
		return 0;
	}
	else 
	{
		if (s[x-1][y-1] == '_')
			return 1;

		else{
			printf("\nYou have already selected this location.\n");
			return 0;
		}
	}
}

