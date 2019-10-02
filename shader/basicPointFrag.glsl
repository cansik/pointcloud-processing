#version 150

uniform mat4 transform;

in vec4 vertColor;

out vec4 fragColor;

void main() {
  fragColor = mix(vec4(1.0, 1.0, 1.0, 1.0), vertColor, vertColor.a);
}