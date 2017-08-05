#version 330 core

layout (location = 0) in vec4 position;
layout (location = 2) in vec4 translation;
layout (location = 3) in vec4 color;

uniform mat4 pr_matrix;
uniform float depth;

out DATA
{
    vec4 color;
} fs_out;

void main(){
    vec4 pos = position;
    pos.xy = pos.xy * translation.zw + translation.xy;
    pos.z = depth;
    gl_Position = pr_matrix * pos;

    fs_out.color=color;
}