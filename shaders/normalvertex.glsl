#version 330 core

layout (location = 0) in vec4 inPosition;
layout (location = 1) in vec3 inNormal;
layout (location = 2) in vec4 inColour;
layout (location = 3) in vec2 inTexcoord;

out VS_OUT {
	vec3 normal;
} vs_out;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
	gl_Position = projection * view * model * inPosition;
	
	mat3 normalMatrix = mat3(transpose(inverse(view * model)));
	
	vs_out.normal = normalize(vec3(projection * vec4(normalMatrix * inNormal, 0.0)));
}