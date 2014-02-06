#define blursize 5

uniform vec2[blursize] blurparam;
uniform vec2 direction;

uniform sampler2D myTexture;

void main(void)
{
    vec4 sum = vec4(0.0);
	
	for (int i=0; i<blursize; ++i)
	{
		vec2 offset = blurparam[i].x * direction;
		
	    sum += blurparam[i].y * texture2D(myTexture, gl_TexCoord[0].xy + offset);
		if (i > 0) sum += blurparam[i].y * texture2D(myTexture, gl_TexCoord[0].xy - offset);
	}
	
	gl_FragColor = sum;
}