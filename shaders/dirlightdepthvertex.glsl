#version 330

layout (location = 0) in vec4 inPosition;
layout (location = 1) in vec3 inNormal;
layout (location = 2) in vec4 inColour;
layout (location = 3) in vec2 inTexcoord;

out VS_OUT {
	vec4 position;
	vec2 texcoord;
} vs_out;

uniform mat4 model;
uniform mat4 lightMatrix;

void main(void) {
	gl_Position = lightMatrix * model * inPosition;
	
	vs_out.position = gl_Position;
	vs_out.texcoord = inTexcoord;
}