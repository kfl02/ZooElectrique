#version 320 es

in vec4 a_position;	// (x,y) = actual position, (z,w) = old position
in vec4 a_grf;		// (x,y) = gravity, (z) = radius, (w) = friction

uniform mat4 u_projTrans;
uniform float u_scale;

out vec4 v_color;
out float v_r;
out float v_d;

void main()
{
	v_color = vec4(1.0, 0.168627451, 0.168627451, 1.0);
	
	float r = a_grf.z * u_scale;
	float d = length(a_position.xy - a_position.zw) * u_scale;
	float pr = (r > d) ? r  : d;

	v_r = r / pr;
	v_d = d / pr;
	gl_PointSize = pr * 2.0;
	gl_Position =  u_projTrans * vec4(a_position.xy, 0.0, 1.0);
}