#version 110

uniform sampler2D texture1;

varying vec4 pointColor;

void main() {
	//if(texture2D(texture1, gl_TexCoord[0].st).a != 1.0) discard;
	gl_FragColor = texture2D(texture1, gl_TexCoord[0].st) * pointColor;
}