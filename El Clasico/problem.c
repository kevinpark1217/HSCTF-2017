#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

bool isCool() {
  char name[32];
  int i;
  printf("Enter your name: ");
  gets(name);
  bool ret = false;
  for(i = 0; i < strlen(name); i++) {
    ret |= (name[i] ^ name[i]) != 0;
  }
  return ret;
}

int main() {
  setbuf(stdout, NULL); // so that we can see output immediately
  printf("Are you cool? We'll use your name to find out.\n");
  if(isCool()) {
    printf("Cool people get a shell!\n");
    system("/bin/sh");
  } else {
    printf("Sorry, you're not cool enough to get a shell. :(\n");
  }
  return 0;
}
