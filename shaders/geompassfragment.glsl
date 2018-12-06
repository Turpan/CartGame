#version 330

in VS_OUT {
	vec4 colour;
	vec3 normal;
	vec2 texcoord;
	vec3 position;
} fs_in;

layout (location = 0) out vec4 position;
layout (location = 1) out vec4 normal;
layout (location = 2) out vec4 albedo;

uniform sampler2D texture_diffuse;

void main(void) {

	vec4 colour = texture(texture_diffuse, fs_in.texcoord);
	if (colour.a <= 0.0) {
		discard;
	}
	
	position = vec4(fs_in.position, 1.0);
	//position = fs_in.position;
	
	normal = vec4(normalize(fs_in.normal), 1.0);
	//normal = normalize(fs_in.normal);
	
	albedo = vec4(colour.rgb, 1.0);
}