/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glQueryCounterPROC) (GLuint id, GLenum target);
typedef void (APIENTRY *glGetQueryObjecti64vPROC) (GLuint id, GLenum pname, GLint64 * params);
typedef void (APIENTRY *glGetQueryObjectui64vPROC) (GLuint id, GLenum pname, GLuint64 * params);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTimerQuery_nglQueryCounter(JNIEnv *env, jclass clazz, jint id, jint target, jlong function_pointer) {
	glQueryCounterPROC glQueryCounter = (glQueryCounterPROC)((intptr_t)function_pointer);
	glQueryCounter(id, target);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTimerQuery_nglGetQueryObjecti64v(JNIEnv *env, jclass clazz, jint id, jint pname, jobject params, jint params_position, jlong function_pointer) {
	GLint64 *params_address = ((GLint64 *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetQueryObjecti64vPROC glGetQueryObjecti64v = (glGetQueryObjecti64vPROC)((intptr_t)function_pointer);
	glGetQueryObjecti64v(id, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTimerQuery_nglGetQueryObjectui64v(JNIEnv *env, jclass clazz, jint id, jint pname, jobject params, jint params_position, jlong function_pointer) {
	GLuint64 *params_address = ((GLuint64 *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetQueryObjectui64vPROC glGetQueryObjectui64v = (glGetQueryObjectui64vPROC)((intptr_t)function_pointer);
	glGetQueryObjectui64v(id, pname, params_address);
}

