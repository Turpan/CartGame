#version 330

uniform sampler2D ssao_texture;

in vec2 texcoord;

out vec4 outColour;

void main(void) {
	vec2 texelSize = 1.0/ vec2(textureSize(ssao_texture, 0));
	float result = 0.0;
	
	for (int x = -2; x < 2; ++x) {
		for (int y = -2; y < 2; ++y) {
			vec2 offset = vec2(float(x), float(y)) * texelSize;
			result += texture(ssao_texture, texcoord + offset).r;
		}
	}
	
	result /= (4.0 * 4.0);
	
	outColour = vec4(result, vec3(1.0));
	//outColour = texture(ssao_texture, texcoord);
	//outColour = vec4(1.0);
}