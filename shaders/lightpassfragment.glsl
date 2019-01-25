#version 330

#define POINTLIGHTS 32
#define POINTLIGHTSSHADOW 4

uniform sampler2D position_texture;
uniform sampler2D normal_texture;
uniform sampler2D albedo_texture;

uniform sampler2D dirShadowMap;
uniform sampler2D pointShadowMap[POINTLIGHTSSHADOW];

uniform mat4 dirLightMatrix;

uniform vec3 viewPosition;

struct DirLight {
	vec3 direction;
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
};
uniform DirLight dirLight;

struct PointLight {
	//float constant = 1.0;
	//float linear = 0.14;
	//float quadratic = 0.07;
	
	vec3 position;
	vec3 ambient;
	vec3 diffuse;
	vec3 specular;
};
uniform PointLight[POINTLIGHTS] pointLights;

uniform bool softshadows;

uniform float farPlane;

in vec2 texcoord;

out vec4 outColour;

vec2 xyzToUV(vec3 v) {
	v = normalize(v);
	float face;
	vec3 vAbs = abs(v);
	float ma;
	vec2 uv;
	
	if (vAbs.z >= vAbs.x && vAbs.z >= vAbs.y) {
		face = (v.z < 0.0) ? 5.0 : 4.0;
		ma = 0.5 / vAbs.z;
		uv = vec2((v.z < 0.0) ? -v.x : v.x, -v.y);
	} else if (vAbs.y >= vAbs.x) {
		face = (v.y < 0.0) ? 3.0 : 2.0;
		ma = 0.5 / vAbs.y;
		uv = vec2(v.x, (v.y < 0.0) ? -v.z : v.z);
	} else {
		face = (v.x < 0.0) ? 1.0 : 0.0;
		ma = 0.5 / vAbs.x;
		uv = vec2((v.x < 0.0) ? v.z : -v.z, -v.y);
	}
	
	uv = (uv * ma) + 0.5;
	uv.x = (uv.x + face) / 6.0;
	return uv;
}

float softPointLightShadow(vec3 fragPosition, int i) {
	vec3 fragToLight = fragPosition - pointLights[i].position;
	
	vec2 uv;
	
	float currentDepth = length(fragToLight);

	uv = xyzToUV(fragToLight);

	vec2 moments = texture(pointShadowMap[i], uv).rg;
	
	if (currentDepth <= moments.x) {
		return 0.0;
	}
	
	float variance = moments.y - (moments.x * moments.x);
	variance = max(variance, 0.00002);
	
	float d = currentDepth - moments.x;
	float pmax = variance / (variance + (d*d));
	
	return 1.0 - pmax;
}

float pointLightShadow(vec3 fragPosition, int i) {
	vec3 fragToLight = fragPosition - pointLights[i].position;
	
	vec2 uv;
	
	float currentDepth = length(fragToLight);
	
	float bias = 0.05;
	float shadow = 0.0;
	
	uv = xyzToUV(fragToLight);
	float closestDepth = texture(pointShadowMap[i], uv).r;
	closestDepth *= farPlane;
				
	if ((currentDepth - bias) > closestDepth) {
		shadow += 1.0;
	}
	
	return shadow;
}

float getPointLightShadow(vec3 fragPosition, int i) {
	if (softshadows) {
		return softPointLightShadow(fragPosition, i);
	} else {
		return pointLightShadow(fragPosition, i);
	}
}

vec3 calculatePointLight(int i, vec3 normal, vec3 fragPosition, vec3 viewDirection) {
	float constant = 1.0;
	float linear = 0.14;
	float quadratic = 0.07;

	vec3 lightDirection = normalize(pointLights[i].position - fragPosition);
	vec3 halfwayDirection = normalize(lightDirection + viewDirection);
	
	float diff = max(dot(normal, lightDirection), 0.0);
	
	float spec = pow(max(dot(normal, halfwayDirection), 0.0), 32);
	
	float dist = length(pointLights[i].position - fragPosition);
	float attenuation = 1.0 / (constant + linear * dist + 
							quadratic * (dist * dist));
	
	vec3 ambient = pointLights[i].ambient;
	vec3 diffuse = pointLights[i].diffuse * diff;
	vec3 specular = pointLights[i].specular * spec;
	ambient *= attenuation;
	diffuse *= attenuation;
	specular *= attenuation;
	
	float shadow = 0.0;
	if (i < POINTLIGHTSSHADOW) {
		shadow = getPointLightShadow(fragPosition, i);
	}
	
	return (ambient + ((1.0 - shadow) * (diffuse + specular)));
}

