#version 330

in vec4 passColour;
in vec2 passTexcoord;

out vec4 outColour;

void main(void) {
	outColour = passColour;
}