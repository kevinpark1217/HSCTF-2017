
#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <bitset>

#define vi vector<int>
#define vs vector<string>
#define pb push_back
#define mod 10000

using namespace std;

ifstream fin ("grid.txt");

int never[1010][1510]; //never moved down
int once[1010][1510]; //moved down once
bool block[1010][1510]; //cannot move here

int M,N,K;

int main(){
	fin >> M >> N >> K;
	for (int i=0; i<K; i++){
		int a, b;
		fin >> a >> b;
		block[a][b]=true;
	}
	never[0][0]=1;
	for (int i=0; i<=M; i++){
		for (int j=0; j<=N; j++){
			if (block[i][j]){continue;}
			if (i>0){never[i][j]+=never[i-1][j];}
			if (j>0){never[i][j]+=never[i][j-1];}
			never[i][j]%=mod;
		}
	}
	for (int i=0; i<=M; i++){
		for (int j=0; j<=N; j++){
			if (block[i][j]){continue;}
			if (i>0){once[i][j]+=once[i-1][j];}
			if (j>0){once[i][j]+=once[i][j-1];}
			if (j<N){once[i][j]+=never[i][j+1];}
			once[i][j]%=mod;
		}
	}
	cout << (never[M][N]+once[M][N])%mod << endl;
}