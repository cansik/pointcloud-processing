#version 150

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 modelview;

uniform vec4 viewport;
uniform int perspective;

in vec4 position;
in vec4 color;
uniform float time;

out vec4 vertColor;

void main() {
	gl_PointSize = 1.0;

	//vec4 worldPosition = transform * position;
	//gl_Position = worldPosition;

	vec4 pos = modelview * position;
	vec4 clip = projection * pos;

	// Perspective ---
	// convert from world to clip by multiplying with projection scaling factor
	// invert Y, projections in Processing invert Y
	vec2 perspScale = (projection * vec4(1, -1, 0, 0)).xy;

	// No Perspective ---
	// multiply by W (to cancel out division by W later in the pipeline) and
	// convert from screen to clip (derived from clip to screen above)
	vec2 noPerspScale = clip.w / (0.5 * viewport.zw);

	gl_Position.xy = clip.xy + mix(noPerspScale, perspScale, float(perspective > 0));
	gl_Position.zw = clip.zw;

	// based on x
	float value = mod(time * 5.0, 10.0) * 200.0 - 1000.0;

	vec4 c = color;
	c = vec4(0.5, 0.5, 0.5, 1.0);

	float rstart = -50.0;
	float rend = 50.0;
	if(position.x - value > rstart && position.x - value < rend) {
		float v = 1.0 - abs(position.x - value) / 100.0;
		c = vec4(v, v, v, 1.0);
	}


	vertColor = c;
}