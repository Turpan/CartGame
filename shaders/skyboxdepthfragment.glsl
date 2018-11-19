#version 330 core
out vec4 outColour;

in vec3 passTexcoords;

uniform sampler2D skybox;

vec2 xyzToUV(vec3 v) {
	float face;
	vec3 vAbs = abs(v);
	float ma;
	vec2 uv;
	
	if (vAbs.z >= vAbs.x && vAbs.z >= vAbs.y) {
		face = (v.z < 0.0) ? 5.0 : 4.0;
		ma = 0.5 / vAbs.z;
		uv = vec2((v.z < 0.0) ? -v.x : v.x, -v.y);
	} else if (vAbs.y >= vAbs.x) {
		face = (v.y < 0.0) ? 3.0 : 2.0;
		ma = 0.5 / vAbs.y;
		uv = vec2(v.x, (v.y < 0.0) ? -v.z : v.z);
	} else {
		face = (v.x < 0.0) ? 1.0 : 0.0;
		ma = 0.5 / vAbs.x;
		uv = vec2((v.x < 0.0) ? v.z : -v.z, -v.y);
	}
	
	uv = (uv * ma) + 0.5;
	uv.x = (uv.x + face) / 6.0;
	return uv;
}

void main() {    
	vec2 uv = xyzToUV(passTexcoords);
    float depthValue = texture(skybox, uv).r;
    
    outColour = vec4(vec3(depthValue), 1.0);
}