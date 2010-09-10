/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glTexImage2DMultisamplePROC) (GLenum target, GLsizei samples, GLint internalformat, GLsizei width, GLsizei height, GLboolean fixedsamplelocations);
typedef void (APIENTRY *glTexImage3DMultisamplePROC) (GLenum target, GLsizei samples, GLint internalformat, GLsizei width, GLsizei height, GLsizei depth, GLboolean fixedsamplelocations);
typedef void (APIENTRY *glGetMultisamplefvPROC) (GLenum pname, GLuint index, GLfloat * val);
typedef void (APIENTRY *glSampleMaskiPROC) (GLuint index, GLbitfield mask);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTextureMultisample_nglTexImage2DMultisample(JNIEnv *env, jclass clazz, jint target, jint samples, jint internalformat, jint width, jint height, jboolean fixedsamplelocations, jlong function_pointer) {
	glTexImage2DMultisamplePROC glTexImage2DMultisample = (glTexImage2DMultisamplePROC)((intptr_t)function_pointer);
	glTexImage2DMultisample(target, samples, internalformat, width, height, fixedsamplelocations);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTextureMultisample_nglTexImage3DMultisample(JNIEnv *env, jclass clazz, jint target, jint samples, jint internalformat, jint width, jint height, jint depth, jboolean fixedsamplelocations, jlong function_pointer) {
	glTexImage3DMultisamplePROC glTexImage3DMultisample = (glTexImage3DMultisamplePROC)((intptr_t)function_pointer);
	glTexImage3DMultisample(target, samples, internalformat, width, height, depth, fixedsamplelocations);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTextureMultisample_nglGetMultisamplefv(JNIEnv *env, jclass clazz, jint pname, jint index, jobject val, jint val_position, jlong function_pointer) {
	GLfloat *val_address = ((GLfloat *)(*env)->GetDirectBufferAddress(env, val)) + val_position;
	glGetMultisamplefvPROC glGetMultisamplefv = (glGetMultisamplefvPROC)((intptr_t)function_pointer);
	glGetMultisamplefv(pname, index, val_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTextureMultisample_nglSampleMaski(JNIEnv *env, jclass clazz, jint index, jint mask, jlong function_pointer) {
	glSampleMaskiPROC glSampleMaski = (glSampleMaskiPROC)((intptr_t)function_pointer);
	glSampleMaski(index, mask);
}