float dirLightShadow(vec4 dirLightFragPosition, vec3 normal, vec3 lightDirection) {
	vec3 projCoords = dirLightFragPosition.xyz / dirLightFragPosition.w;
	
	if (projCoords.z > 1.0) {
		return 0.0;
	}
	
	projCoords = (projCoords * 0.5) + 0.5;
	
	float currentDepth = projCoords.z;
	
	float bias = max(0.005 * (1.0 - dot(normal, lightDirection)), 0.0005);
	
	float shadow = 0.0;
	vec2 texelSize = 1.0 / textureSize(dirShadowMap, 0);
	
	for (int x=-1; x <= 1; ++x) {
		for (int y=-1; y<=1; ++y) {
			float pcfDepth = texture(dirShadowMap, projCoords.xy + (vec2(x, y) * texelSize)).r;
			shadow += (currentDepth - bias) > pcfDepth ? 1.0 : 0.0;
		}
	}
	
	shadow /= 9.0;
	
	//float pcfDepth = texture(dirShadowMap, projCoords.xy).r;
	//shadow += (currentDepth - bias) > pcfDepth ? 1.0 : 0.0;
	
	return shadow;
}

float softDirLightShadow(vec4 dirLightFragPosition, vec3 normal, vec3 lightDirection) {
	vec3 projCoords = dirLightFragPosition.xyz / dirLightFragPosition.w;
	
	if (projCoords.z > 1.0) {
		return 0.0;
	}
	
	projCoords = (projCoords * 0.5) + 0.5;
	float depth = projCoords.z;
	
	vec2 moments = texture(dirShadowMap, projCoords.xy).rg;
	
	if (depth <= moments.x) {
		return 0.0;
	}
	
	float variance = moments.y - (moments.x * moments.x);
	variance = max(variance, 0.00002);
	
	float d = depth - moments.x;
	float pmax = variance / (variance + (d*d));
	
	return 1.0 - pmax;
}

float getDirLightShadow(vec4 dirLightFragPosition, vec3 normal, vec3 lightDirection) {
	if (softshadows) {
		//return softDirLightShadow(dirLightFragPosition, normal, lightDirection);
	} else {
		//return dirLightShadow(dirLightFragPosition, normal, lightDirection);
	}
	return dirLightShadow(dirLightFragPosition, normal, lightDirection);
}

vec3 calculateDirLight(DirLight light, vec3 normal, vec3 viewDirection, vec4 lightSpacePosition) {
	vec3 lightDirection = normalize(-light.direction);
	
	float diff = max(dot(normal, lightDirection), 0.0);
	
	vec3 reflectDirection = reflect(-lightDirection, normal);
	float spec = pow(max(dot(viewDirection, reflectDirection), 0.0), 32);
	
	vec3 ambient = light.ambient;
	vec3 diffuse = light.diffuse * diff;
	vec3 specular = light.specular * spec;
	
	float shadow = getDirLightShadow(lightSpacePosition, normal, lightDirection);
	
	return (ambient + ((1.0 - shadow) * (diffuse + specular)));
}

void main(void) {
	vec3 fragPosition = texture(position_texture, texcoord).rgb;
	vec3 fragNormal = texture(normal_texture, texcoord).rgb;
	vec3 fragColour = texture(albedo_texture, texcoord).rgb;
	
	vec3 viewDirection = normalize(viewPosition - fragPosition);
	vec4 dirLightSpacePosition = dirLightMatrix * vec4(fragPosition.rgb, 1.0);
	
	vec3 result = calculateDirLight(dirLight, fragNormal, viewDirection, dirLightSpacePosition);
	
	for (int i=0; i<POINTLIGHTS; i++) {
		result += calculatePointLight(i, fragNormal, fragPosition, viewDirection);
	}
	
	outColour = vec4((fragColour * result), 1.0);
}