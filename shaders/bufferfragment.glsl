#version 330

uniform sampler2D render;

uniform float gamma;

in vec2 texcoord;

out vec4 outColour;

vec4 gammaCorrection(vec4 fragColour) {
	fragColour.rgb = pow(fragColour.rgb, vec3(1.0/gamma));
	return fragColour;
}

void main(void) {
	outColour = vec4(texture(render, texcoord).rgb, 1.0);
	outColour = gammaCorrection(outColour);
}