#include <stdio.h>

void function(char *str) {
  printf(str);
  printf("Goodbye.\n");
}

int main() {
  setbuf(stdout, NULL); // so that we can see output immediately
  char buf[128];
  printf("Welcome to pointless broken program #93884. Enter something: ");
  fgets(buf, 128, stdin);
  function(buf);
  return 0;
}
