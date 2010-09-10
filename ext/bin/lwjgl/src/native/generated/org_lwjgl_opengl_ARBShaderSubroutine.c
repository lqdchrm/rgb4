/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef GLint (APIENTRY *glGetSubroutineUniformLocationPROC) (GLuint program, GLenum shadertype, const GLbyte * name);
typedef GLuint (APIENTRY *glGetSubroutineIndexPROC) (GLuint program, GLenum shadertype, const GLbyte * name);
typedef void (APIENTRY *glGetActiveSubroutineUniformivPROC) (GLuint program, GLenum shadertype, GLuint index, GLenum pname, GLint * values);
typedef void (APIENTRY *glGetActiveSubroutineUniformNamePROC) (GLuint program, GLenum shadertype, GLuint index, GLsizei bufsize, GLsizei * length, GLbyte * name);
typedef void (APIENTRY *glGetActiveSubroutineNamePROC) (GLuint program, GLenum shadertype, GLuint index, GLsizei bufsize, GLsizei * length, GLbyte * name);
typedef void (APIENTRY *glUniformSubroutinesuivPROC) (GLenum shadertype, GLsizei count, const GLuint * indices);
typedef void (APIENTRY *glGetUniformSubroutineuivPROC) (GLenum shadertype, GLint location, GLuint * params);
typedef void (APIENTRY *glGetProgramStageivPROC) (GLuint program, GLenum shadertype, GLenum pname, GLint * values);

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_ARBShaderSubroutine_nglGetSubroutineUniformLocation(JNIEnv *env, jclass clazz, jint program, jint shadertype, jobject name, jint name_position, jlong function_pointer) {
	const GLbyte *name_address = ((const GLbyte *)(*env)->GetDirectBufferAddress(env, name)) + name_position;
	glGetSubroutineUniformLocationPROC glGetSubroutineUniformLocation = (glGetSubroutineUniformLocationPROC)((intptr_t)function_pointer);
	GLint __result = glGetSubroutineUniformLocation(program, shadertype, name_address);
	return __result;
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_ARBShaderSubroutine_nglGetSubroutineIndex(JNIEnv *env, jclass clazz, jint program, jint shadertype, jobject name, jint name_position, jlong function_pointer) {
	const GLbyte *name_address = ((const GLbyte *)(*env)->GetDirectBufferAddress(env, name)) + name_position;
	glGetSubroutineIndexPROC glGetSubroutineIndex = (glGetSubroutineIndexPROC)((intptr_t)function_pointer);
	GLuint __result = glGetSubroutineIndex(program, shadertype, name_address);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBShaderSubroutine_nglGetActiveSubroutineUniformiv(JNIEnv *env, jclass clazz, jint program, jint shadertype, jint index, jint pname, jobject values, jint values_position, jlong function_pointer) {
	GLint *values_address = ((GLint *)(*env)->GetDirectBufferAddress(env, values)) + values_position;
	glGetActiveSubroutineUniformivPROC glGetActiveSubroutineUniformiv = (glGetActiveSubroutineUniformivPROC)((intptr_t)function_pointer);
	glGetActiveSubroutineUniformiv(program, shadertype, index, pname, values_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBShaderSubroutine_nglGetActiveSubroutineUniformName(JNIEnv *env, jclass clazz, jint program, jint shadertype, jint index, jint bufsize, jobject length, jint length_position, jobject name, jint name_position, jlong function_pointer) {
	GLsizei *length_address = ((GLsizei *)safeGetBufferAddress(env, length)) + length_position;
	GLbyte *name_address = ((GLbyte *)(*env)->GetDirectBufferAddress(env, name)) + name_position;
	glGetActiveSubroutineUniformNamePROC glGetActiveSubroutineUniformName = (glGetActiveSubroutineUniformNamePROC)((intptr_t)function_pointer);
	glGetActiveSubroutineUniformName(program, shadertype, index, bufsize, length_address, name_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBShaderSubroutine_nglGetActiveSubroutineName(JNIEnv *env, jclass clazz, jint program, jint shadertype, jint index, jint bufsize, jobject length, jint length_position, jobject name, jint name_position, jlong function_pointer) {
	GLsizei *length_address = ((GLsizei *)safeGetBufferAddress(env, length)) + length_position;
	GLbyte *name_address = ((GLbyte *)(*env)->GetDirectBufferAddress(env, name)) + name_position;
	glGetActiveSubroutineNamePROC glGetActiveSubroutineName = (glGetActiveSubroutineNamePROC)((intptr_t)function_pointer);
	glGetActiveSubroutineName(program, shadertype, index, bufsize, length_address, name_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBShaderSubroutine_nglUniformSubroutinesuiv(JNIEnv *env, jclass clazz, jint shadertype, jint count, jobject indices, jint indices_position, jlong function_pointer) {
	const GLuint *indices_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, indices)) + indices_position;
	glUniformSubroutinesuivPROC glUniformSubroutinesuiv = (glUniformSubroutinesuivPROC)((intptr_t)function_pointer);
	glUniformSubroutinesuiv(shadertype, count, indices_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBShaderSubroutine_nglGetUniformSubroutineuiv(JNIEnv *env, jclass clazz, jint shadertype, jint location, jobject params, jint params_position, jlong function_pointer) {
	GLuint *params_address = ((GLuint *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetUniformSubroutineuivPROC glGetUniformSubroutineuiv = (glGetUniformSubroutineuivPROC)((intptr_t)function_pointer);
	glGetUniformSubroutineuiv(shadertype, location, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBShaderSubroutine_nglGetProgramStageiv(JNIEnv *env, jclass clazz, jint program, jint shadertype, jint pname, jobject values, jint values_position, jlong function_pointer) {
	GLint *values_address = ((GLint *)(*env)->GetDirectBufferAddress(env, values)) + values_position;
	glGetProgramStageivPROC glGetProgramStageiv = (glGetProgramStageivPROC)((intptr_t)function_pointer);
	glGetProgramStageiv(program, shadertype, pname, values_address);
}

