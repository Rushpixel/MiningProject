#version 110

varying vec4 pointColor;
uniform sampler2D texture1;

void main() {

	pointColor = gl_Color;
	gl_Position = ftransform();
	gl_TexCoord[0] = gl_MultiTexCoord0;
}