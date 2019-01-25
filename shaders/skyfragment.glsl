#version 330 core
out vec4 outColour;

in vec3 passTexcoords;

uniform samplerCube skybox1;
uniform samplerCube skybox2;

uniform float blend;

void main()
{    
    float transition = blend;
    if (transition > 1.0) {
    	transition = 1.0;
    } else if (transition < 0.0) {
    	transition = 0.0;
    }
    
    vec3 sample1 = texture(skybox1, passTexcoords).xyz;
    vec3 sample2 = texture(skybox2, passTexcoords).xyz;
    
    sample1 *= transition;
    sample2 *= (1.0 - transition);
    
    outColour = vec4(sample1 + sample2, 1.0);
}