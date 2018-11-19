#version 330

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 texcoord;

out VS_OUT {
	out vec2 texcoord;
} vs_out;

void main(void) {
	gl_Position = position;
	
	vs_out.texcoord = texcoord;
}