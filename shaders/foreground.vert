#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 tex_coord;
layout (location = 2) in vec4 translation; //(x,y) location (z,w) scale
layout (location = 3) in vec4 uvtranslation; //(x,y) location (z,w) scale

uniform mat4 pr_matrix;
uniform float depth;

out DATA
{
    vec2 tc;
} vs_out;

void main(){
    vec4 pos = position;
    pos.xy = pos.xy * translation.zw + translation.xy;
    pos.z = depth;
    gl_Position = pr_matrix * pos;
    vs_out.tc=tex_coord * uvtranslation.zw + uvtranslation.xy;
}