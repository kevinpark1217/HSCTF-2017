#include <iostream>
#include <cstring>
#include <cstdint>

const size_t EXPLOIT_SIZE = 6;

void exploit()
{
	system("/bin/cat ./flag.txt");
}

int main()
{
	setbuf(stdout, NULL);
	
	std::string in = "";
	std::cin >> in;

	uint8_t *shellcode = new uint8_t[EXPLOIT_SIZE];
	memcpy(shellcode, in.c_str(), EXPLOIT_SIZE);
	
	(reinterpret_cast<void(*)(void)>(shellcode))();

	return 0;
}