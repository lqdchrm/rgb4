/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glGetUniformIndicesPROC) (GLuint program, GLsizei uniformCount, const GLchar ** uniformNames, GLuint * uniformIndices);
typedef void (APIENTRY *glGetActiveUniformsivPROC) (GLuint program, GLsizei uniformCount, const GLuint * uniformIndices, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetActiveUniformNamePROC) (GLuint program, GLuint uniformIndex, GLsizei bufSize, GLsizei * length, GLchar * uniformName);
typedef GLuint (APIENTRY *glGetUniformBlockIndexPROC) (GLuint program, const GLchar * uniformBlockName);
typedef void (APIENTRY *glGetActiveUniformBlockivPROC) (GLuint program, GLuint uniformBlockIndex, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetActiveUniformBlockNamePROC) (GLuint program, GLuint uniformBlockIndex, GLsizei bufSize, GLsizei * length, GLchar * uniformBlockName);
typedef void (APIENTRY *glBindBufferRangePROC) (GLenum target, GLuint index, GLuint buffer, GLintptr offset, GLsizeiptr size);
typedef void (APIENTRY *glBindBufferBasePROC) (GLenum target, GLuint index, GLuint buffer);
typedef void (APIENTRY *glGetIntegeri_vPROC) (GLenum value, GLuint index, GLint * data);
typedef void (APIENTRY *glUniformBlockBindingPROC) (GLuint program, GLuint uniformBlockIndex, GLuint uniformBlockBinding);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBUniformBufferObject_nglGetUniformIndices(JNIEnv *env, jclass clazz, jint program, jint uniformCount, jobject uniformNames, jint uniformNames_position, jobject uniformIndices, jint uniformIndices_position, jlong function_pointer) {
	const GLchar *uniformNames_address = ((const GLchar *)(*env)->GetDirectBufferAddress(env, uniformNames)) + uniformNames_position;
	unsigned int _str_i;
	GLchar *_str_address;
	GLchar **uniformNames_str = (GLchar **) malloc(uniformCount*sizeof(GLchar*));
	GLuint *uniformIndices_address = ((GLuint *)(*env)->GetDirectBufferAddress(env, uniformIndices)) + uniformIndices_position;
	glGetUniformIndicesPROC glGetUniformIndices = (glGetUniformIndicesPROC)((intptr_t)function_pointer);
	_str_i = 0;
	_str_address = (GLchar *)uniformNames_address;
	while ( _str_i < uniformCount ) {
		uniformNames_str[_str_i++] = _str_address;
		_str_address += strlen(_str_address) + 1;
	}
	glGetUniformIndices(program, uniformCount, (const GLchar **)uniformNames_str, uniformIndices_address);
	free(uniformNames_str);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBUniformBufferObject_nglGetActiveUniformsiv(JNIEnv *env, jclass clazz, jint program, jint uniformCount, jobject uniformIndices, jint uniformIndices_position, jint pname, jobject params, jint params_position, jlong function_pointer) {
	const GLuint *uniformIndices_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, uniformIndices)) + uniformIndices_position;
	GLint *params_address = ((GLint *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetActiveUniformsivPROC glGetActiveUniformsiv = (glGetActiveUniformsivPROC)((intptr_t)function_pointer);
	glGetActiveUniformsiv(program, uniformCount, uniformIndices_address, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBUniformBufferObject_nglGetActiveUniformName(JNIEnv *env, jclass clazz, jint program, jint uniformIndex, jint bufSize, jobject length, jint length_position, jobject uniformName, jint uniformName_position, jlong function_pointer) {
	GLsizei *length_address = ((GLsizei *)safeGetBufferAddress(env, length)) + length_position;
	GLchar *uniformName_address = ((GLchar *)(*env)->GetDirectBufferAddress(env, uniformName)) + uniformName_position;
	glGetActiveUniformNamePROC glGetActiveUniformName = (glGetActiveUniformNamePROC)((intptr_t)function_pointer);
	glGetActiveUniformName(program, uniformIndex, bufSize, length_address, uniformName_address);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_ARBUniformBufferObject_nglGetUniformBlockIndex(JNIEnv *env, jclass clazz, jint program, jobject uniformBlockName, jint uniformBlockName_position, jlong function_pointer) {
	const GLchar *uniformBlockName_address = ((const GLchar *)(*env)->GetDirectBufferAddress(env, uniformBlockName)) + uniformBlockName_position;
	glGetUniformBlockIndexPROC glGetUniformBlockIndex = (glGetUniformBlockIndexPROC)((intptr_t)function_pointer);
	GLuint __result = glGetUniformBlockIndex(program, uniformBlockName_address);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBUniformBufferObject_nglGetActiveUniformBlockiv(JNIEnv *env, jclass clazz, jint program, jint uniformBlockIndex, jint pname, jobject params, jint params_position, jlong function_pointer) {
	GLint *params_address = ((GLint *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetActiveUniformBlockivPROC glGetActiveUniformBlockiv = (glGetActiveUniformBlockivPROC)((intptr_t)function_pointer);
	glGetActiveUniformBlockiv(program, uniformBlockIndex, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBUniformBufferObject_nglGetActiveUniformBlockName(JNIEnv *env, jclass clazz, jint program, jint uniformBlockIndex, jint bufSize, jobject length, jint length_position, jobject uniformBlockName, jint uniformBlockName_position, jlong function_pointer) {
	GLsizei *length_address = ((GLsizei *)safeGetBufferAddress(env, length)) + length_position;
	GLchar *uniformBlockName_address = ((GLchar *)(*env)->GetDirectBufferAddress(env, uniformBlockName)) + uniformBlockName_position;
	glGetActiveUniformBlockNamePROC glGetActiveUniformBlockName = (glGetActiveUniformBlockNamePROC)((intptr_t)function_pointer);
	glGetActiveUniformBlockName(program, uniformBlockIndex, bufSize, length_address, uniformBlockName_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBUniformBufferObject_nglBindBufferRange(JNIEnv *env, jclass clazz, jint target, jint index, jint buffer, jlong offset, jlong size, jlong function_pointer) {
	glBindBufferRangePROC glBindBufferRange = (glBindBufferRangePROC)((intptr_t)function_pointer);
	glBindBufferRange(target, index, buffer, offset, size);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBUniformBufferObject_nglBindBufferBase(JNIEnv *env, jclass clazz, jint target, jint index, jint buffer, jlong function_pointer) {
	glBindBufferBasePROC glBindBufferBase = (glBindBufferBasePROC)((intptr_t)function_pointer);
	glBindBufferBase(target, index, buffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBUniformBufferObject_nglGetIntegeri_v(JNIEnv *env, jclass clazz, jint value, jint index, jobject data, jint data_position, jlong function_pointer) {
	GLint *data_address = ((GLint *)(*env)->GetDirectBufferAddress(env, data)) + data_position;
	glGetIntegeri_vPROC glGetIntegeri_v = (glGetIntegeri_vPROC)((intptr_t)function_pointer);
	glGetIntegeri_v(value, index, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBUniformBufferObject_nglUniformBlockBinding(JNIEnv *env, jclass clazz, jint program, jint uniformBlockIndex, jint uniformBlockBinding, jlong function_pointer) {
	glUniformBlockBindingPROC glUniformBlockBinding = (glUniformBlockBindingPROC)((intptr_t)function_pointer);
	glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
}

