#define num 8

uniform sampler2D tex;

uniform vec4[num] playercolor;
uniform vec2[num] target;
uniform float[num] strength;
uniform float range;

void main(void)
{
	vec4 orgcolor = texture2D(tex, gl_TexCoord[0].st);
	float brightness = orgcolor.r * 0.3 + orgcolor.g * 0.59 + orgcolor.b * 0.11;
	
	vec4 color = vec4(0.0);
	
	for (int i=0; i< num; ++i)
	{
		float dist = distance(target[i], noise2(gl_FragCoord.xy));
		//if (dist < range/4.0) dist = range-dist*4.0;
		float dfactor = clamp(0.25+(range-dist)/range, 0.0, 1.0);
		color += playercolor[i] * brightness * dfactor * strength[i];
	}
	
	gl_FragColor = color;
}