#version 110

uniform vec4 ambience_color; // ambient light color
uniform int SHADERMODE; // which mode to use, table below
uniform sampler2D texture1; // texture to use

varying vec4 pointColor;

// SHADERMODE 0 - Normal
// SHADERMODE 1 - multiply output by ambience color

void main() {
		if(SHADERMODE == 0){ // case normal / diffuse mode / external lighting mode
    		gl_FragColor = texture2D(texture1, gl_TexCoord[0].st) * pointColor;
    }
    else if(SHADERMODE == 1){ // case multiplitive / light ignoring layer mode
				gl_FragColor = texture2D(texture1, gl_TexCoord[0].st) * pointColor * ambience_color;
    }
}
