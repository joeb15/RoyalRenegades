#version 330 core

in DATA
{
    vec4 color;
} fs_in;

void main(){
    gl_FragColor = fs_in.color;
}