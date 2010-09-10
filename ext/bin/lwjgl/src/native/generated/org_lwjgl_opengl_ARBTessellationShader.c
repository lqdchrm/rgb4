/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glPatchParameteriPROC) (GLenum pname, GLint value);
typedef void (APIENTRY *glPatchParameterfvPROC) (GLenum pname, const GLfloat * values);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTessellationShader_nglPatchParameteri(JNIEnv *env, jclass clazz, jint pname, jint value, jlong function_pointer) {
	glPatchParameteriPROC glPatchParameteri = (glPatchParameteriPROC)((intptr_t)function_pointer);
	glPatchParameteri(pname, value);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTessellationShader_nglPatchParameterfv(JNIEnv *env, jclass clazz, jint pname, jobject values, jint values_position, jlong function_pointer) {
	const GLfloat *values_address = ((const GLfloat *)(*env)->GetDirectBufferAddress(env, values)) + values_position;
	glPatchParameterfvPROC glPatchParameterfv = (glPatchParameterfvPROC)((intptr_t)function_pointer);
	glPatchParameterfv(pname, values_address);
}

