/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glCopyBufferSubDataPROC) (GLenum readTarget, GLenum writeTarget, GLintptr readOffset, GLintptr writeOffset, GLsizeiptr size);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBCopyBuffer_nglCopyBufferSubData(JNIEnv *env, jclass clazz, jint readTarget, jint writeTarget, jlong readOffset, jlong writeOffset, jlong size, jlong function_pointer) {
	glCopyBufferSubDataPROC glCopyBufferSubData = (glCopyBufferSubDataPROC)((intptr_t)function_pointer);
	glCopyBufferSubData(readTarget, writeTarget, readOffset, writeOffset, size);
}

