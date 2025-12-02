#version 320 es

struct Point {
	vec2 pos;
	vec2 oldPos;
	vec2 g;
	float radius;
	float friction;
}

