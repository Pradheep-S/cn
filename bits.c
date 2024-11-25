#include <stdio.h>

int main(){
    char input[100], output[200];
    int count = 0, j = 0;
    printf("Enter the binary data: ");
    scanf("%s", input);

    for (int i = 0; input[i] != '\0'; i++){
        output[j++] = input[i];

        if (input[i] == '1'){
            count++;
            if (count == 5){
                output[j++] = '0'; 
                count = 0;         
            }
        }
        else{
            count = 0;
        }
    }
    output[j] = '\0';
    
    printf("After bit stuffing: %s\n", output);
    return 0;
}
