#include <stdio.h>
#include <stdbool.h>
#include <conio.h>
#include <time.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

char *people[6] = 
{
    "sarah",
    "ali",
    "nazanin",
    "hosein",
    "shirin",
    "ahmad"
};

char *devices[6] = 
{
    "knife",
    "stone",
    "cyanide",
    "car",
    "gun",
    "rod"
};

struct detective
{
    int current; //current place on the board . will be initilized to 0

    int personCard;
    int placeCard;
    int deviceCard;
};

struct detective detectives[5];
int correct[3];
int places[6]; //must be initialized randomly

int dice();
void run();
void checkPlace(int);
void choosePlace(int);
void chooseUserPlace(int);
void showCards();
void move(int y);
void userMove(int y);
void userGuessOrPass(int y);
void guessOrPass(int y);
void initilize();
void shuffle ( int arr[], int n );
bool checkGuess(char[], char[], int, int);
bool checkForWin(char[], char[], int);

int main()
{
    run();
}

void run()
{
    initilize();
    showCards();
    fflush(stdout);
    sleep(5);
    int i = 0;
    while (1)
    {
        system("cls");
        i == 0 ? userMove(i) : move(i);
        if(i<4) 
			i++;
        else 
			i=0;
    }  
}

void initilize()
{   
	srand(time(NULL));
	for (int i = 0; i < 6; ++i)
    {
        int x;
        x = rand()%45+1;
        places[i] = x;
    }

	int a[] = {0,1,2,3,4,5};
	shuffle(a,6);
	
	correct[0] = a[0];
	for(int i = 0; i < 5; i++){
		detectives[i].current = 0;
		detectives[i].personCard = a[i+1];
	}
	
	shuffle(a,6);
	correct[1] = a[1];
	for(int i = 0; i < 5; i++){
		detectives[i].deviceCard = a[i+1];
	}
	
	shuffle(a,6);
	correct[1] = a[2];
	for(int i = 0; i < 5; i++){
		detectives[i].placeCard = a[i+1];
	}
}

void swap (int *a, int *b)
{
    int temp = *a;
    *a = *b;
    *b = temp;
}

void shuffle ( int arr[], int n )
{
    srand(time(NULL));
 
    for (int i = n-1; i > 0; i--)
    {
        int j = rand() % (i+1);
        swap(&arr[i], &arr[j]);
    }
}

int dice()
{
    srand(time(NULL));
    int random = rand()%6+1;
    return random;
}

void checkPlace(int y)
{
    for(int i = 0; i < 5; i++)
	{
        if(i != y){
            if(detectives[i].current != 4 && detectives[y].current != 0 && detectives[i].current == detectives[y].current){
                printf("\noh no someone is already here! need to start over %i:(\n",i);
                printf("current place : %i\n\n",detectives[y].current);
                detectives[y].current = 0;
            }
        } else {
        	for(int j = 0; j < 6; j++){
        		if(places[j] == detectives[y].current){
        			y == 0 ? userGuessOrPass(y) : guessOrPass(y);
				}
			}     
        }
    }
}

void userMove(int y)
{
    printf("your turn:\n\tcurrent place : %i\n\npress d to roll a dice!\n",detectives[y].current);
    if(detectives[y].current == 0){
        printf("you are at place 0! you need to roll a 4 in order to move!\n");
    }
    bool flag = true;
    while (flag)
    {
        char a = getche();
        if(a == 'd'){
            int random = dice();
            if(detectives[y].current == 0){
                if (random == 4)
                {
                    printf("\n\nnice!");
                        detectives[y].current += random;
                    if (detectives[y].current > 44){
                        chooseUserPlace(y);
                    } else {
                        printf("\n\nyou rolled %i!\ncurrent place : %i\n\n",random,detectives[y].current);
                    }
                    checkPlace(y);
                    fflush(stdout);
                    sleep(4);
                    
                } else {
                    printf("\n\nsad:(");
                   
                    printf("\nyou rolled %i!\ncurrent place : %i\n\n",random,detectives[y].current); 
                    fflush(stdout);
                    sleep(4); 
                }
            } else {
                detectives[y].current += random;
                if (detectives[y].current > 44){
                    chooseUserPlace(y);
                } else {
                    printf("\n\nyou rolled %i!\ncurrent place : %i\n\n",random,detectives[y].current);
                }
                
                checkPlace(y);
                fflush(stdout);
                sleep(4);
            }
            
            flag = false;
        }
    }   
}

void chooseUserPlace(int y)
{
	printf("the road ahead is blocked! Choose the next location that you wanna start from:");
	int x;
	scanf("%d",&x);
	
	detectives[y].current = x;
	
	checkPlace(y);
}

void choosePlace(int y)
{
	printf("\nThe road ahead is blocked! Choose the next location that you wanna start from:\n");
	srand(time(NULL));
	int x;
	
	x = rand()%45+1;
		
	detectives[y].current = x;
	printf("\nDetective #5d choose place: %d to start from:\n",y,x);
	checkPlace(y);
	fflush(stdout);
	sleep(3);
}

