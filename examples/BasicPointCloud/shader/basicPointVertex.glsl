#version 150

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 modelview;

uniform vec4 viewport;
uniform int perspective;

in vec4 position;
in vec4 color;

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

	vertColor = color;
}