#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <bitset>

#define vi vector<int>
#define vs vector<string>
#define pb push_back

using namespace std;

int grid[405][4005];
bool isprime[4006];
int mod=1000000009;
int ans=0;

int main(){
	for (int i=2; i<4006; i++){
		isprime[i]=true;
	}
	isprime[1]=false;
	for (int i=2; i<4006; i++){
		if (!isprime[i]){continue;}
		for (int j=i*2; j<4006; j+=i){
			isprime[j]=false;
		}
	}
	for (int i=1; i<10; i++){
		grid[1][i]=1;
	}
	for (int len=2; len<=400; len++){
		for (int dig=0; dig<10; dig++){
			for (int r=1; r<4005; r++){
				grid[len][r+dig]+=grid[len-1][r];
				grid[len][r+dig]%=mod;
			}
		}
	}
	for (int len=21; len<=400; len++){
		for (int sum=2; sum<=4006; sum++){
			if (isprime[sum]){
				ans=(ans+grid[len][sum])%mod;
			}
		}
	}
	cout << ans << endl;
}