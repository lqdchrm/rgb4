uniform sampler2D tex;

uniform float factor;
uniform vec2 target;

void main(void)
{
	vec4 color = texture2D(tex, gl_TexCoord[0].st);
	
	float brightness = color.r * 0.3 + color.g * 0.59 + color.b * 0.11;
	
	float fact = factor * (1.0 - smoothstep(40.0, 200.0, distance(target, gl_FragCoord.xy)));
	
	brightness = brightness * fact; 
	
	color.r = brightness;
	color.g = brightness;
	color.b = brightness;
	
	gl_FragColor = color;
}