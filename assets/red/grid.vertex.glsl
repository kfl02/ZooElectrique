#version 320 es

in vec4 a_position;
in vec2 a_texCoords0;

uniform mat4 u_projTrans;
uniform sampler2D u_texture;
uniform vec4 u_textureUV;

out vec2 v_texCoords;

void main()
{
	vec2 pos = a_position.xy + a_position.zw;
	// vec2 tOrg = u_textureUV.xy;
	// vec2 tDim = u_textureUV.zw; // - u_textureUV.xy;
	// vec2 tDimP = vec2(textureSize(u_texture, 0));
	// v_texCoords = pos / tDimP * tDim + tOrg;
	// v_texCoords.y = v_texCoords.y;
	v_texCoords = a_texCoords0;
	gl_Position = u_projTrans * vec4(pos, 0.0, 1.0);
}