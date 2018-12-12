#version 330

uniform sampler2D render;

uniform float gamma;

uniform float exposure;

in vec2 texcoord;

out vec4 outColour;

vec4 hdrCorrection(vec4 fragColour) {
	vec3 hdr = fragColour.rgb;
	
	//vec3 ldr = hdr / (hdr + vec3(1.0));
	vec3 ldr = vec3(1.0) - exp(-hdr * exposure);
	
	return vec4(ldr, 1.0);
}

vec4 gammaCorrection(vec4 fragColour) {
	fragColour.rgb = pow(fragColour.rgb, vec3(1.0/gamma));
	return fragColour;
}

void main(void) {
	outColour = vec4(texture(render, texcoord).rgb, 1.0);
	outColour = hdrCorrection(outColour);
	outColour = gammaCorrection(outColour);
}