void move(int y)
{
    printf("\ndetective #%i turn:\n\tcurrent place : %i\n",y+1,detectives[y].current);
    if(detectives[y].current == 0){
        printf("\ndetective #%i is at place 0! he/she needs to roll a 4 in order to move!\n",y+1);
    }

    int random = dice();
    if(detectives[y].current == 0){
        if (random == 4)
        {
            printf("nice!\n");
            detectives[y].current += random;
            if (detectives[y].current > 44)
			{
                choosePlace(y);
            } 
			else {
                printf("\n\ndetective #%i rolled %i!\ncurrent place : %i\n\n",y+1,random,detectives[y].current);
            }
            checkPlace(y);
        
            fflush(stdout);
            sleep(4);
        } else {
            printf("\n\nsad :(");
            
            printf("\ndetective #%i rolled %i!\ncurrent place : %i\n\n",y+1,random,detectives[y].current);
            
            fflush(stdout);
            sleep(4);
        }
    } else {
        detectives[y].current += random;
        if (detectives[y].current > 44)
		{
            choosePlace(y);
        } 
		else {
            printf("\n\ndetective #%i rolled %i!\ncurrent place : %i\n",y+1,random,detectives[y].current);
        }
        
        checkPlace(y);
        sleep(4);
    } 
}

void showCards()
{
	printf("\nyour cards\n\tperson : %s\n\tdevice : %s\n\tplace  : %i\n", people[detectives[0].personCard], devices[detectives[0].deviceCard], places[detectives[0].placeCard]);
}

void guessOrPass(int y)
{
    system("cls");
    int i = dice();
    
    int j = dice();
    
    //show guess
    printf("detective #%i geuss are : %s %s %d",y, people[i], devices[j], detectives[y].current);  
    fflush(stdout);
    sleep(3);
    
	if(checkGuess(people[i], devices[j], detectives[y].current, y))
    {
    	if(checkForWin(people[i], devices[j], detectives[y].current))
    	{
    		printf("detective #%i won the game",y);
			printf("case info was:\n\tmurderer : %s\n\tdevice   : %s\n\tplace    : %i\n",people[correct[0]], devices[correct[1]], places[correct[2]]);
			printf("/nbetter luck next time!");
			exit(0);
		}
		else
	    {
	    	printf("\n\t\tGame over\n");
			printf("detective #%i lost the game",y);
			printf("case info was:\n\tmurderer : %s\n\tdevice   : %s\n\tplace    : %i\n",people[correct[0]], devices[correct[1]], places[correct[2]]);
			exit(0);
		}
	}
	else
	{
		printf("\n\noops! your guess was wrong");
		fflush(stdout);
		sleep(2);
		return;
	}
}

void userGuessOrPass(int y)
{
    printf("\nDo you wanna make a guess?(y/n)\n");
    char person[10];
    char device[10];
    int place;
    while (1)
    {
        char a = getche();
        if(a == 'y')
		{
            showCards();
            bool flag = true;
            while (flag)
            {
                printf("\nPlease enter a name: ");
                scanf("%s",&person);
                for (int i = 0; i < 6; i++)
                {
                    if (strcmp(person,people[i]) == 0)
                    {
                        flag = false;
                        break;
                    }
                }
                fflush(stdin);
                if(flag) 
					printf("Person does not exist!\n"); 
            }
            flag = true;
            while (flag)
            {
                printf("\nPlease enter a device: ");
                scanf("%s",&device);
                for (int i = 0; i < 6; i++)
                {
                    if (strcmp(device,devices[i]) == 0)
                    {
                        flag = false;
                        break;
                    }
                } 
				fflush(stdin); 
				if(flag) 
					printf("Device does not exist!\n"); 
            }
            place = detectives[y].current;
        
		    if(checkGuess(person, device, place, y))
		    {
		    	if(checkForWin(person, device, place))
		    	{
		    		system("cls");
					printf("\n\t\tWell done, you won!\n");
					printf("detective #%i qon the game",y);
					printf("case info was:\n\tmurderer : %s\n\tdevice   : %s\n\tplace    : %i\n",people[correct[0]], devices[correct[1]], places[correct[2]]);
					exit(0);
				}
				else
			    {
			    	system("cls");
					printf("\n\t\tGame over\n");
					printf("detective #%i lost the game",y);
					printf("case info was:\n\tmurderer : %s\n\tdevice   : %s\n\tplace    : %i\n",people[correct[0]], devices[correct[1]], places[correct[2]]);
					exit(0);
				}
			}
			else
			{
				printf("\noops! your guess was wrong");
				sleep(1);
				return; }
		} else if (a == 'n'){
				fflush(stdin);
    			return;
		}
    }     	
}

bool checkGuess(char person[], char device[], int place,int y)
{
    for(int i = 0 ; i < 5 ; i++)
    {
    	system("cls");
    	if(i != y && strcmp(people[detectives[i].personCard] ,person) == 0)
    	{
    		//show cards
    
    		printf("\nI am detective #%i and Your Wrong! My cards are:\n",i);
    		printf("\n\n\tPerson: %s \n\tDevice: %s \n\tplace: %d", people[detectives[i].personCard], devices[detectives[i].deviceCard], places[detectives[i].placeCard]);
    		return false;
		}		

		else if(i != y && strcmp(devices[detectives[i].deviceCard] ,device) == 0)
		{
			
			printf("\nI am detective #%i and Your Wrong! My cards are:\n",i);
    		printf("\n\n\tPerson: %s \n\tDevice: %s \n\tplace: %d", people[detectives[i].personCard], devices[detectives[i].deviceCard], places[detectives[i].placeCard]);
    		return false;
		}
		
		else if(i != y && places[detectives[i].placeCard] == place)
		{

			printf("\nI am detective #%i and Your Wrong! My cards are:\n", i);
    		printf("\n\n\tPerson: %s \n\tDevice: %s \n\tplace: %d", people[detectives[i].personCard], devices[detectives[i].deviceCard], places[detectives[i].placeCard]);
    		return false;
		}		
	}
	return true;
}

bool checkForWin(char person[], char device[], int place)
{
	if(correct[0] == place && strcmp(correct[1],person) == 0 && strcmp(correct[2],device) == 0)
		return true;

	return false;
}