#version 110

uniform vec4 ambience_color;
uniform int SHADERMODE;

varying vec4 pointColor;

void main(){
	pointColor = gl_Color;
 	gl_Position = ftransform();
	gl_TexCoord[0] = gl_MultiTexCoord0;
}
