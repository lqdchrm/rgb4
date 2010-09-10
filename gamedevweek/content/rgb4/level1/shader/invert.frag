uniform sampler2D tex;

void main(void)
{

  vec4 color = texture2D(tex, gl_TexCoord[0].st);

  color.r = 1 - color.r;
  color.g = 1 - color.g;
  color.b = 1 - color.b;

  gl_FragColor = color;
}