
uniform vec2 invsize;

uniform sampler2D myTexture;

void main(void)
{
    vec4 sams[13];
	sams[0] = texture2D(myTexture, vec2(gl_TexCoord[0]));
	sams[1] = texture2D(myTexture, vec2(gl_TexCoord[0])+vec2(invsize.x,0.0));
	sams[2] = texture2D(myTexture, vec2(gl_TexCoord[0])+vec2(0.0,invsize.y));
	sams[3] = texture2D(myTexture, vec2(gl_TexCoord[0])+vec2(invsize.x,0.0));
	sams[4] = texture2D(myTexture, vec2(gl_TexCoord[0])+vec2(0.0,-invsize.y));
	sams[5] = texture2D(myTexture, vec2(gl_TexCoord[0])+vec2(-invsize.x,-invsize.y));
	sams[6] = texture2D(myTexture, vec2(gl_TexCoord[0])+vec2(invsize.x,-invsize.y));
	sams[7] = texture2D(myTexture, vec2(gl_TexCoord[0])+vec2(-invsize.x,invsize.y));
	sams[8] = texture2D(myTexture, vec2(gl_TexCoord[0])+vec2(invsize.x,invsize.y));
	sams[9] = texture2D(myTexture, vec2(gl_TexCoord[0])+vec2(invsize.x*2.0,0.0));
	sams[10] = texture2D(myTexture, vec2(gl_TexCoord[0])+vec2(0.0,invsize.y*2.0));
	sams[11] = texture2D(myTexture, vec2(gl_TexCoord[0])+vec2(invsize.x*2.0,0.0));
	sams[12] = texture2D(myTexture, vec2(gl_TexCoord[0])+vec2(0.0,-invsize.y*2.0));
	gl_FragColor = ((sams[0]*2.0+sams[1]+sams[2]+sams[3]+sams[4]
		+sams[5]+sams[6]+sams[7]+sams[8])*2.0+sams[9]+sams[10]+sams[11]+sams[12]) / 24.0;
}