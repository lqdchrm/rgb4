#define num 8

uniform sampler2D tex;

uniform vec4[num] playercolor;
uniform vec2[num] target;
uniform float[num] strength;
uniform float range;
uniform float focus;

void main(void)
{
	vec4 orgcolor = texture2D(tex, gl_TexCoord[0].st);
	float brightness = orgcolor.r * 0.3 + orgcolor.g * 0.59 + orgcolor.b * 0.11;
	
	vec4 color = vec4(0.0);
	
	float invrange = 1.0/range;
	
	for (int i=0; i< num; ++i)
	{
		vec2 direction = gl_FragCoord.xy - target[i];
		float dist = length(direction);
		
		if (direction.x*focus > 0.001)
			dist = dist*clamp(abs(direction.y)/(focus*direction.x), 1.0, 10.0);
		else if (focus != 0.0)
			dist *= 10.0;
		
		float dfactor = clamp(0.25+(range-dist)*invrange, 0.0, 1.0);
		color += playercolor[i] * brightness * dfactor[i] * strength[i];
	}
	
	gl_FragColor = color;
}