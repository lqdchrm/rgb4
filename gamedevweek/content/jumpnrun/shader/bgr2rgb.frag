// Fragment shader che inverte i colori R e B
uniform sampler2D tex;

void main(void)
{
  vec4 color = texture2D(tex, gl_TexCoord[0].st);
  float r = color.r;
  color.r = color.b;
  color.b = r;
  gl_FragColor = color;
}