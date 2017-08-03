#version 330 core

uniform sampler2D tex;

uniform float width;
uniform float fade;
uniform vec3 color;

uniform float width2;
uniform float fade2;
uniform vec3 color2;

uniform vec2 offset;

in DATA
{
    vec2 tc;
} fs_in;

void main(){
    float distance = 1-texture(tex, fs_in.tc).a;
    float alpha = 1-smoothstep(width, width+fade, distance);

    float distance2 = 1-texture(tex, fs_in.tc+offset).a;
    float alpha2 = 1-smoothstep(width2, width2+fade2, distance2);

    vec3 finColor = color * alpha + color2 * (1-alpha);
    float finAlpha = alpha+(1-alpha)*alpha2;

    gl_FragColor = vec4(finColor, finAlpha);
}