#version 330

//http://blog.simonrodriguez.fr/articles/30-07-2016_implementing_fxaa.html

#define EDGE_MIN 0.0312
#define EDGE_MAX 0.125
#define ITERATIONS 12;
#define SUBPIXELQUALITY 1.0;

uniform sampler2D render;

uniform float invwidth;
uniform float invheight;

in vec2 texcoord;

out vec4 outColour;

float jump_amount(int i) {
	switch(i) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			return 1.0;
		case 6:
		case 7:
		case 8:
		case 9:
			return 2.0;
		case 10:
			return 4.0;
	}

	return 8.0;
}

float rgb_to_luma(vec3 rgb) {
	//using the rgb weighting system key to fxaa
	return sqrt(dot(rgb, vec3(0.299, 0.587, 0.114)));
}

void main(void) {
	vec3 colourCentre = texture(render, texcoord).rgb;
	
	float lumaCentre = rgb_to_luma(colourCentre);
	
	float lumaUp = rgb_to_luma(textureOffset(render, texcoord, ivec2(0, 1)).rgb);
	float lumaDown = rgb_to_luma(textureOffset(render, texcoord, ivec2(0, -1)).rgb);
	float lumaLeft = rgb_to_luma(textureOffset(render, texcoord, ivec2(-1, 0)).rgb);
	float lumaRight = rgb_to_luma(textureOffset(render, texcoord, ivec2(1, 0)).rgb);
	
	float lumaMin = min(lumaCentre, min(min(lumaUp, lumaDown), min(lumaLeft, lumaRight)));
	float lumaMax = max(lumaCentre, max(max(lumaUp, lumaDown), max(lumaLeft, lumaRight)));
	
	float lumaRange = lumaMax - lumaMin;
	
	if (lumaRange < max(EDGE_MIN, (lumaMax * EDGE_MAX))) {
		outColour = vec4(colourCentre, 1.0);
		return;
	}
	
	float lumaUpLeft = rgb_to_luma(textureOffset(render, texcoord, ivec2(-1, 1)).rgb);
	float lumaUpRight = rgb_to_luma(textureOffset(render, texcoord, ivec2(1, 1)).rgb);
	float lumaDownLeft = rgb_to_luma(textureOffset(render, texcoord, ivec2(-1, -1)).rgb);
	float lumaDownRight = rgb_to_luma(textureOffset(render, texcoord, ivec2(1, -1)).rgb);
	
	float lumaDownUp = lumaDown + lumaUp;
	float lumaLeftRight = lumaLeft + lumaRight;
	
	float lumaLeftCorners = lumaDownLeft + lumaUpLeft;
	float lumaRightCorners = lumaDownRight + lumaUpRight;
	float lumaUpCorners = lumaUpRight + lumaUpLeft;
	float lumaDownCorners = lumaDownRight + lumaDownLeft;
	
	float edgeHorizontal = abs((-2.0 * lumaLeft) + lumaLeftCorners) + (abs((-2.0 * lumaCentre) + lumaDownUp) * 2.0)
			+ abs((-2.0 * lumaRight) + lumaRightCorners);
	float edgeVertical = abs((-2.0 * lumaUp) + lumaUpCorners) + (abs((-2.0 * lumaCentre) + lumaLeftRight) * 2.0)
			+ abs((-2.0 * lumaDown) + lumaDownCorners);
			
	bool horizontal = (edgeHorizontal >= edgeVertical);
	
	float luma1 = horizontal ? lumaDown : lumaLeft;
	float luma2 = horizontal ? lumaUp : lumaRight;
	
	float gradient1 = luma1 - lumaCentre;
	float gradient2 = luma2 - lumaCentre;
	
	bool steepest1 = (abs(gradient1) >= abs(gradient2));
	
	float gradientScaled = 0.25 * max(abs(gradient1), abs(gradient2));
	
	float stepLength = horizontal ? invheight : invwidth;
	
	float lumaLocalAverage = 0.0;
	
	if (steepest1) {
		stepLength = -stepLength;
		lumaLocalAverage = 0.5 * (luma1 + lumaCentre);
	} else {
		lumaLocalAverage = 0.5 * (luma2 + lumaCentre);
	}
	
	vec2 uv = texcoord;
	if (horizontal) {
		uv.y += stepLength * 0.5;
	} else {
		uv.x += stepLength * 0.5;
	}
	
	vec2 offset = horizontal ? vec2(invwidth, 0.0) : vec2(0.0, invheight);
	
	vec2 uv1 = uv - offset;
	vec2 uv2 = uv + offset;
	
	float lumaEnd1 = rgb_to_luma(texture(render, uv1).rgb);
	float lumaEnd2 = rgb_to_luma(texture(render, uv2).rgb);
	lumaEnd1 -= lumaLocalAverage;
	lumaEnd2 -= lumaLocalAverage;
	
	bool reached1 = (abs(lumaEnd1) >= gradientScaled);
	bool reached2 = (abs(lumaEnd2) >= gradientScaled);
	bool reachedboth = (reached1 && reached2);
	
	if (!reached1) {
		uv1 -= offset;
	}
	if (!reached2) {
		uv2 += offset;
	}
	
	if (!reachedboth) {
		for (int i=2; i<12; i++) {
			if (!reached1) {
				lumaEnd1 = rgb_to_luma(texture(render, uv1).rgb);
				lumaEnd1 = lumaEnd1 - lumaLocalAverage;
			}
			
			if (!reached2) {
				lumaEnd2 = rgb_to_luma(texture(render, uv2).rgb);
				lumaEnd2 = lumaEnd2 - lumaLocalAverage;
			}
			
			reached1 = (abs(lumaEnd1) >= gradientScaled);
			reached2 = (abs(lumaEnd2) >= gradientScaled);
			reachedboth = (reached1 && reached2);
			
			if (!reached1) {
				uv1 -= (offset * jump_amount(i));
			}
			
			if (!reached1) {
				uv2 += (offset * jump_amount(i));
			}
			
			if (reachedboth) {
				break;
			}
		}
	}
	
	float distance1 = horizontal ? (texcoord.x - uv1.x) : (texcoord.y - uv1.y);
	float distance2 = horizontal ? (uv2.x - texcoord.x) : (uv2.y - texcoord.y);
	
	bool direction1 = (distance1 < distance2);
	float distanceFinal = min(distance1, distance2);
	
	float edgeThickness = (distance1 + distance2);
	float pixelOffset = (-distanceFinal / edgeThickness) + 0.5;
	
	bool lumaCentreSmaller = (lumaCentre < lumaLocalAverage);
	bool correctVariation = (((direction1 ? lumaEnd1 : lumaEnd2) < 0.0) != lumaCentreSmaller);
	
	float finalOffset = correctVariation ? pixelOffset : 0.0;
	
	float lumaAverage = (1.0 / 12.0) * (2.0 * (lumaDownUp + lumaLeftRight) + lumaLeftCorners + lumaRightCorners);
	
	float subPixelOffset1 = clamp((abs(lumaAverage - lumaCentre) / lumaRange), 0.0, 1.0);
	float subPixelOffset2 = (-2.0 * subPixelOffset1 + 3.0) * subPixelOffset1 * subPixelOffset1;
	
	float subPixelOffsetFinal = subPixelOffset2 * subPixelOffset2 * SUBPIXELQUALITY;
	
	finalOffset = max(finalOffset, subPixelOffsetFinal);
	
	vec2 finalUV = texcoord;
	
	if (horizontal) {
		finalUV.y += finalOffset * stepLength;
	} else {
		finalUV.x += finalOffset * stepLength;
	}
	
	outColour = vec4(texture(render, finalUV).rgb, 1.0);
}