/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glGetBooleanIndexedvEXTPROC) (GLenum pname, GLuint index, GLboolean * data);
typedef void (APIENTRY *glGetIntegerIndexedvEXTPROC) (GLenum pname, GLuint index, GLint * data);
typedef void (APIENTRY *glGetMultisamplefvNVPROC) (GLenum pname, GLuint index, GLfloat * val);
typedef void (APIENTRY *glSampleMaskIndexedNVPROC) (GLuint index, GLbitfield mask);
typedef void (APIENTRY *glTexRenderbufferNVPROC) (GLenum target, GLuint renderbuffer);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_NVExplicitMultisample_nglGetBooleanIndexedvEXT(JNIEnv *env, jclass clazz, jint pname, jint index, jobject data, jint data_position, jlong function_pointer) {
	GLboolean *data_address = ((GLboolean *)(*env)->GetDirectBufferAddress(env, data)) + data_position;
	glGetBooleanIndexedvEXTPROC glGetBooleanIndexedvEXT = (glGetBooleanIndexedvEXTPROC)((intptr_t)function_pointer);
	glGetBooleanIndexedvEXT(pname, index, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_NVExplicitMultisample_nglGetIntegerIndexedvEXT(JNIEnv *env, jclass clazz, jint pname, jint index, jobject data, jint data_position, jlong function_pointer) {
	GLint *data_address = ((GLint *)(*env)->GetDirectBufferAddress(env, data)) + data_position;
	glGetIntegerIndexedvEXTPROC glGetIntegerIndexedvEXT = (glGetIntegerIndexedvEXTPROC)((intptr_t)function_pointer);
	glGetIntegerIndexedvEXT(pname, index, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_NVExplicitMultisample_nglGetMultisamplefvNV(JNIEnv *env, jclass clazz, jint pname, jint index, jobject val, jint val_position, jlong function_pointer) {
	GLfloat *val_address = ((GLfloat *)(*env)->GetDirectBufferAddress(env, val)) + val_position;
	glGetMultisamplefvNVPROC glGetMultisamplefvNV = (glGetMultisamplefvNVPROC)((intptr_t)function_pointer);
	glGetMultisamplefvNV(pname, index, val_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_NVExplicitMultisample_nglSampleMaskIndexedNV(JNIEnv *env, jclass clazz, jint index, jint mask, jlong function_pointer) {
	glSampleMaskIndexedNVPROC glSampleMaskIndexedNV = (glSampleMaskIndexedNVPROC)((intptr_t)function_pointer);
	glSampleMaskIndexedNV(index, mask);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_NVExplicitMultisample_nglTexRenderbufferNV(JNIEnv *env, jclass clazz, jint target, jint renderbuffer, jlong function_pointer) {
	glTexRenderbufferNVPROC glTexRenderbufferNV = (glTexRenderbufferNVPROC)((intptr_t)function_pointer);
	glTexRenderbufferNV(target, renderbuffer);
}

