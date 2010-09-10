/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef GLsync (APIENTRY *glFenceSyncPROC) (GLenum condition, GLbitfield flags);
typedef GLboolean (APIENTRY *glIsSyncPROC) (GLsync sync);
typedef void (APIENTRY *glDeleteSyncPROC) (GLsync sync);
typedef GLenum (APIENTRY *glClientWaitSyncPROC) (GLsync sync, GLbitfield flags, GLuint64 timeout);
typedef void (APIENTRY *glWaitSyncPROC) (GLsync sync, GLbitfield flags, GLuint64 timeout);
typedef void (APIENTRY *glGetInteger64vPROC) (GLenum pname, GLint64 * params);
typedef void (APIENTRY *glGetSyncivPROC) (GLsync sync, GLenum pname, GLsizei bufSize, GLsizei * length, GLint * values);

JNIEXPORT jlong JNICALL Java_org_lwjgl_opengl_ARBSync_nglFenceSync(JNIEnv *env, jclass clazz, jint condition, jint flags, jlong function_pointer) {
	glFenceSyncPROC glFenceSync = (glFenceSyncPROC)((intptr_t)function_pointer);
	GLsync __result = glFenceSync(condition, flags);
	return (intptr_t)__result;
}

JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_ARBSync_nglIsSync(JNIEnv *env, jclass clazz, jlong sync, jlong function_pointer) {
	glIsSyncPROC glIsSync = (glIsSyncPROC)((intptr_t)function_pointer);
	GLboolean __result = glIsSync((GLsync)(intptr_t)sync);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSync_nglDeleteSync(JNIEnv *env, jclass clazz, jlong sync, jlong function_pointer) {
	glDeleteSyncPROC glDeleteSync = (glDeleteSyncPROC)((intptr_t)function_pointer);
	glDeleteSync((GLsync)(intptr_t)sync);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_ARBSync_nglClientWaitSync(JNIEnv *env, jclass clazz, jlong sync, jint flags, jlong timeout, jlong function_pointer) {
	glClientWaitSyncPROC glClientWaitSync = (glClientWaitSyncPROC)((intptr_t)function_pointer);
	GLenum __result = glClientWaitSync((GLsync)(intptr_t)sync, flags, timeout);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSync_nglWaitSync(JNIEnv *env, jclass clazz, jlong sync, jint flags, jlong timeout, jlong function_pointer) {
	glWaitSyncPROC glWaitSync = (glWaitSyncPROC)((intptr_t)function_pointer);
	glWaitSync((GLsync)(intptr_t)sync, flags, timeout);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSync_nglGetInteger64v(JNIEnv *env, jclass clazz, jint pname, jobject params, jint params_position, jlong function_pointer) {
	GLint64 *params_address = ((GLint64 *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetInteger64vPROC glGetInteger64v = (glGetInteger64vPROC)((intptr_t)function_pointer);
	glGetInteger64v(pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSync_nglGetSynciv(JNIEnv *env, jclass clazz, jlong sync, jint pname, jint bufSize, jobject length, jint length_position, jobject values, jint values_position, jlong function_pointer) {
	GLsizei *length_address = ((GLsizei *)safeGetBufferAddress(env, length)) + length_position;
	GLint *values_address = ((GLint *)(*env)->GetDirectBufferAddress(env, values)) + values_position;
	glGetSyncivPROC glGetSynciv = (glGetSyncivPROC)((intptr_t)function_pointer);
	glGetSynciv((GLsync)(intptr_t)sync, pname, bufSize, length_address, values_address);
}

