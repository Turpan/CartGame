#version 330

uniform sampler2D texture_diffuse;

in VS_OUT {
	vec4 colour;
	vec2 texcoord;
} fs_in;

out vec4 outColour;

uniform int colourSelector;

vec4 useColour() {
	return fs_in.colour;
}

vec4 useTexture() {
	return texture(texture_diffuse, fs_in.texcoord);
}

vec4 getColour() {
	if (colourSelector == 1) {
		return useColour();
	} else {
		return useTexture();
	}
}

void main(void) {
	outColour = getColour();
}