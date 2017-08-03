#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 tex_coord;
layout (location = 2) in vec4 translation;
layout (location = 3) in vec2 uvtranslation;
layout (location = 4) in float rotation;

uniform mat4 pr_matrix;
uniform float depth;
uniform vec2 uvsize;

#define TAU 6.283185307;

out DATA
{
    vec2 tc;
} vs_out;

void rotate(float);

vec4 pos = position;

void main(){
    rotate(rotation);
    pos.xy = pos.xy*translation.zw + translation.xy;
    pos.z = depth;
    gl_Position = pr_matrix * pos;
    vs_out.tc=tex_coord * uvsize + uvtranslation;
}

void rotate(float deg){
    pos.xy = pos.xy - vec2(.5, .5);
    float origRot = atan(pos.y, pos.x);
    pos.xy = pos.xy + vec2(.5, .5);
    float finRot = origRot+deg/360*TAU;
    float rx = cos(finRot) - cos(origRot);
    float ry = sin(finRot) - sin(origRot);
    pos.xy = pos.xy + vec2(rx/sqrt(2), ry/sqrt(2));
}