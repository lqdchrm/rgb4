/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glGenSamplersPROC) (GLsizei count, GLuint * samplers);
typedef void (APIENTRY *glDeleteSamplersPROC) (GLsizei count, const GLuint * samplers);
typedef GLboolean (APIENTRY *glIsSamplerPROC) (GLuint sampler);
typedef void (APIENTRY *glBindSamplerPROC) (GLenum unit, GLuint sampler);
typedef void (APIENTRY *glSamplerParameteriPROC) (GLuint sampler, GLenum pname, GLint param);
typedef void (APIENTRY *glSamplerParameterfPROC) (GLuint sampler, GLenum pname, GLfloat param);
typedef void (APIENTRY *glSamplerParameterivPROC) (GLuint sampler, GLenum pname, const GLint * params);
typedef void (APIENTRY *glSamplerParameterfvPROC) (GLuint sampler, GLenum pname, const GLfloat * params);
typedef void (APIENTRY *glSamplerParameterIivPROC) (GLuint sampler, GLenum pname, const GLint * params);
typedef void (APIENTRY *glSamplerParameterIuivPROC) (GLuint sampler, GLenum pname, const GLuint * params);
typedef void (APIENTRY *glGetSamplerParameterivPROC) (GLuint sampler, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetSamplerParameterfvPROC) (GLuint sampler, GLenum pname, GLfloat * params);
typedef void (APIENTRY *glGetSamplerParameterIivPROC) (GLuint sampler, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetSamplerParameterIuivPROC) (GLuint sampler, GLenum pname, GLint * params);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglGenSamplers(JNIEnv *env, jclass clazz, jint count, jobject samplers, jint samplers_position, jlong function_pointer) {
	GLuint *samplers_address = ((GLuint *)(*env)->GetDirectBufferAddress(env, samplers)) + samplers_position;
	glGenSamplersPROC glGenSamplers = (glGenSamplersPROC)((intptr_t)function_pointer);
	glGenSamplers(count, samplers_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglDeleteSamplers(JNIEnv *env, jclass clazz, jint count, jobject samplers, jint samplers_position, jlong function_pointer) {
	const GLuint *samplers_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, samplers)) + samplers_position;
	glDeleteSamplersPROC glDeleteSamplers = (glDeleteSamplersPROC)((intptr_t)function_pointer);
	glDeleteSamplers(count, samplers_address);
}

JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglIsSampler(JNIEnv *env, jclass clazz, jint sampler, jlong function_pointer) {
	glIsSamplerPROC glIsSampler = (glIsSamplerPROC)((intptr_t)function_pointer);
	GLboolean __result = glIsSampler(sampler);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglBindSampler(JNIEnv *env, jclass clazz, jint unit, jint sampler, jlong function_pointer) {
	glBindSamplerPROC glBindSampler = (glBindSamplerPROC)((intptr_t)function_pointer);
	glBindSampler(unit, sampler);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglSamplerParameteri(JNIEnv *env, jclass clazz, jint sampler, jint pname, jint param, jlong function_pointer) {
	glSamplerParameteriPROC glSamplerParameteri = (glSamplerParameteriPROC)((intptr_t)function_pointer);
	glSamplerParameteri(sampler, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglSamplerParameterf(JNIEnv *env, jclass clazz, jint sampler, jint pname, jfloat param, jlong function_pointer) {
	glSamplerParameterfPROC glSamplerParameterf = (glSamplerParameterfPROC)((intptr_t)function_pointer);
	glSamplerParameterf(sampler, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglSamplerParameteriv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jobject params, jint params_position, jlong function_pointer) {
	const GLint *params_address = ((const GLint *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glSamplerParameterivPROC glSamplerParameteriv = (glSamplerParameterivPROC)((intptr_t)function_pointer);
	glSamplerParameteriv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglSamplerParameterfv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jobject params, jint params_position, jlong function_pointer) {
	const GLfloat *params_address = ((const GLfloat *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glSamplerParameterfvPROC glSamplerParameterfv = (glSamplerParameterfvPROC)((intptr_t)function_pointer);
	glSamplerParameterfv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglSamplerParameterIiv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jobject params, jint params_position, jlong function_pointer) {
	const GLint *params_address = ((const GLint *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glSamplerParameterIivPROC glSamplerParameterIiv = (glSamplerParameterIivPROC)((intptr_t)function_pointer);
	glSamplerParameterIiv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglSamplerParameterIuiv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jobject params, jint params_position, jlong function_pointer) {
	const GLuint *params_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glSamplerParameterIuivPROC glSamplerParameterIuiv = (glSamplerParameterIuivPROC)((intptr_t)function_pointer);
	glSamplerParameterIuiv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglGetSamplerParameteriv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jobject params, jint params_position, jlong function_pointer) {
	GLint *params_address = ((GLint *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetSamplerParameterivPROC glGetSamplerParameteriv = (glGetSamplerParameterivPROC)((intptr_t)function_pointer);
	glGetSamplerParameteriv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglGetSamplerParameterfv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jobject params, jint params_position, jlong function_pointer) {
	GLfloat *params_address = ((GLfloat *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetSamplerParameterfvPROC glGetSamplerParameterfv = (glGetSamplerParameterfvPROC)((intptr_t)function_pointer);
	glGetSamplerParameterfv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglGetSamplerParameterIiv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jobject params, jint params_position, jlong function_pointer) {
	GLint *params_address = ((GLint *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetSamplerParameterIivPROC glGetSamplerParameterIiv = (glGetSamplerParameterIivPROC)((intptr_t)function_pointer);
	glGetSamplerParameterIiv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBSamplerObjects_nglGetSamplerParameterIuiv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jobject params, jint params_position, jlong function_pointer) {
	GLint *params_address = ((GLint *)(*env)->GetDirectBufferAddress(env, params)) + params_position;
	glGetSamplerParameterIuivPROC glGetSamplerParameterIuiv = (glGetSamplerParameterIuivPROC)((intptr_t)function_pointer);
	glGetSamplerParameterIuiv(sampler, pname, params_address);
}

