/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef GLboolean (APIENTRY *glIsRenderbufferPROC) (GLuint renderbuffer);
typedef void (APIENTRY *glBindRenderbufferPROC) (GLenum target, GLuint renderbuffer);
typedef void (APIENTRY *glDeleteRenderbuffersPROC) (GLsizei n, const GLuint * renderbuffers);
typedef void (APIENTRY *glGenRenderbuffersPROC) (GLsizei n, GLuint * renderbuffers);
typedef void (APIENTRY *glRenderbufferStoragePROC) (GLenum target, GLenum internalformat, GLsizei width, GLsizei height);
typedef void (APIENTRY *glRenderbufferStorageMultisamplePROC) (GLenum target, GLsizei samples, GLenum internalformat, GLsizei width, GLsizei height);
typedef void (APIENTRY *glGetRenderbufferParameterivPROC) (GLenum target, GLenum pname, GLint * params);
typedef GLboolean (APIENTRY *glIsFramebufferPROC) (GLuint framebuffer);
typedef void (APIENTRY *glBindFramebufferPROC) (GLenum target, GLuint framebuffer);
typedef void (APIENTRY *glDeleteFramebuffersPROC) (GLsizei n, const GLuint * framebuffers);
typedef void (APIENTRY *glGenFramebuffersPROC) (GLsizei n, GLuint * framebuffers);
typedef GLenum (APIENTRY *glCheckFramebufferStatusPROC) (GLenum target);
typedef void (APIENTRY *glFramebufferTexture1DPROC) (GLenum target, GLenum attachment, GLenum textarget, GLuint texture, GLint level);
typedef void (APIENTRY *glFramebufferTexture2DPROC) (GLenum target, GLenum attachment, GLenum textarget, GLuint texture, GLint level);
typedef void (APIENTRY *glFramebufferTexture3DPROC) (GLenum target, GLenum attachment, GLenum textarget, GLuint texture, GLint level, GLint layer);
typedef void (APIENTRY *glFramebufferTextureLayerPROC) (GLenum target, GLenum attachment, GLuint texture, GLint level, GLint layer);
typedef void (APIENTRY *glFramebufferRenderbufferPROC) (GLenum target, GLenum attachment, GLenum renderbuffertarget, GLuint renderbuffer);
typedef void (APIENTRY *glGetFramebufferAttachmentParameterivPROC) (GLenum target, GLenum attachment, GLenum pname, GLint * params);
typedef void (APIENTRY *glBlitFramebufferPROC) (GLint srcX0, GLint srcY0, GLint srcX1, GLint srcY1, GLint dstX0, GLint dstY0, GLint dstX1, GLint dstY1, GLbitfield mask, GLenum filter);
typedef void (APIENTRY *glGenerateMipmapPROC) (GLenum target);

JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglIsRenderbuffer(JNIEnv *env, jclass clazz, jint renderbuffer, jlong function_pointer) {
	glIsRenderbufferPROC glIsRenderbuffer = (glIsRenderbufferPROC)((intptr_t)function_pointer);
	GLboolean __result = glIsRenderbuffer(renderbuffer);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglBindRenderbuffer(JNIEnv *env, jclass clazz, jint target, jint renderbuffer, jlong function_pointer) {
	glBindRenderbufferPROC glBindRenderbuffer = (glBindRenderbufferPROC)((intptr_t)function_pointer);
	glBindRenderbuffer(target, renderbuffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglDeleteRenderbuffers(JNIEnv *env, jclass clazz, jint n, jobject renderbuffers, jint renderbuffers_position, jlong function_pointer) {
	const GLuint *renderbuffers_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, renderbuffers)) + renderbuffers_position;
	glDeleteRenderbuffersPROC glDeleteRenderbuffers = (glDeleteRenderbuffersPROC)((intptr_t)function_pointer);
	glDeleteRenderbuffers(n, renderbuffers_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglGenRenderbuffers(JNIEnv *env, jclass clazz, jint n, jobject renderbuffers, jint renderbuffers_position, jlong function_pointer) {
	GLuint *renderbuffers_address = ((GLuint *)(*env)->GetDirectBufferAddress(env, renderbuffers)) + renderbuffers_position;
	glGenRenderbuffersPROC glGenRenderbuffers = (glGenRenderbuffersPROC)((intptr_t)function_pointer);
	glGenRenderbuffers(n, renderbuffers_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglRenderbufferStorage(JNIEnv *env, jclass clazz, jint target, jint internalformat, jint width, jint height, jlong function_pointer) {
	glRenderbufferStoragePROC glRenderbufferStorage = (glRenderbufferStoragePROC)((intptr_t)function_pointer);
	glRenderbufferStorage(target, internalformat, width, height);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglRenderbufferStorageMultisample(JNIEnv *env, jclass clazz, jint target, jint samples, jint internalformat, jint width, jint height, jlong function_pointer) {
	glRenderbufferStorageMultisamplePROC glRenderbufferStorageMultisample = (glRenderbufferStorageMultisamplePROC)((intptr_t)function_pointer);
	glRenderbufferStorageMultisample(target, samples, internalformat, width, height);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglGetRenderbufferParameteriv(JNIEnv *env, jclass clazz, jint target, jint pname, jobject params, jint params_position, jlong function_pointer) {
	GLint *params_address = ((GLint *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetRenderbufferParameterivPROC glGetRenderbufferParameteriv = (glGetRenderbufferParameterivPROC)((intptr_t)function_pointer);
	glGetRenderbufferParameteriv(target, pname, params_address);
}

JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglIsFramebuffer(JNIEnv *env, jclass clazz, jint framebuffer, jlong function_pointer) {
	glIsFramebufferPROC glIsFramebuffer = (glIsFramebufferPROC)((intptr_t)function_pointer);
	GLboolean __result = glIsFramebuffer(framebuffer);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglBindFramebuffer(JNIEnv *env, jclass clazz, jint target, jint framebuffer, jlong function_pointer) {
	glBindFramebufferPROC glBindFramebuffer = (glBindFramebufferPROC)((intptr_t)function_pointer);
	glBindFramebuffer(target, framebuffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglDeleteFramebuffers(JNIEnv *env, jclass clazz, jint n, jobject framebuffers, jint framebuffers_position, jlong function_pointer) {
	const GLuint *framebuffers_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, framebuffers)) + framebuffers_position;
	glDeleteFramebuffersPROC glDeleteFramebuffers = (glDeleteFramebuffersPROC)((intptr_t)function_pointer);
	glDeleteFramebuffers(n, framebuffers_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglGenFramebuffers(JNIEnv *env, jclass clazz, jint n, jobject framebuffers, jint framebuffers_position, jlong function_pointer) {
	GLuint *framebuffers_address = ((GLuint *)(*env)->GetDirectBufferAddress(env, framebuffers)) + framebuffers_position;
	glGenFramebuffersPROC glGenFramebuffers = (glGenFramebuffersPROC)((intptr_t)function_pointer);
	glGenFramebuffers(n, framebuffers_address);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglCheckFramebufferStatus(JNIEnv *env, jclass clazz, jint target, jlong function_pointer) {
	glCheckFramebufferStatusPROC glCheckFramebufferStatus = (glCheckFramebufferStatusPROC)((intptr_t)function_pointer);
	GLenum __result = glCheckFramebufferStatus(target);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglFramebufferTexture1D(JNIEnv *env, jclass clazz, jint target, jint attachment, jint textarget, jint texture, jint level, jlong function_pointer) {
	glFramebufferTexture1DPROC glFramebufferTexture1D = (glFramebufferTexture1DPROC)((intptr_t)function_pointer);
	glFramebufferTexture1D(target, attachment, textarget, texture, level);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglFramebufferTexture2D(JNIEnv *env, jclass clazz, jint target, jint attachment, jint textarget, jint texture, jint level, jlong function_pointer) {
	glFramebufferTexture2DPROC glFramebufferTexture2D = (glFramebufferTexture2DPROC)((intptr_t)function_pointer);
	glFramebufferTexture2D(target, attachment, textarget, texture, level);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglFramebufferTexture3D(JNIEnv *env, jclass clazz, jint target, jint attachment, jint textarget, jint texture, jint level, jint layer, jlong function_pointer) {
	glFramebufferTexture3DPROC glFramebufferTexture3D = (glFramebufferTexture3DPROC)((intptr_t)function_pointer);
	glFramebufferTexture3D(target, attachment, textarget, texture, level, layer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglFramebufferTextureLayer(JNIEnv *env, jclass clazz, jint target, jint attachment, jint texture, jint level, jint layer, jlong function_pointer) {
	glFramebufferTextureLayerPROC glFramebufferTextureLayer = (glFramebufferTextureLayerPROC)((intptr_t)function_pointer);
	glFramebufferTextureLayer(target, attachment, texture, level, layer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglFramebufferRenderbuffer(JNIEnv *env, jclass clazz, jint target, jint attachment, jint renderbuffertarget, jint renderbuffer, jlong function_pointer) {
	glFramebufferRenderbufferPROC glFramebufferRenderbuffer = (glFramebufferRenderbufferPROC)((intptr_t)function_pointer);
	glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglGetFramebufferAttachmentParameteriv(JNIEnv *env, jclass clazz, jint target, jint attachment, jint pname, jobject params, jint params_position, jlong function_pointer) {
	GLint *params_address = ((GLint *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetFramebufferAttachmentParameterivPROC glGetFramebufferAttachmentParameteriv = (glGetFramebufferAttachmentParameterivPROC)((intptr_t)function_pointer);
	glGetFramebufferAttachmentParameteriv(target, attachment, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglBlitFramebuffer(JNIEnv *env, jclass clazz, jint srcX0, jint srcY0, jint srcX1, jint srcY1, jint dstX0, jint dstY0, jint dstX1, jint dstY1, jint mask, jint filter, jlong function_pointer) {
	glBlitFramebufferPROC glBlitFramebuffer = (glBlitFramebufferPROC)((intptr_t)function_pointer);
	glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBFramebufferObject_nglGenerateMipmap(JNIEnv *env, jclass clazz, jint target, jlong function_pointer) {
	glGenerateMipmapPROC glGenerateMipmap = (glGenerateMipmapPROC)((intptr_t)function_pointer);
	glGenerateMipmap(target);
}

