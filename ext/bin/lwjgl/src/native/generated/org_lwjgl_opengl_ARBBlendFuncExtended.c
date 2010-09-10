/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glBindFragDataLocationIndexedPROC) (GLuint program, GLuint colorNumber, GLuint index, const GLchar * name);
typedef GLint (APIENTRY *glGetFragDataIndexPROC) (GLuint program, const GLchar * name);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBBlendFuncExtended_nglBindFragDataLocationIndexed(JNIEnv *env, jclass clazz, jint program, jint colorNumber, jint index, jobject name, jint name_position, jlong function_pointer) {
	const GLchar *name_address = ((const GLchar *)(*env)->GetDirectBufferAddress(env, name)) + name_position;
	glBindFragDataLocationIndexedPROC glBindFragDataLocationIndexed = (glBindFragDataLocationIndexedPROC)((intptr_t)function_pointer);
	glBindFragDataLocationIndexed(program, colorNumber, index, name_address);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_ARBBlendFuncExtended_nglGetFragDataIndex(JNIEnv *env, jclass clazz, jint program, jobject name, jint name_position, jlong function_pointer) {
	const GLchar *name_address = ((const GLchar *)(*env)->GetDirectBufferAddress(env, name)) + name_position;
	glGetFragDataIndexPROC glGetFragDataIndex = (glGetFragDataIndexPROC)((intptr_t)function_pointer);
	GLint __result = glGetFragDataIndex(program, name_address);
	return __result;
}

