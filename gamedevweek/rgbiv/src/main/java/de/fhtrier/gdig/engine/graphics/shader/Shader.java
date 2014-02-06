package de.fhtrier.gdig.engine.graphics.shader;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Stack;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.Util;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdig.demos.jumpnrun.identifiers.Constants;

public class Shader
{
    /**
     * The internal OpenGL Id of this Shader (Used for ARB-Commands)
     */
    private int shaderId = -1;
    private int vertexShaderId = -1;
    private int fragmentShaderId = -1;
    private static Shader activeShader = null;
    private static Stack<Shader> shaderStack = new Stack<Shader>();
    
    /**
     * Creates a new Shader, combining the code of the provided files
     * for vertex and fragment shader. Compiling Information is printed
     * to Log.debug.
     * 
     * @param vertShaderFilename Filename of the VertexShader File
     * @param fragShaderFilename Filename of the FragmentShader File
     */
    public Shader(String vertShaderFilename, String fragShaderFilename)
    {
        // Create Vertex Shader
        vertexShaderId = ARBShaderObjects.glCreateShaderObjectARB(ARBVertexShader.GL_VERTEX_SHADER_ARB);
        ARBShaderObjects.glShaderSourceARB(vertexShaderId, createProgramCode(vertShaderFilename));
        ARBShaderObjects.glCompileShaderARB(vertexShaderId);
        printLogInfo(vertexShaderId, "Vertex Shader "+vertShaderFilename);
        
        // Create Fragment Shader
        fragmentShaderId = ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        ARBShaderObjects.glShaderSourceARB(fragmentShaderId, createProgramCode(fragShaderFilename));
        ARBShaderObjects.glCompileShaderARB(fragmentShaderId);
        printLogInfo(fragmentShaderId, "Fragment Shader "+fragShaderFilename);
        
        // Combine Shader
        shaderId = ARBShaderObjects.glCreateProgramObjectARB();
        ARBShaderObjects.glAttachObjectARB(shaderId, vertexShaderId);
        ARBShaderObjects.glAttachObjectARB(shaderId, fragmentShaderId);
        
        // Link and Compile
        ARBShaderObjects.glLinkProgramARB(shaderId);
        ARBShaderObjects.glValidateProgramARB(shaderId);
        
    }
    
    
    /**
     * Sets the Value of a Uniform-Float Value with the given Name.
     * @param name Name of the uniform float variable
     * @param x New Value.
     */
    public void setValue(String name, float x)
    {
        int variableId = ARBShaderObjects.glGetUniformLocationARB(shaderId, toByteString(name));
        ARBShaderObjects.glUniform1fARB(variableId, x);
    }
    
    public void setValue(String name, float x, float y)
    {
        int variableId = ARBShaderObjects.glGetUniformLocationARB(shaderId, toByteString(name));
        ARBShaderObjects.glUniform2fARB(variableId, x, y);
    }
    
    public void setValue(String name, float x, float y, float z)
    {
        int variableId = ARBShaderObjects.glGetUniformLocationARB(shaderId, toByteString(name));
        ARBShaderObjects.glUniform3fARB(variableId, x, y, z);
    }
    
    public void setValue(String name, float x, float y, float z, float w)
    {
        int variableId = ARBShaderObjects.glGetUniformLocationARB(shaderId, toByteString(name));
        ARBShaderObjects.glUniform4fARB(variableId, x, y, z, w);
    }
    
    public void setValue(String name, Color col) {
    	int variableId = ARBShaderObjects.glGetUniformLocationARB(shaderId, toByteString(name));
        ARBShaderObjects.glUniform4fARB(variableId, col.r, col.g, col.b, col.a);
	}
    
    /**
     * Sets the Value for an uniform float-Array with the given name. An array of vectors
     * can be set by providing the right width value. I.e. if the Shader contains an array
     * of type vec2 - the call should look like this: setValue(coords, 2, array)
     * Each consecutive 2 values in the array will be combined to one vec2 element.
     * 
     * @param name Name of the Uniform Array (float, vec2, vec3 or vec4)
     * @param width Number of floats in one Array-Element
     * @param values A float array with all values
     */
    public void setValue(String name, int width, float[] values)
    {
    	FloatBuffer vbuffer = BufferUtils.createFloatBuffer(values.length);
    	vbuffer.put(values);
    	vbuffer.flip();
        int variableId = ARBShaderObjects.glGetUniformLocationARB(shaderId, toByteString(name));
        switch (width) {
		case 1:
			ARBShaderObjects.glUniform1ARB(variableId, vbuffer);
			break;
		case 2:
			ARBShaderObjects.glUniform2ARB(variableId, vbuffer);
			break;
		case 3:
			ARBShaderObjects.glUniform3ARB(variableId, vbuffer);
			break;
		case 4:
			ARBShaderObjects.glUniform4ARB(variableId, vbuffer);
			break;

		default:
			throw new NumberFormatException("Shader.setValue() - width must be in the interval [1..4]");
		}
    }
    
