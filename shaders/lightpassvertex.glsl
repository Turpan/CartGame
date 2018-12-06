#version 330

layout (location = 0) in vec4 inPosition;

out vec2 passTexcoord;

void main(void) {
	gl_Position = inPosition;
	
	passTexcoord = (vec2(inPosition.xy * 0.5)) + 0.5;
}