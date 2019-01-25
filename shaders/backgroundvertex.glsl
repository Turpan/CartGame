#version 330

layout (location = 0) in vec4 inPosition;

out vec2 texcoord;

void main(void) {
	vec2 position = inPosition.xy; 
	position.y = (inPosition.y + 1.0) / 2.0;
	
	gl_Position = vec4(position, 1.0, 1.0);
	
	texcoord = (vec2(inPosition.xy * 0.5)) + 0.5;
	
	//texcoord.y = 1.0 - texcoord.y;
}