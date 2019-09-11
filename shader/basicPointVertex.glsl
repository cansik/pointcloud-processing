#version 150

uniform mat4 transform;

in vec4 position;

out vec4 vertColor;

void main() {
	gl_PointSize = 1.0;
	gl_Position = transform * position;
	vertColor = vec4(1.0, 1.0, 1.0, 1.0);
}