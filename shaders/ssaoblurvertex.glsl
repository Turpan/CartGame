#version 330

layout (location = 0) in vec4 inPosition;

out vec2 texcoord;

void main(void) {
	gl_Position = inPosition;
	
	texcoord = (vec2(inPosition.xy * 0.5)) + 0.5;
}