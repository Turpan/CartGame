#version 330

uniform sampler2D texture_diffuse;

in VS_OUT {
	vec2 texcoord;
} fs_in;

out vec4 outColour;

uniform vec4 textColour;

void main(void) {
	outColour = texture(texture_diffuse, fs_in.texcoord);
	outColour *= textColour;
	
	//outColour = vec4(1.0, 1.0, 0.0, 1.0);
}