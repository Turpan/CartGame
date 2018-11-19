#version 330 core
out vec4 outColour;

in vec3 passTexcoords;

uniform samplerCube skybox;

void main()
{    
    outColour = texture(skybox, passTexcoords);
}