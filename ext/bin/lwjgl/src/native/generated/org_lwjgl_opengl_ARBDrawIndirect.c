/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glDrawArraysIndirectPROC) (GLenum mode, const GLvoid * indirect);
typedef void (APIENTRY *glDrawElementsIndirectPROC) (GLenum mode, GLenum type, const GLvoid * indirect);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBDrawIndirect_nglDrawArraysIndirect(JNIEnv *env, jclass clazz, jint mode, jobject indirect, jint indirect_position, jlong function_pointer) {
	const GLvoid *indirect_address = ((const GLvoid *)(((char *)(*env)->GetDirectBufferAddress(env, indirect)) + indirect_position));
	glDrawArraysIndirectPROC glDrawArraysIndirect = (glDrawArraysIndirectPROC)((intptr_t)function_pointer);
	glDrawArraysIndirect(mode, indirect_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBDrawIndirect_nglDrawArraysIndirectBO(JNIEnv *env, jclass clazz, jint mode, jlong indirect_buffer_offset, jlong function_pointer) {
	const GLvoid *indirect_address = ((const GLvoid *)offsetToPointer(indirect_buffer_offset));
	glDrawArraysIndirectPROC glDrawArraysIndirect = (glDrawArraysIndirectPROC)((intptr_t)function_pointer);
	glDrawArraysIndirect(mode, indirect_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBDrawIndirect_nglDrawElementsIndirect(JNIEnv *env, jclass clazz, jint mode, jint type, jobject indirect, jint indirect_position, jlong function_pointer) {
	const GLvoid *indirect_address = ((const GLvoid *)(((char *)(*env)->GetDirectBufferAddress(env, indirect)) + indirect_position));
	glDrawElementsIndirectPROC glDrawElementsIndirect = (glDrawElementsIndirectPROC)((intptr_t)function_pointer);
	glDrawElementsIndirect(mode, type, indirect_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBDrawIndirect_nglDrawElementsIndirectBO(JNIEnv *env, jclass clazz, jint mode, jint type, jlong indirect_buffer_offset, jlong function_pointer) {
	const GLvoid *indirect_address = ((const GLvoid *)offsetToPointer(indirect_buffer_offset));
	glDrawElementsIndirectPROC glDrawElementsIndirect = (glDrawElementsIndirectPROC)((intptr_t)function_pointer);
	glDrawElementsIndirect(mode, type, indirect_address);
}

