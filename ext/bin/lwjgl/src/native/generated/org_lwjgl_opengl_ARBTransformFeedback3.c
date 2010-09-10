/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glDrawTransformFeedbackStreamPROC) (GLenum mode, GLuint id, GLuint stream);
typedef void (APIENTRY *glBeginQueryIndexedPROC) (GLenum target, GLuint index, GLuint id);
typedef void (APIENTRY *glEndQueryIndexedPROC) (GLenum target, GLuint index);
typedef void (APIENTRY *glGetQueryIndexedivPROC) (GLenum target, GLuint index, GLenum pname, GLint * params);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTransformFeedback3_nglDrawTransformFeedbackStream(JNIEnv *env, jclass clazz, jint mode, jint id, jint stream, jlong function_pointer) {
	glDrawTransformFeedbackStreamPROC glDrawTransformFeedbackStream = (glDrawTransformFeedbackStreamPROC)((intptr_t)function_pointer);
	glDrawTransformFeedbackStream(mode, id, stream);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTransformFeedback3_nglBeginQueryIndexed(JNIEnv *env, jclass clazz, jint target, jint index, jint id, jlong function_pointer) {
	glBeginQueryIndexedPROC glBeginQueryIndexed = (glBeginQueryIndexedPROC)((intptr_t)function_pointer);
	glBeginQueryIndexed(target, index, id);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTransformFeedback3_nglEndQueryIndexed(JNIEnv *env, jclass clazz, jint target, jint index, jlong function_pointer) {
	glEndQueryIndexedPROC glEndQueryIndexed = (glEndQueryIndexedPROC)((intptr_t)function_pointer);
	glEndQueryIndexed(target, index);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTransformFeedback3_nglGetQueryIndexediv(JNIEnv *env, jclass clazz, jint target, jint index, jint pname, jobject params, jint params_position, jlong function_pointer) {
	GLint *params_address = ((GLint *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetQueryIndexedivPROC glGetQueryIndexediv = (glGetQueryIndexedivPROC)((intptr_t)function_pointer);
	glGetQueryIndexediv(target, index, pname, params_address);
}

