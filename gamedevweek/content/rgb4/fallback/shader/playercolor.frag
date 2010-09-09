
uniform sampler2D tex;

uniform vec4 playercolor;

void main(void)
{
	vec4 color = texture2D(tex, gl_TexCoord[0].st) * playercolor;
	
	gl_FragColor = color;
}