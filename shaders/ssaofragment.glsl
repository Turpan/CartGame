#version 330

#define SAMPLES 64

uniform sampler2D position_texture;
uniform sampler2D normal_texture;
uniform sampler2D noise_texture;

uniform vec2 noise_scale;
uniform vec3 samples[SAMPLES];

uniform mat4 view;
uniform mat4 projection;

in vec2 texcoord;

out vec4 outColour;

void main(void) {
	const int SAMPLECOUNT = SAMPLES;
	const float RADIUS = 0.5;
	const float BIAS = 0.025;

	vec4 position = vec4(texture(position_texture, texcoord).rgb, 1.0);
	vec3 normal = texture(normal_texture, texcoord).rgb;
	vec3 noise = texture(noise_texture, texcoord * noise_scale).rgb;
	
	//position = projection * view * position;
	//position.xyz /= position.w;
	position = view * position;
	normal = vec4(view * vec4(normal, 0.0)).xyz;
	
	vec3 tangent = normalize(noise - normal * dot(noise, normal));
	vec3 bitangent = cross(normal, tangent);
	mat3 tbn_mat = mat3(tangent, bitangent, normal);
	
	float occlusion = 0.0;
	for (int i=0; i<SAMPLECOUNT; i++) {
		vec3 sample = tbn_mat * samples[i];
		sample = vec3(position.xyz) + (sample * RADIUS);
		
		vec4 offset = vec4(sample, 1.0);
		offset = projection * offset;
		offset.xyz /= offset.w;
		offset.xyz = (offset.xyz * 0.5) + 0.5;
		
		vec4 sample_position = vec4(texture(position_texture, offset.xy).rgb, 1.0);
		sample_position = view * sample_position;
		
		float sample_depth = sample_position.z;
		
		//float range_check = smoothstep(0.0, 1.0, RADIUS / abs(position.z - sample_depth));
		//occlusion += ((sample_depth >= sample.z + BIAS) ? 1.0 : 0.0) * range_check;
		
		occlusion += ((sample_depth >= sample.z + BIAS) ? 1.0 : 0.0);
	}
	
	//occlusion = 1.0 - (occlusion / SAMPLECOUNT);
	occlusion = (occlusion / 64);
	outColour = vec4(occlusion, vec3(1.0));
}