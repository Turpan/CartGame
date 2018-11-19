#version 330 core
out vec4 fragColor;
  
in vec2 passTexcoord;

uniform sampler2D depthMap;

void main()
{             
    float depthValue = texture(depthMap, passTexcoord).r;
    
    fragColor = vec4(vec3(depthValue), 1.0);
}  