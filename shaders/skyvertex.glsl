#version 330 core
layout (location = 0) in vec4 inPos;

out vec3 passTexcoords;

uniform mat4 projection;
uniform mat4 view;

void main()
{
    passTexcoords = vec3(inPos);
    vec4 position = projection * view * inPos;
    gl_Position = position.xyww;
}