/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glDrawElementsBaseVertexPROC) (GLenum mode, GLsizei count, GLenum type, const GLvoid * indices, GLint basevertex);
typedef void (APIENTRY *glDrawRangeElementsBaseVertexPROC) (GLenum mode, GLuint start, GLuint end, GLsizei count, GLenum type, const GLvoid * indices, GLint basevertex);
typedef void (APIENTRY *glDrawElementsInstancedBaseVertexPROC) (GLenum mode, GLsizei count, GLenum type, const GLvoid * indices, GLsizei primcount, GLint basevertex);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBDrawElementsBaseVertex_nglDrawElementsBaseVertex(JNIEnv *env, jclass clazz, jint mode, jint count, jint type, jobject indices, jint indices_position, jint basevertex, jlong function_pointer) {
	const GLvoid *indices_address = ((const GLvoid *)(((char *)(*env)->GetDirectBufferAddress(env, indices)) + indices_position));
	glDrawElementsBaseVertexPROC glDrawElementsBaseVertex = (glDrawElementsBaseVertexPROC)((intptr_t)function_pointer);
	glDrawElementsBaseVertex(mode, count, type, indices_address, basevertex);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBDrawElementsBaseVertex_nglDrawElementsBaseVertexBO(JNIEnv *env, jclass clazz, jint mode, jint count, jint type, jlong indices_buffer_offset, jint basevertex, jlong function_pointer) {
	const GLvoid *indices_address = ((const GLvoid *)offsetToPointer(indices_buffer_offset));
	glDrawElementsBaseVertexPROC glDrawElementsBaseVertex = (glDrawElementsBaseVertexPROC)((intptr_t)function_pointer);
	glDrawElementsBaseVertex(mode, count, type, indices_address, basevertex);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBDrawElementsBaseVertex_nglDrawRangeElementsBaseVertex(JNIEnv *env, jclass clazz, jint mode, jint start, jint end, jint count, jint type, jobject indices, jint indices_position, jint basevertex, jlong function_pointer) {
	const GLvoid *indices_address = ((const GLvoid *)(((char *)(*env)->GetDirectBufferAddress(env, indices)) + indices_position));
	glDrawRangeElementsBaseVertexPROC glDrawRangeElementsBaseVertex = (glDrawRangeElementsBaseVertexPROC)((intptr_t)function_pointer);
	glDrawRangeElementsBaseVertex(mode, start, end, count, type, indices_address, basevertex);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBDrawElementsBaseVertex_nglDrawRangeElementsBaseVertexBO(JNIEnv *env, jclass clazz, jint mode, jint start, jint end, jint count, jint type, jlong indices_buffer_offset, jint basevertex, jlong function_pointer) {
	const GLvoid *indices_address = ((const GLvoid *)offsetToPointer(indices_buffer_offset));
	glDrawRangeElementsBaseVertexPROC glDrawRangeElementsBaseVertex = (glDrawRangeElementsBaseVertexPROC)((intptr_t)function_pointer);
	glDrawRangeElementsBaseVertex(mode, start, end, count, type, indices_address, basevertex);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBDrawElementsBaseVertex_nglDrawElementsInstancedBaseVertex(JNIEnv *env, jclass clazz, jint mode, jint count, jint type, jobject indices, jint indices_position, jint primcount, jint basevertex, jlong function_pointer) {
	const GLvoid *indices_address = ((const GLvoid *)(((char *)(*env)->GetDirectBufferAddress(env, indices)) + indices_position));
	glDrawElementsInstancedBaseVertexPROC glDrawElementsInstancedBaseVertex = (glDrawElementsInstancedBaseVertexPROC)((intptr_t)function_pointer);
	glDrawElementsInstancedBaseVertex(mode, count, type, indices_address, primcount, basevertex);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBDrawElementsBaseVertex_nglDrawElementsInstancedBaseVertexBO(JNIEnv *env, jclass clazz, jint mode, jint count, jint type, jlong indices_buffer_offset, jint primcount, jint basevertex, jlong function_pointer) {
	const GLvoid *indices_address = ((const GLvoid *)offsetToPointer(indices_buffer_offset));
	glDrawElementsInstancedBaseVertexPROC glDrawElementsInstancedBaseVertex = (glDrawElementsInstancedBaseVertexPROC)((intptr_t)function_pointer);
	glDrawElementsInstancedBaseVertex(mode, count, type, indices_address, primcount, basevertex);
}

