/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glVertexP2uiPROC) (GLenum type, GLuint value);
typedef void (APIENTRY *glVertexP3uiPROC) (GLenum type, GLuint value);
typedef void (APIENTRY *glVertexP4uiPROC) (GLenum type, GLuint value);
typedef void (APIENTRY *glVertexP2uivPROC) (GLenum type, const GLuint * value);
typedef void (APIENTRY *glVertexP3uivPROC) (GLenum type, const GLuint * value);
typedef void (APIENTRY *glVertexP4uivPROC) (GLenum type, const GLuint * value);
typedef void (APIENTRY *glTexCoordP1uiPROC) (GLenum type, GLuint coords);
typedef void (APIENTRY *glTexCoordP2uiPROC) (GLenum type, GLuint coords);
typedef void (APIENTRY *glTexCoordP3uiPROC) (GLenum type, GLuint coords);
typedef void (APIENTRY *glTexCoordP4uiPROC) (GLenum type, GLuint coords);
typedef void (APIENTRY *glTexCoordP1uivPROC) (GLenum type, const GLuint * coords);
typedef void (APIENTRY *glTexCoordP2uivPROC) (GLenum type, const GLuint * coords);
typedef void (APIENTRY *glTexCoordP3uivPROC) (GLenum type, const GLuint * coords);
typedef void (APIENTRY *glTexCoordP4uivPROC) (GLenum type, const GLuint * coords);
typedef void (APIENTRY *glMultiTexCoordP1uiPROC) (GLenum texture, GLenum type, GLuint coords);
typedef void (APIENTRY *glMultiTexCoordP2uiPROC) (GLenum texture, GLenum type, GLuint coords);
typedef void (APIENTRY *glMultiTexCoordP3uiPROC) (GLenum texture, GLenum type, GLuint coords);
typedef void (APIENTRY *glMultiTexCoordP4uiPROC) (GLenum texture, GLenum type, GLuint coords);
typedef void (APIENTRY *glMultiTexCoordP1uivPROC) (GLenum texture, GLenum type, const GLuint * coords);
typedef void (APIENTRY *glMultiTexCoordP2uivPROC) (GLenum texture, GLenum type, const GLuint * coords);
typedef void (APIENTRY *glMultiTexCoordP3uivPROC) (GLenum texture, GLenum type, const GLuint * coords);
typedef void (APIENTRY *glMultiTexCoordP4uivPROC) (GLenum texture, GLenum type, const GLuint * coords);
typedef void (APIENTRY *glNormalP3uiPROC) (GLenum type, GLuint coords);
typedef void (APIENTRY *glNormalP3uivPROC) (GLenum type, const GLuint * coords);
typedef void (APIENTRY *glColorP3uiPROC) (GLenum type, GLuint color);
typedef void (APIENTRY *glColorP4uiPROC) (GLenum type, GLuint color);
typedef void (APIENTRY *glColorP3uivPROC) (GLenum type, const GLuint * color);
typedef void (APIENTRY *glColorP4uivPROC) (GLenum type, const GLuint * color);
typedef void (APIENTRY *glSecondaryColorP3uiPROC) (GLenum type, GLuint color);
typedef void (APIENTRY *glSecondaryColorP3uivPROC) (GLenum type, const GLuint * color);
typedef void (APIENTRY *glVertexAttribP1uiPROC) (GLuint index, GLenum type, GLboolean normalized, GLuint value);
typedef void (APIENTRY *glVertexAttribP2uiPROC) (GLuint index, GLenum type, GLboolean normalized, GLuint value);
typedef void (APIENTRY *glVertexAttribP3uiPROC) (GLuint index, GLenum type, GLboolean normalized, GLuint value);
typedef void (APIENTRY *glVertexAttribP4uiPROC) (GLuint index, GLenum type, GLboolean normalized, GLuint value);
typedef void (APIENTRY *glVertexAttribP1uivPROC) (GLuint index, GLenum type, GLboolean normalized, const GLuint * value);
typedef void (APIENTRY *glVertexAttribP2uivPROC) (GLuint index, GLenum type, GLboolean normalized, const GLuint * value);
typedef void (APIENTRY *glVertexAttribP3uivPROC) (GLuint index, GLenum type, GLboolean normalized, const GLuint * value);
typedef void (APIENTRY *glVertexAttribP4uivPROC) (GLuint index, GLenum type, GLboolean normalized, const GLuint * value);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexP2ui(JNIEnv *env, jclass clazz, jint type, jint value, jlong function_pointer) {
	glVertexP2uiPROC glVertexP2ui = (glVertexP2uiPROC)((intptr_t)function_pointer);
	glVertexP2ui(type, value);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexP3ui(JNIEnv *env, jclass clazz, jint type, jint value, jlong function_pointer) {
	glVertexP3uiPROC glVertexP3ui = (glVertexP3uiPROC)((intptr_t)function_pointer);
	glVertexP3ui(type, value);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexP4ui(JNIEnv *env, jclass clazz, jint type, jint value, jlong function_pointer) {
	glVertexP4uiPROC glVertexP4ui = (glVertexP4uiPROC)((intptr_t)function_pointer);
	glVertexP4ui(type, value);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexP2uiv(JNIEnv *env, jclass clazz, jint type, jobject value, jint value_position, jlong function_pointer) {
	const GLuint *value_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glVertexP2uivPROC glVertexP2uiv = (glVertexP2uivPROC)((intptr_t)function_pointer);
	glVertexP2uiv(type, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexP3uiv(JNIEnv *env, jclass clazz, jint type, jobject value, jint value_position, jlong function_pointer) {
	const GLuint *value_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glVertexP3uivPROC glVertexP3uiv = (glVertexP3uivPROC)((intptr_t)function_pointer);
	glVertexP3uiv(type, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexP4uiv(JNIEnv *env, jclass clazz, jint type, jobject value, jint value_position, jlong function_pointer) {
	const GLuint *value_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glVertexP4uivPROC glVertexP4uiv = (glVertexP4uivPROC)((intptr_t)function_pointer);
	glVertexP4uiv(type, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglTexCoordP1ui(JNIEnv *env, jclass clazz, jint type, jint coords, jlong function_pointer) {
	glTexCoordP1uiPROC glTexCoordP1ui = (glTexCoordP1uiPROC)((intptr_t)function_pointer);
	glTexCoordP1ui(type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglTexCoordP2ui(JNIEnv *env, jclass clazz, jint type, jint coords, jlong function_pointer) {
	glTexCoordP2uiPROC glTexCoordP2ui = (glTexCoordP2uiPROC)((intptr_t)function_pointer);
	glTexCoordP2ui(type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglTexCoordP3ui(JNIEnv *env, jclass clazz, jint type, jint coords, jlong function_pointer) {
	glTexCoordP3uiPROC glTexCoordP3ui = (glTexCoordP3uiPROC)((intptr_t)function_pointer);
	glTexCoordP3ui(type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglTexCoordP4ui(JNIEnv *env, jclass clazz, jint type, jint coords, jlong function_pointer) {
	glTexCoordP4uiPROC glTexCoordP4ui = (glTexCoordP4uiPROC)((intptr_t)function_pointer);
	glTexCoordP4ui(type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglTexCoordP1uiv(JNIEnv *env, jclass clazz, jint type, jobject coords, jint coords_position, jlong function_pointer) {
	const GLuint *coords_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, coords)) + coords_position;
	glTexCoordP1uivPROC glTexCoordP1uiv = (glTexCoordP1uivPROC)((intptr_t)function_pointer);
	glTexCoordP1uiv(type, coords_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglTexCoordP2uiv(JNIEnv *env, jclass clazz, jint type, jobject coords, jint coords_position, jlong function_pointer) {
	const GLuint *coords_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, coords)) + coords_position;
	glTexCoordP2uivPROC glTexCoordP2uiv = (glTexCoordP2uivPROC)((intptr_t)function_pointer);
	glTexCoordP2uiv(type, coords_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglTexCoordP3uiv(JNIEnv *env, jclass clazz, jint type, jobject coords, jint coords_position, jlong function_pointer) {
	const GLuint *coords_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, coords)) + coords_position;
	glTexCoordP3uivPROC glTexCoordP3uiv = (glTexCoordP3uivPROC)((intptr_t)function_pointer);
	glTexCoordP3uiv(type, coords_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglTexCoordP4uiv(JNIEnv *env, jclass clazz, jint type, jobject coords, jint coords_position, jlong function_pointer) {
	const GLuint *coords_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, coords)) + coords_position;
	glTexCoordP4uivPROC glTexCoordP4uiv = (glTexCoordP4uivPROC)((intptr_t)function_pointer);
	glTexCoordP4uiv(type, coords_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglMultiTexCoordP1ui(JNIEnv *env, jclass clazz, jint texture, jint type, jint coords, jlong function_pointer) {
	glMultiTexCoordP1uiPROC glMultiTexCoordP1ui = (glMultiTexCoordP1uiPROC)((intptr_t)function_pointer);
	glMultiTexCoordP1ui(texture, type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglMultiTexCoordP2ui(JNIEnv *env, jclass clazz, jint texture, jint type, jint coords, jlong function_pointer) {
	glMultiTexCoordP2uiPROC glMultiTexCoordP2ui = (glMultiTexCoordP2uiPROC)((intptr_t)function_pointer);
	glMultiTexCoordP2ui(texture, type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglMultiTexCoordP3ui(JNIEnv *env, jclass clazz, jint texture, jint type, jint coords, jlong function_pointer) {
	glMultiTexCoordP3uiPROC glMultiTexCoordP3ui = (glMultiTexCoordP3uiPROC)((intptr_t)function_pointer);
	glMultiTexCoordP3ui(texture, type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglMultiTexCoordP4ui(JNIEnv *env, jclass clazz, jint texture, jint type, jint coords, jlong function_pointer) {
	glMultiTexCoordP4uiPROC glMultiTexCoordP4ui = (glMultiTexCoordP4uiPROC)((intptr_t)function_pointer);
	glMultiTexCoordP4ui(texture, type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglMultiTexCoordP1uiv(JNIEnv *env, jclass clazz, jint texture, jint type, jobject coords, jint coords_position, jlong function_pointer) {
	const GLuint *coords_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, coords)) + coords_position;
	glMultiTexCoordP1uivPROC glMultiTexCoordP1uiv = (glMultiTexCoordP1uivPROC)((intptr_t)function_pointer);
	glMultiTexCoordP1uiv(texture, type, coords_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglMultiTexCoordP2uiv(JNIEnv *env, jclass clazz, jint texture, jint type, jobject coords, jint coords_position, jlong function_pointer) {
	const GLuint *coords_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, coords)) + coords_position;
	glMultiTexCoordP2uivPROC glMultiTexCoordP2uiv = (glMultiTexCoordP2uivPROC)((intptr_t)function_pointer);
	glMultiTexCoordP2uiv(texture, type, coords_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglMultiTexCoordP3uiv(JNIEnv *env, jclass clazz, jint texture, jint type, jobject coords, jint coords_position, jlong function_pointer) {
	const GLuint *coords_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, coords)) + coords_position;
	glMultiTexCoordP3uivPROC glMultiTexCoordP3uiv = (glMultiTexCoordP3uivPROC)((intptr_t)function_pointer);
	glMultiTexCoordP3uiv(texture, type, coords_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglMultiTexCoordP4uiv(JNIEnv *env, jclass clazz, jint texture, jint type, jobject coords, jint coords_position, jlong function_pointer) {
	const GLuint *coords_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, coords)) + coords_position;
	glMultiTexCoordP4uivPROC glMultiTexCoordP4uiv = (glMultiTexCoordP4uivPROC)((intptr_t)function_pointer);
	glMultiTexCoordP4uiv(texture, type, coords_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglNormalP3ui(JNIEnv *env, jclass clazz, jint type, jint coords, jlong function_pointer) {
	glNormalP3uiPROC glNormalP3ui = (glNormalP3uiPROC)((intptr_t)function_pointer);
	glNormalP3ui(type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglNormalP3uiv(JNIEnv *env, jclass clazz, jint type, jobject coords, jint coords_position, jlong function_pointer) {
	const GLuint *coords_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, coords)) + coords_position;
	glNormalP3uivPROC glNormalP3uiv = (glNormalP3uivPROC)((intptr_t)function_pointer);
	glNormalP3uiv(type, coords_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglColorP3ui(JNIEnv *env, jclass clazz, jint type, jint color, jlong function_pointer) {
	glColorP3uiPROC glColorP3ui = (glColorP3uiPROC)((intptr_t)function_pointer);
	glColorP3ui(type, color);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglColorP4ui(JNIEnv *env, jclass clazz, jint type, jint color, jlong function_pointer) {
	glColorP4uiPROC glColorP4ui = (glColorP4uiPROC)((intptr_t)function_pointer);
	glColorP4ui(type, color);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglColorP3uiv(JNIEnv *env, jclass clazz, jint type, jobject color, jint color_position, jlong function_pointer) {
	const GLuint *color_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, color)) + color_position;
	glColorP3uivPROC glColorP3uiv = (glColorP3uivPROC)((intptr_t)function_pointer);
	glColorP3uiv(type, color_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglColorP4uiv(JNIEnv *env, jclass clazz, jint type, jobject color, jint color_position, jlong function_pointer) {
	const GLuint *color_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, color)) + color_position;
	glColorP4uivPROC glColorP4uiv = (glColorP4uivPROC)((intptr_t)function_pointer);
	glColorP4uiv(type, color_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglSecondaryColorP3ui(JNIEnv *env, jclass clazz, jint type, jint color, jlong function_pointer) {
	glSecondaryColorP3uiPROC glSecondaryColorP3ui = (glSecondaryColorP3uiPROC)((intptr_t)function_pointer);
	glSecondaryColorP3ui(type, color);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglSecondaryColorP3uiv(JNIEnv *env, jclass clazz, jint type, jobject color, jint color_position, jlong function_pointer) {
	const GLuint *color_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, color)) + color_position;
	glSecondaryColorP3uivPROC glSecondaryColorP3uiv = (glSecondaryColorP3uivPROC)((intptr_t)function_pointer);
	glSecondaryColorP3uiv(type, color_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexAttribP1ui(JNIEnv *env, jclass clazz, jint index, jint type, jboolean normalized, jint value, jlong function_pointer) {
	glVertexAttribP1uiPROC glVertexAttribP1ui = (glVertexAttribP1uiPROC)((intptr_t)function_pointer);
	glVertexAttribP1ui(index, type, normalized, value);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexAttribP2ui(JNIEnv *env, jclass clazz, jint index, jint type, jboolean normalized, jint value, jlong function_pointer) {
	glVertexAttribP2uiPROC glVertexAttribP2ui = (glVertexAttribP2uiPROC)((intptr_t)function_pointer);
	glVertexAttribP2ui(index, type, normalized, value);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexAttribP3ui(JNIEnv *env, jclass clazz, jint index, jint type, jboolean normalized, jint value, jlong function_pointer) {
	glVertexAttribP3uiPROC glVertexAttribP3ui = (glVertexAttribP3uiPROC)((intptr_t)function_pointer);
	glVertexAttribP3ui(index, type, normalized, value);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexAttribP4ui(JNIEnv *env, jclass clazz, jint index, jint type, jboolean normalized, jint value, jlong function_pointer) {
	glVertexAttribP4uiPROC glVertexAttribP4ui = (glVertexAttribP4uiPROC)((intptr_t)function_pointer);
	glVertexAttribP4ui(index, type, normalized, value);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexAttribP1uiv(JNIEnv *env, jclass clazz, jint index, jint type, jboolean normalized, jobject value, jint value_position, jlong function_pointer) {
	const GLuint *value_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glVertexAttribP1uivPROC glVertexAttribP1uiv = (glVertexAttribP1uivPROC)((intptr_t)function_pointer);
	glVertexAttribP1uiv(index, type, normalized, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexAttribP2uiv(JNIEnv *env, jclass clazz, jint index, jint type, jboolean normalized, jobject value, jint value_position, jlong function_pointer) {
	const GLuint *value_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glVertexAttribP2uivPROC glVertexAttribP2uiv = (glVertexAttribP2uivPROC)((intptr_t)function_pointer);
	glVertexAttribP2uiv(index, type, normalized, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexAttribP3uiv(JNIEnv *env, jclass clazz, jint index, jint type, jboolean normalized, jobject value, jint value_position, jlong function_pointer) {
	const GLuint *value_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glVertexAttribP3uivPROC glVertexAttribP3uiv = (glVertexAttribP3uivPROC)((intptr_t)function_pointer);
	glVertexAttribP3uiv(index, type, normalized, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_ARBVertexType2_10_10_10_REV_nglVertexAttribP4uiv(JNIEnv *env, jclass clazz, jint index, jint type, jboolean normalized, jobject value, jint value_position, jlong function_pointer) {
	const GLuint *value_address = ((const GLuint *)(*env)->GetDirectBufferAddress(env, value)) + value_position;
	glVertexAttribP4uivPROC glVertexAttribP4uiv = (glVertexAttribP4uivPROC)((intptr_t)function_pointer);
	glVertexAttribP4uiv(index, type, normalized, value_address);
}

