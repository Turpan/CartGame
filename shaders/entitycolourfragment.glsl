uniform float gamma;

in VS_OUT {
	vec4 colour;
	vec3 normal;
	vec2 texcoord;
	vec3 fragPosition;
	vec4 dirLightSpacePosition;
} fs_in;

out vec4 outColour;

vec4 gammaCorrection(vec4 fragColour) {
	fragColour.rgb = pow(fragColour.rgb, vec3(1.0/gamma));
	return fragColour;
}

void main(void) {
	vec4 fragColour = fs_in.colour;
	if (fragColour.a <= 0.0) {
		discard;
	}

	outColour = fragColour;
	
	outColour = gammaCorrection(outColour);
}