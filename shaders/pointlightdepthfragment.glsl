#version 330 core

uniform sampler2D texture_diffuse;

in VS_OUT {
	vec4 position;
	vec2 texcoord;
} fs_in;

layout(location = 0) out vec4 colour;

uniform vec3 lightPosition;
uniform float farPlane;
uniform bool softshadow;

void main() {
	vec4 fragColour = texture(texture_diffuse, fs_in.texcoord);
	
	if (fragColour.a <= 0.0) {
		discard;
	}

	float lightDistance = length(fs_in.position.xyz - lightPosition);
	
	lightDistance = lightDistance / farPlane;
	
	gl_FragDepth = lightDistance;
	
	float moment2 = 0.0;
	
	if (softshadow) {
		moment2 = lightDistance * lightDistance;
	}
	
	colour = vec4(lightDistance, moment2, 1.0, 1.0);
}