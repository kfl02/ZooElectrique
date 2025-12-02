#version 320 es

#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP 
#endif

// standard shader
uniform sampler2D u_texture;

uniform ScreenInfo {
vec2 u_gutter;
vec2 u_screen;
vec2 u_world;
};

layout(binding = 3) buffer LensInfo {
vec2 u_lensPos;
float u_lensRadius;
float u_param;
};

// standard shader
in LOWP vec4 v_color;
in vec2 v_texCoords;

out vec4 fragColor;

const float PI = 3.14159265358979323846;

vec2 rotate(vec2 v, float a) {
  float s = sin(a);
  float c = cos(a);
  mat2 m = mat2(c, s, -s, c);
  return m * v;
}

// Vector distortion function, direction vector assumed to be in world coordinates, output in wolrd coordinates
vec2 vecFn(vec2 dir) {
  float l = length(dir) / u_lensRadius;

  return rotate(dir * pow((1.0f - l), 2.0 * u_param), (1.0 - l) * 2.0 * PI * u_param);
}

void main()
{
  // lens position, radius to screen size
  vec2 lensPosS = u_lensPos / u_world * u_screen + u_gutter;
  float lensRadiusS = u_lensRadius / u_world.x * u_screen.x;
  // direction vector of current fragment to lens position in screen coordinates
  vec2 dir = lensPosS - gl_FragCoord.xy;
  float len = length(dir);

  if(len < lensRadiusS) {
    // direction vector to world coordinates
    vec2 dirW = dir * u_world / u_screen;
    // lens position in texture coordinates
    vec2 lensPosT = u_lensPos / u_world;

    // calculate distorted vector, transform from world to texture coordinates
    vec2 c = vecFn(dirW) / u_world + lensPosT;
    c.x = 1.0 - c.x;

    fragColor = v_color * texture(u_texture, c);
  } else {
    discard;
  }
}
