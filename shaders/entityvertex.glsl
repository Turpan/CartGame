#version 330

layout (location = 0) in vec4 inPosition;
layout (location = 1) in vec3 inNormal;
layout (location = 2) in vec4 inColour;
layout (location = 3) in vec2 inTexcoord;

out VS_OUT {
	vec4 colour;
	vec3 normal;
	vec2 texcoord;
	vec3 fragPosition;
	vec4 dirLightSpacePosition;
} vs_out;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform mat4 dirLightMatrix;

void main(void) {
	gl_Position = projection * view * model * inPosition;
	
	vs_out.colour = inColour;
	vs_out.normal = vec3(vec4(inNormal, 0.0) * model);
	vs_out.texcoord = inTexcoord;
	vs_out.fragPosition = vec3(model * inPosition);
	vs_out.dirLightSpacePosition = dirLightMatrix * vec4(vs_out.fragPosition, 1.0);
}