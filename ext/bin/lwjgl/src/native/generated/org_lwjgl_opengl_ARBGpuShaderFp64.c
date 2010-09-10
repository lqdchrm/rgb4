/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glUniform1dPROC) (GLint location, GLdouble x);
typedef void (APIENTRY *glUniform2dPROC) (GLint location, GLdouble x, GLdouble y);
typedef void (APIENTRY *glUniform3dPROC) (GLint location, GLdouble x, GLdouble y, GLdouble z);
typedef void (APIENTRY *glUniform4dPROC) (GLint location, GLdouble x, GLdouble y, GLdouble z, GLdouble w);
typedef void (APIENTRY *glUniform1dvPROC) (GLint location, GLsizei count, const GLdouble * value);
typedef void (APIENTRY *glUniform2dvPROC) (GLint location, GLsizei count, const GLdouble * value);
typedef void (APIENTRY *glUniform3dvPROC) (GLint location, GLsizei count, const GLdouble * value);
typedef void (APIENTRY *glUniform4dvPROC) (GLint location, GLsizei count, const GLdouble * value);
typedef void (APIENTRY *glUniformMatrix2dvPROC) (GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glUniformMatrix3dvPROC) (GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glUniformMatrix4dvPROC) (GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glUniformMatrix2x3dvPROC) (GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glUniformMatrix2x4dvPROC) (GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glUniformMatrix3x2dvPROC) (GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glUniformMatrix3x4dvPROC) (GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glUniformMatrix4x2dvPROC) (GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glUniformMatrix4x3dvPROC) (GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glGetUniformdvPROC) (GLuint program, GLint location, GLdouble * params);
typedef void (APIENTRY *glProgramUniform1dEXTPROC) (GLuint program, GLint location, GLdouble x);
typedef void (APIENTRY *glProgramUniform2dEXTPROC) (GLuint program, GLint location, GLdouble x, GLdouble y);
typedef void (APIENTRY *glProgramUniform3dEXTPROC) (GLuint program, GLint location, GLdouble x, GLdouble y, GLdouble z);
typedef void (APIENTRY *glProgramUniform4dEXTPROC) (GLuint program, GLint location, GLdouble x, GLdouble y, GLdouble z, GLdouble w);
typedef void (APIENTRY *glProgramUniform1dvEXTPROC) (GLuint program, GLint location, GLsizei count, const GLdouble * value);
typedef void (APIENTRY *glProgramUniform2dvEXTPROC) (GLuint program, GLint location, GLsizei count, const GLdouble * value);
typedef void (APIENTRY *glProgramUniform3dvEXTPROC) (GLuint program, GLint location, GLsizei count, const GLdouble * value);
typedef void (APIENTRY *glProgramUniform4dvEXTPROC) (GLuint program, GLint location, GLsizei count, const GLdouble * value);
typedef void (APIENTRY *glProgramUniformMatrix2dvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glProgramUniformMatrix3dvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glProgramUniformMatrix4dvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glProgramUniformMatrix2x3dvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glProgramUniformMatrix2x4dvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glProgramUniformMatrix3x2dvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glProgramUniformMatrix3x4dvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glProgramUniformMatrix4x2dvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);
typedef void (APIENTRY *glProgramUniformMatrix4x3dvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLdouble * value);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniform1d(JNIEnv *env, jclass clazz, jint location, jdouble x, jlong function_pointer) {
	glUniform1dPROC glUniform1d = (glUniform1dPROC)((intptr_t)function_pointer);
	glUniform1d(location, x);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniform2d(JNIEnv *env, jclass clazz, jint location, jdouble x, jdouble y, jlong function_pointer) {
	glUniform2dPROC glUniform2d = (glUniform2dPROC)((intptr_t)function_pointer);
	glUniform2d(location, x, y);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniform3d(JNIEnv *env, jclass clazz, jint location, jdouble x, jdouble y, jdouble z, jlong function_pointer) {
	glUniform3dPROC glUniform3d = (glUniform3dPROC)((intptr_t)function_pointer);
	glUniform3d(location, x, y, z);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniform4d(JNIEnv *env, jclass clazz, jint location, jdouble x, jdouble y, jdouble z, jdouble w, jlong function_pointer) {
	glUniform4dPROC glUniform4d = (glUniform4dPROC)((intptr_t)function_pointer);
	glUniform4d(location, x, y, z, w);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniform1dv(JNIEnv *env, jclass clazz, jint location, jint count, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniform1dvPROC glUniform1dv = (glUniform1dvPROC)((intptr_t)function_pointer);
	glUniform1dv(location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniform2dv(JNIEnv *env, jclass clazz, jint location, jint count, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniform2dvPROC glUniform2dv = (glUniform2dvPROC)((intptr_t)function_pointer);
	glUniform2dv(location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniform3dv(JNIEnv *env, jclass clazz, jint location, jint count, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniform3dvPROC glUniform3dv = (glUniform3dvPROC)((intptr_t)function_pointer);
	glUniform3dv(location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniform4dv(JNIEnv *env, jclass clazz, jint location, jint count, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniform4dvPROC glUniform4dv = (glUniform4dvPROC)((intptr_t)function_pointer);
	glUniform4dv(location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniformMatrix2dv(JNIEnv *env, jclass clazz, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniformMatrix2dvPROC glUniformMatrix2dv = (glUniformMatrix2dvPROC)((intptr_t)function_pointer);
	glUniformMatrix2dv(location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniformMatrix3dv(JNIEnv *env, jclass clazz, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniformMatrix3dvPROC glUniformMatrix3dv = (glUniformMatrix3dvPROC)((intptr_t)function_pointer);
	glUniformMatrix3dv(location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniformMatrix4dv(JNIEnv *env, jclass clazz, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniformMatrix4dvPROC glUniformMatrix4dv = (glUniformMatrix4dvPROC)((intptr_t)function_pointer);
	glUniformMatrix4dv(location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniformMatrix2x3dv(JNIEnv *env, jclass clazz, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniformMatrix2x3dvPROC glUniformMatrix2x3dv = (glUniformMatrix2x3dvPROC)((intptr_t)function_pointer);
	glUniformMatrix2x3dv(location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniformMatrix2x4dv(JNIEnv *env, jclass clazz, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniformMatrix2x4dvPROC glUniformMatrix2x4dv = (glUniformMatrix2x4dvPROC)((intptr_t)function_pointer);
	glUniformMatrix2x4dv(location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniformMatrix3x2dv(JNIEnv *env, jclass clazz, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniformMatrix3x2dvPROC glUniformMatrix3x2dv = (glUniformMatrix3x2dvPROC)((intptr_t)function_pointer);
	glUniformMatrix3x2dv(location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniformMatrix3x4dv(JNIEnv *env, jclass clazz, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniformMatrix3x4dvPROC glUniformMatrix3x4dv = (glUniformMatrix3x4dvPROC)((intptr_t)function_pointer);
	glUniformMatrix3x4dv(location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniformMatrix4x2dv(JNIEnv *env, jclass clazz, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniformMatrix4x2dvPROC glUniformMatrix4x2dv = (glUniformMatrix4x2dvPROC)((intptr_t)function_pointer);
	glUniformMatrix4x2dv(location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglUniformMatrix4x3dv(JNIEnv *env, jclass clazz, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glUniformMatrix4x3dvPROC glUniformMatrix4x3dv = (glUniformMatrix4x3dvPROC)((intptr_t)function_pointer);
	glUniformMatrix4x3dv(location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglGetUniformdv(JNIEnv *env, jclass clazz, jint program, jint location, jobject params, jint params_position, jlong function_pointer) {
	GLdouble *params_address = ((GLdouble *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetUniformdvPROC glGetUniformdv = (glGetUniformdvPROC)((intptr_t)function_pointer);
	glGetUniformdv(program, location, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniform1dEXT(JNIEnv *env, jclass clazz, jint program, jint location, jdouble x, jlong function_pointer) {
	glProgramUniform1dEXTPROC glProgramUniform1dEXT = (glProgramUniform1dEXTPROC)((intptr_t)function_pointer);
	glProgramUniform1dEXT(program, location, x);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniform2dEXT(JNIEnv *env, jclass clazz, jint program, jint location, jdouble x, jdouble y, jlong function_pointer) {
	glProgramUniform2dEXTPROC glProgramUniform2dEXT = (glProgramUniform2dEXTPROC)((intptr_t)function_pointer);
	glProgramUniform2dEXT(program, location, x, y);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniform3dEXT(JNIEnv *env, jclass clazz, jint program, jint location, jdouble x, jdouble y, jdouble z, jlong function_pointer) {
	glProgramUniform3dEXTPROC glProgramUniform3dEXT = (glProgramUniform3dEXTPROC)((intptr_t)function_pointer);
	glProgramUniform3dEXT(program, location, x, y, z);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniform4dEXT(JNIEnv *env, jclass clazz, jint program, jint location, jdouble x, jdouble y, jdouble z, jdouble w, jlong function_pointer) {
	glProgramUniform4dEXTPROC glProgramUniform4dEXT = (glProgramUniform4dEXTPROC)((intptr_t)function_pointer);
	glProgramUniform4dEXT(program, location, x, y, z, w);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniform1dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniform1dvEXTPROC glProgramUniform1dvEXT = (glProgramUniform1dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniform1dvEXT(program, location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniform2dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniform2dvEXTPROC glProgramUniform2dvEXT = (glProgramUniform2dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniform2dvEXT(program, location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniform3dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniform3dvEXTPROC glProgramUniform3dvEXT = (glProgramUniform3dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniform3dvEXT(program, location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniform4dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniform4dvEXTPROC glProgramUniform4dvEXT = (glProgramUniform4dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniform4dvEXT(program, location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniformMatrix2dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniformMatrix2dvEXTPROC glProgramUniformMatrix2dvEXT = (glProgramUniformMatrix2dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix2dvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniformMatrix3dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniformMatrix3dvEXTPROC glProgramUniformMatrix3dvEXT = (glProgramUniformMatrix3dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix3dvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniformMatrix4dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniformMatrix4dvEXTPROC glProgramUniformMatrix4dvEXT = (glProgramUniformMatrix4dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix4dvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniformMatrix2x3dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniformMatrix2x3dvEXTPROC glProgramUniformMatrix2x3dvEXT = (glProgramUniformMatrix2x3dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix2x3dvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniformMatrix2x4dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniformMatrix2x4dvEXTPROC glProgramUniformMatrix2x4dvEXT = (glProgramUniformMatrix2x4dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix2x4dvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniformMatrix3x2dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniformMatrix3x2dvEXTPROC glProgramUniformMatrix3x2dvEXT = (glProgramUniformMatrix3x2dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix3x2dvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniformMatrix3x4dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniformMatrix3x4dvEXTPROC glProgramUniformMatrix3x4dvEXT = (glProgramUniformMatrix3x4dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix3x4dvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniformMatrix4x2dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniformMatrix4x2dvEXTPROC glProgramUniformMatrix4x2dvEXT = (glProgramUniformMatrix4x2dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix4x2dvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBGpuShaderFp64_nglProgramUniformMatrix4x3dvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jobject value, jint value_position, jlong function_pointer) {
	const GLdouble *value_address = ((const GLdouble *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glProgramUniformMatrix4x3dvEXTPROC glProgramUniformMatrix4x3dvEXT = (glProgramUniformMatrix4x3dvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix4x3dvEXT(program, location, count, transpose, value_address);
}

