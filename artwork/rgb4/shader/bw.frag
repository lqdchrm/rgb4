uniform sampler2D tex;

void main(void)
{
	vec4 color = texture2D(tex, gl_TexCoord[0].st);
	
	float brightness = color.r * 0.3 + color.g * 0.59 + color.b * 0.11;
	
	color.r = brightness;
	color.g = brightness;
	color.b = brightness;
	
	gl_FragColor = color;
}