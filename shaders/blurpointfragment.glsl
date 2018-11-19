#version 330 core

out vec4 fragColor;
  
in vec2 passTexcoord;

uniform sampler2D to_blur;

uniform float blur;
uniform vec2 dir;

vec2 wrapUV(vec2 offset) {
	int face;
	if (passTexcoord.x > 0.0) {
		face = int(ceil(passTexcoord.x * 6));
	} else {
		face = 0;
	}
	float range = 1.0 / 6.0;
	
	vec2 newuv = passTexcoord + offset;
	
	newuv.x = (newuv.x - (range * face)) * 6.0;
	float s = newuv.x;
	float t = newuv.y;
	
	if (s >= 1.0) {
		if (face == 0) {
			face = 5;
			newuv.x = s - 1.0;
		} else if (face == 1) {
			face = 4;
			newuv.x = s - 1.0;
		} else if (face == 2) {
			face = 0;
			newuv.x = 1.0 - t;
			newuv.y = s - 1.0;
		} else if (face == 3) {
			face = 0;
			newuv.x = t;
			newuv.y = 2.0 - s;
		} else if (face == 4) {
			face = 0;
			newuv.x = s - 1.0;
		} else if (face == 5) {
			face = 1;
			newuv.x = s - 1.0;
		}
	} else if (s <= 0.0) {
		if (face == 0) {
			face = 4;
			newuv.x = s + 1.0;
		} else if (face == 1) {
			face = 5;
			newuv.x = s + 1.0;
		} else if (face == 2) {
			face = 1;
			newuv.x = t;
			newuv.y = -s;
		} else if (face == 3) {
			face = 1;
			newuv.x = 1.0 - t;
			newuv.y = s + 1.0;
		} else if (face == 4) {
			face = 1;
			newuv.x = s + 1.0;
		} else if (face == 5) {
			face = 0;
			newuv.x = s + 1.0;
		}
	} else if (t >= 1.0) {
		if (face == 0) {
			face = 3;
			newuv.x = 2.0 - t;
			newuv.y = s;
		} else if (face == 1) {
			face = 3;
			newuv.x = t - 1.0;
			newuv.y = 1.0 - s;
		} else if (face == 2) {
			face = 4;
			newuv.y = t - 1.0;
		} else if (face == 3) {
			face = 5;
			newuv.x = 1.0 - s;
			newuv.y = 2.0 - t;
		} else if (face == 4) {
			face = 3;
			newuv.y = t - 1.0;
		} else if (face == 5) {
			face = 3;
			newuv.x = 1.0 - s;
			newuv.y = 2.0 - t;
		}
	} else if (t <= 0.0) {
		if (face == 0) {
			face = 2;
			newuv.x = 1.0 + t;
			newuv.y = 1.0 - s;
		} else if (face == 1) {
			face = 2;
			newuv.x = -t;
			newuv.y = s;
		} else if (face == 2) {
			face = 5;
			newuv.x = 1.0 - s;
			newuv.y = -t;
		} else if (face == 3) {
			face = 4;
			newuv.y = t + 1.0;
		} else if (face == 4) {
			face = 2;
			newuv.y = t + 1.0;
		} else if (face == 5) {
			face = 2;
			newuv.x = 1.0 - s;
			newuv.y = -t;
		}
	}
	
	newuv.x = (newuv.x / 6.0) + (face * range);
	
	return newuv;
}

void main()
{             
    vec4 colour = vec4(0.0);
    
    colour += texture(to_blur, wrapUV(vec2(-(4.0*blur*dir.x), -(4.0*blur*dir.y)))) * 0.0162162162;
    colour += texture(to_blur, wrapUV(vec2(-(3.0*blur*dir.x), -(3.0*blur*dir.y)))) * 0.0540540541;
    colour += texture(to_blur, wrapUV(vec2(-(2.0*blur*dir.x), -(2.0*blur*dir.y)))) * 0.1216216216;
    colour += texture(to_blur, wrapUV(vec2(-(1.0*blur*dir.x), -(1.0*blur*dir.y)))) * 0.1945945946;
    
    colour += texture(to_blur, vec2(passTexcoord.x, passTexcoord.y)) * 0.2270270270;
    
    colour += texture(to_blur, wrapUV(vec2((1.0*blur*dir.x), (1.0*blur*dir.y)))) * 0.1945945946;
    colour += texture(to_blur, wrapUV(vec2((2.0*blur*dir.x), (2.0*blur*dir.y)))) * 0.1216216216;
    colour += texture(to_blur, wrapUV(vec2((3.0*blur*dir.x), (3.0*blur*dir.y)))) * 0.0540540541;
    colour += texture(to_blur, wrapUV(vec2((4.0*blur*dir.x), (4.0*blur*dir.y)))) * 0.0162162162;
    
    fragColor = vec4(colour.rgb, 1.0);
}  