    /**
     * Activates the provided Shader for all following rendering operations.
     * setActiveShader(null) should be called after each use of a Shader
     * to avoid conflicts with render-methods without Shaders.
     * 
     * @param shader The Shader to be used, or null to enable default rendering.
     */
    public static void setActiveShader(Shader shader)
    {
    	if (shader == activeShader) return;
    		
        if (shader == null) ARBShaderObjects.glUseProgramObjectARB(0);
        else ARBShaderObjects.glUseProgramObjectARB(shader.shaderId);
        
        activeShader = shader;
    }
    
    /**
     * Retrieves the currently active Shader, but only if it was set using this
     * class or one of it's subclasses.
     * 
     * @return The last argument which was set with setActiveShader(Shader s)
     */
    public static Shader getActiveShader()
    {
    	return activeShader;
    }
    
    /**
     * Activates Additive Blending for all following rendering operations.
     */
    public static void activateAdditiveBlending()
    {
    	Renderer.get().glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE);
    }
    
    /**
     * Resets Blending to the default Mode.
     */
    public static void activateDefaultBlending()
    {
    	Renderer.get().glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
    }
    
    /**
     * Reads a file into a ByteBuffer, which can be compiled on the GPU.
     * @param filename Name of the Source-File
     * @return A ByteBuffer containing the contents of the file.
     */
    private static ByteBuffer createProgramCode(String filename)
    {
        InputStream fileInputStream = null;
        byte[] shaderCode = null;
        
        try
        {
            if (fileInputStream == null)
            {
                fileInputStream = new FileInputStream(filename);
            }
            DataInputStream dataStream = new DataInputStream(fileInputStream);
            dataStream.readFully(shaderCode = new byte[fileInputStream.available()]);
            fileInputStream.close();
            dataStream.close();
        }
        catch (Exception e)
        {
            Log.debug(e.getMessage());
        }
        
        ByteBuffer shaderPro = BufferUtils.createByteBuffer(shaderCode.length);
        
        shaderPro.put(shaderCode);
        shaderPro.flip();
        
        return shaderPro;
    }
    
    /**
     * Converts a String to a ByteBuffer
     * @param str The String to convert
     * @param isNullTerminated a flag to specify if the string contains a character
     * 			with escape code 0 as final character.
     * @return A ByteBuffer ready to read from.
     */
    private static ByteBuffer toByteString(String str, boolean isNullTerminated)
    {
        int length = str.length();
        if (isNullTerminated)
        {
            length++;
        }
        ByteBuffer buff = BufferUtils.createByteBuffer(length);
        buff.put(str.getBytes());
        
        if (isNullTerminated)
        {
            buff.put((byte) 0);
        }
        
        buff.flip();
        return buff;
    }
    
    /**
     * Converts a String to a ByteBuffer.
     * @param str The String to convert
     * @return A ByteBuffer ready to read from.
     */
    public static ByteBuffer toByteString(String str)
    {
        return Shader.toByteString(str, true);
    }
    
    /**
     * Retrieves the Log-Info about a certain OpenGL-Object from the GPU and
     * prints it to Log.debug.
     * @param glObjectId The interal OpenGL-ID  of the Object.
     * @param string Additional Info for tracing the error
     */
    public static void printLogInfo(int glObjectId, String string)
    {
        IntBuffer iVal = BufferUtils.createIntBuffer(1);
        ARBShaderObjects.glGetObjectParameterARB(glObjectId, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB, iVal);
        
        int length = iVal.get();
        if (length > 1)
        {
            // We have some info we need to output.
            ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
            iVal.flip();
            ARBShaderObjects.glGetInfoLogARB(glObjectId, iVal, infoLog);
            byte[] infoBytes = new byte[length-1];
            infoLog.get(infoBytes);
            String out = new String(infoBytes);
            if (!out.toLowerCase().contains("no errors") && !out.toLowerCase().contains("successfully"))
            {
            	Log.debug(string+"("+glObjectId+") -- OpenGL ERROR:\r\n" + out);
            	
            	if (Constants.Debug.shadersAutoDisable)
            	{
            		Log.debug("OpenGL compiling encountered an ERROR and shadersAutoDisable is active.\n" +
            				">>>>>>>>>> ALL SHADERS ARE DISABLED <<<<<<<<<");
            		Constants.Debug.shadersActive = false;
            		setActiveShader(null);
            	}
            }
        }
        
        try
        {
        	Util.checkGLError();
        }
        catch (OpenGLException e)
        {
        	if (Constants.Debug.shadersAutoDisable)
        	{
        		Log.debug(string+"("+glObjectId+") -- OpenGL ERROR:\r\n");
        		e.printStackTrace();
        		Log.debug("OpenGL CRITCAL ERROR and shadersAutoDisable is active.\n" +
        				">>>>>>>>>> ALL SHADERS ARE DISABLED <<<<<<<<<");
        		Constants.Debug.shadersActive = false;
        		setActiveShader(null);
        	}
        }
        
    }
    
    /**
     * 
     * @param shader
     */
	public static void pushShader(Shader shader)
	{
		shaderStack.push(activeShader);
		setActiveShader(shader);
	}

	public static void popShader()
	{
		if (shaderStack.isEmpty())
		{
			setActiveShader(null);
		}
		else
		{
			setActiveShader(shaderStack.pop());
		}
		
	}
}
