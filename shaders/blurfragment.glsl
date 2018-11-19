#version 330 core

out vec4 fragColor;
  
in vec2 passTexcoord;

uniform sampler2D to_blur;

uniform float blur;
uniform vec2 dir;

void main()
{             
    vec4 colour = vec4(0.0);
    
    colour += texture(to_blur, vec2(passTexcoord.x - 4.0*blur*dir.x, passTexcoord.y - 4.0*blur*dir.y)) * 0.0162162162;
    colour += texture(to_blur, vec2(passTexcoord.x - 3.0*blur*dir.x, passTexcoord.y - 3.0*blur*dir.y)) * 0.0540540541;
    colour += texture(to_blur, vec2(passTexcoord.x - 2.0*blur*dir.x, passTexcoord.y - 2.0*blur*dir.y)) * 0.1216216216;
    colour += texture(to_blur, vec2(passTexcoord.x - 1.0*blur*dir.x, passTexcoord.y - 1.0*blur*dir.y)) * 0.1945945946;
    
    colour += texture(to_blur, vec2(passTexcoord.x, passTexcoord.y)) * 0.2270270270;
    
    colour += texture(to_blur, vec2(passTexcoord.x + 1.0*blur*dir.x, passTexcoord.y + 1.0*blur*dir.y)) * 0.1945945946;
    colour += texture(to_blur, vec2(passTexcoord.x + 2.0*blur*dir.x, passTexcoord.y + 2.0*blur*dir.y)) * 0.1216216216;
    colour += texture(to_blur, vec2(passTexcoord.x + 3.0*blur*dir.x, passTexcoord.y + 3.0*blur*dir.y)) * 0.0540540541;
    colour += texture(to_blur, vec2(passTexcoord.x + 4.0*blur*dir.x, passTexcoord.y + 4.0*blur*dir.y)) * 0.0162162162;
    
    fragColor = vec4(colour.rgb, 1.0);
}  