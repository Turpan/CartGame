#version 330

uniform sampler2D texture_diffuse;

uniform bool softshadows;

in VS_OUT {
	vec4 position;
	vec2 texcoord;
} fs_in;

layout(location = 0) out vec4 colour;

float calculateMoment2(float depth) {
	float moment2 = depth * depth;
	float dx = dFdx(depth);
	float dy = dFdy(depth);
	moment2 += 0.25*(dx*dx + dy*dy);
	return moment2;
}

void main(void) {
	vec4 fragColour = texture(texture_diffuse, fs_in.texcoord);
	
	if (fragColour.a <= 0.5) {
		discard;
	}
	
	float depth = fs_in.position.z / fs_in.position.w;
	depth = (depth * 0.5) + 0.5;
	
	float moment1 = depth;
	float moment2 = 0.0;
	
	if (softshadows) {
		moment2 = calculateMoment2(depth);
	}
	
	//Still have to output vec4, even if only two channels are written to
	colour = vec4(moment1, moment2, 1.0, 1.0);
}