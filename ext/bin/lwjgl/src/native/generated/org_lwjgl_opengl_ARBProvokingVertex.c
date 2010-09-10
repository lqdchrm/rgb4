/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glProvokingVertexPROC) (GLenum mode);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBProvokingVertex_nglProvokingVertex(JNIEnv *env, jclass clazz, jint mode, jlong function_pointer) {
	glProvokingVertexPROC glProvokingVertex = (glProvokingVertexPROC)((intptr_t)function_pointer);
	glProvokingVertex(mode);
}

