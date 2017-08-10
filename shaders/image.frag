#version 330 core

uniform sampler2D tex;

in DATA
{
    vec2 tc;
} fs_in;

void main(){
    gl_FragColor = texture(tex, fs_in.tc);
}