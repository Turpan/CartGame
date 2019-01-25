#version 330

in vec2 texcoord;

uniform sampler2D background1;
uniform sampler2D background2;

uniform float blend;

out vec4 outColour;

void main(void) {
	float transition = blend;
    if (transition > 1.0) {
    	transition = 1.0;
    } else if (transition < 0.0) {
    	transition = 0.0;
    }
    
    vec3 sample1 = texture(background1, texcoord).xyz;
    vec3 sample2 = texture(background2, texcoord).xyz;
    
    sample1 *= transition;
    sample2 *= (1.0 - transition);
    
    outColour = vec4(sample1 + sample2, 1.0);
}