/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glBindTransformFeedbackPROC) (GLenum target, GLuint id);
typedef void (APIENTRY *glDeleteTransformFeedbacksPROC) (GLsizei n, const GLuint * ids);
typedef void (APIENTRY *glGenTransformFeedbacksPROC) (GLsizei n, GLuint * ids);
typedef GLboolean (APIENTRY *glIsTransformFeedbackPROC) (GLuint id);
typedef void (APIENTRY *glPauseTransformFeedbackPROC) ();
typedef void (APIENTRY *glResumeTransformFeedbackPROC) ();
typedef void (APIENTRY *glDrawTransformFeedbackPROC) (GLenum mode, GLuint id);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTransformFeedback2_nglBindTransformFeedback(JNIEnv *env, jclass clazz, jint target, jint id, jlong function_pointer) {
	glBindTransformFeedbackPROC glBindTransformFeedback = (glBindTransformFeedbackPROC)((intptr_t)function_pointer);
	glBindTransformFeedback(target, id);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTransformFeedback2_nglDeleteTransformFeedbacks(JNIEnv *env, jclass clazz, jint n, jobject ids, jint ids_position, jlong function_pointer) {
	const GLuint *ids_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, ids)) + ids_position;
	glDeleteTransformFeedbacksPROC glDeleteTransformFeedbacks = (glDeleteTransformFeedbacksPROC)((intptr_t)function_pointer);
	glDeleteTransformFeedbacks(n, ids_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTransformFeedback2_nglGenTransformFeedbacks(JNIEnv *env, jclass clazz, jint n, jobject ids, jint ids_position, jlong function_pointer) {
	GLuint *ids_address = ((GLuint *)(*env)->GetDirectBufferAddress(env, ids)) + ids_position;
	glGenTransformFeedbacksPROC glGenTransformFeedbacks = (glGenTransformFeedbacksPROC)((intptr_t)function_pointer);
	glGenTransformFeedbacks(n, ids_address);
}

JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_ARBTransformFeedback2_nglIsTransformFeedback(JNIEnv *env, jclass clazz, jint id, jlong function_pointer) {
	glIsTransformFeedbackPROC glIsTransformFeedback = (glIsTransformFeedbackPROC)((intptr_t)function_pointer);
	GLboolean __result = glIsTransformFeedback(id);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTransformFeedback2_nglPauseTransformFeedback(JNIEnv *env, jclass clazz, jlong function_pointer) {
	glPauseTransformFeedbackPROC glPauseTransformFeedback = (glPauseTransformFeedbackPROC)((intptr_t)function_pointer);
	glPauseTransformFeedback();
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTransformFeedback2_nglResumeTransformFeedback(JNIEnv *env, jclass clazz, jlong function_pointer) {
	glResumeTransformFeedbackPROC glResumeTransformFeedback = (glResumeTransformFeedbackPROC)((intptr_t)function_pointer);
	glResumeTransformFeedback();
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBTransformFeedback2_nglDrawTransformFeedback(JNIEnv *env, jclass clazz, jint mode, jint id, jlong function_pointer) {
	glDrawTransformFeedbackPROC glDrawTransformFeedback = (glDrawTransformFeedbackPROC)((intptr_t)function_pointer);
	glDrawTransformFeedback(mode, id);
}

