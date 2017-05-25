
kernel void ctf(global float* a, global float*b, global float* result, int const size) {
	const int itemId = get_global_id(0);
	if (itemId < size){
		float4 p = (float4)(4, 0, a[itemId], 3);
		float4 q = (float4)(8, b[itemId], -6, 7);
		float8 m = p.xwxyzyzy;
		float8 n = q.zyzwxyzw;
		float s = dot(m.even, n.lo);
		float t = dot(m.odd, n.hi);
		result[itemId] = s + t;
	}
}
