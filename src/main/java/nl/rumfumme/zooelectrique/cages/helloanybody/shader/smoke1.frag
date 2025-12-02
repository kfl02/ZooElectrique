// https://www.shadertoy.com/view/3fBfRm

#version 150

uniform float time;
uniform vec2 resolution;
uniform vec2 mouse;
uniform vec3 spectrum;

uniform sampler2D texture0;
uniform sampler2D texture1;
uniform sampler2D texture2;
uniform sampler2D texture3;
uniform sampler2D prevFrame;
uniform sampler2D prevPass;

in VertexData
{
    vec4 v_position;
    vec3 v_normal;
    vec2 v_texcoord;
} inData;

out vec4 fragColor;

void main(void)
{
    float t = time;
    float i = 0.0;  // apperantly should be 0.0
    float z = 1.0;  // brightness
    float d = 1.0;
    float c = 7.0;  // contrast
    float sw = 0.1;  // swirl
    vec4 vd = vec4(0, 33, 11, 0);   // swirl distortion
    float sp = 5.0; // zoom speed
    float di = 1.0; // Attention: Not > 1.0, computer crashes.
    float dm = 9.0; // detail level
    float dd = 0.7; // fuzziness
    float dc = 0.02;    // again something like contrast
    float da = 2.0; // dark cloudyness
    float df = 0.6; // fizzyness
    float dk = 8.0; // compactness

    vec2 fragCoord = gl_FragCoord.xy;

    for (fragColor *= i; i++ < 1e2;
    fragColor += vec4(
    1.0,   // red
    1.0,   // green
    1.0,   // blue
    1.0    // alpha
    ) * (z / c) / d)
    {
        vec3 p = z * normalize(vec3(fragCoord + fragCoord, 0) - resolution.xyy);

        p.xy *= mat2(cos((z + t) * sw + vd));

        p.z -= sp * t;

        for (d = di; d < dm; d /= dd) {
            p += cos(p.yzx * d + t) / d;
        }

        d = dc + abs(da - dot(cos(p), sin(p.yzx * df))) / dk;
        z += d;
    }

    fragColor = tanh(fragColor * fragColor / 1e7);
}