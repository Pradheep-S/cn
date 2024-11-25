#include <stdio.h>
#include <string.h>
int main()
{
    char input[100][100], stuffed[200][100];
    int number;
    printf("Enter the number of frames: ");
    scanf("%d", &number);
    printf("Enter the frames:\n");
    for (int i = 0; i < number; i++)
    {
        scanf("%s", input[i]);
    }
    int j = 0;
    for (int i = 0; i < number; i++)
    {
        if (strcmp(input[i], "flag") == 0 || strcmp(input[i], "esc") == 0)
        {
            strcpy(stuffed[j++], "esc");
        }
        strcpy(stuffed[j++], input[i]);
    }
    for (int i = 0; i < j; i++)
    {
        printf("%s ", stuffed[i]);
    }
}
