#version 320 es

#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP 
#endif

uniform float u_mix;

in LOWP vec4 v_color;
in float v_r;
in float v_d;

out vec4 fragColor;

void main()
{
	const vec2 c = vec2(0.5, 0.5);
	vec2 p = (gl_PointCoord - c) * -2.0;
	bool set = false;

	float l = length(p);

	vec4 color = vec4(0.0, 0.0, 0.0, 0.0);
	vec4 white = vec4(1.0, 1.0, 1.0, 1.0);

	if(p.y > 0.0) {
		float m = (1.0 - p.y) * v_r;

		if(abs(p.x) <= m) {
			color = white * pow(1.0 - abs(p.x) / m, 0.5) * pow(1.0 - p.y, 0.5);
			set = true;
		}
	}

	if(l <= v_r) {
		color = max(color, white * pow(1.0 - l / v_r, 0.5));
		set = true;
	}

	if(set) {
		fragColor.rgb = mix(v_color, v_color * color, u_mix).rgb;
		fragColor.a = 1.0;
	} else {
		discard;
	}
}
