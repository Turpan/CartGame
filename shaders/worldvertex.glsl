#version 330

layout (location = 0) in vec4 inPosition;
layout (location = 1) in vec3 inNormal;
layout (location = 2) in vec4 inColour;
layout (location = 3) in vec2 inTexcoord;

out vec4 passColour;
out vec2 passTexcoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main(void) {
	gl_Position = projection * view * model * inPosition;
	//gl_Position = inPosition;
	
	passColour = inColour;
	passTexcoord = inTexcoord;
}