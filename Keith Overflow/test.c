#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <string.h>

typedef enum _access_level
{
	normal = 0,
	special = 0x0200B000,
	admin = 0x023FBA8B
} access_level;

char username[512];
access_level access = normal;

void overflow()
{
	if (access == admin)
	{
		system("/bin/cat ./flag.txt");
	}
}

int main()
{
	access_level accesslevel = 0;
	char password[512];

	memset(username, 0, 512);
	memset(password, 0, 512);

	setbuf(stdout, 0);

	printf("Username: ");
	fgets(username, 1024, stdin);
	printf("Password: ");
	fgets(password, 1024, stdin);

	access = accesslevel;
        printf("%x", access);
	return 0;
}
