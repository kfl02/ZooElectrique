#version 320 es

#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP 
#endif

in vec2 v_texCoords;

uniform sampler2D u_texture;

out vec4 fragColor;

void main() {
	fragColor = texture2D(u_texture, v_texCoords);
}