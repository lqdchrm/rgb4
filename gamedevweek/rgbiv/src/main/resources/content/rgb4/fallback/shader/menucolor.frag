
uniform sampler2D tex;

uniform vec4 menucolor;

void main(void)
{
	vec4 color = texture2D(tex, gl_TexCoord[0].st);
	
	color *= menucolor;
	
	gl_FragColor = color;
}