package opencl;


import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opencl.CL;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLCommandQueue;
import org.lwjgl.opencl.CLContext;
import org.lwjgl.opencl.CLDevice;
import org.lwjgl.opencl.CLKernel;
import org.lwjgl.opencl.CLMem;
import org.lwjgl.opencl.CLPlatform;
import org.lwjgl.opencl.CLProgram;
import org.lwjgl.opencl.Util;

/**
 * @author Jason Yang
 *
 */

public class Boot {
	// OpenCL variables
	public CLContext context;
	public CLPlatform platform;
	public List<CLDevice> devices;
	public CLDevice device;
	public CLCommandQueue queue;
	
	int[] array;
	
	String check = "NgLn TQvscdUp@k\\e^n(Jb";
	
	public static String ANSI_RESET = "";
	public static String ANSI_BLACK = "";
	public static String ANSI_RED = "";
	public static String ANSI_GREEN = "";
	public static String ANSI_YELLOW = "";
	public static String ANSI_BLUE = "";
	public static String ANSI_PURPLE = "";
	public static String ANSI_CYAN = "";
	public static String ANSI_WHITE = "";
	
	static long t;

	public static void main(String[] args){
		try {
			if (args.length > 0){
				if (args[0].toLowerCase().equals("ansi")){
					ANSI_RESET = "\u001B[0m";
					ANSI_BLACK = "\u001B[30;1m";
					ANSI_RED = "\u001B[31;1m";
					ANSI_GREEN = "\u001B[32;1m";
					ANSI_YELLOW = "\u001B[33;1m";
					ANSI_BLUE = "\u001B[34;1m";
					ANSI_PURPLE = "\u001B[35;1m";
					ANSI_CYAN = "\u001B[36;1m";
					ANSI_WHITE = "\u001B[37;1m";
				}
			}
			String answer = "NgLn TQvscdUp@k\\e^n(Jb";
			char[] input = new char[answer.length()];
			for(int i=0; i<input.length; i++)
				input[i] = 32;
			int index=0;
			while(index < input.length) {
				
				String in = "";
				for(int l=0;l<input.length;l++)
					in += input[l];
				System.out.print(in + " : ");
				String result = new Boot().run(in);
				System.out.println(result);
				if(result.charAt(index) == answer.charAt(index)) {
					index++;
					continue;
				}
				
				input[index]++;
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	public String run(String aString) throws LWJGLException {
		initializeCL();
		
		loadPass();
		
		String source = loadText("/opencl/kernel.cl");
		
		//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Building kernel...");
		CLProgram program = CL10.clCreateProgramWithSource(context, source, null);
		int error = CL10.clBuildProgram(program, devices.get(0), "", null);
		if (error == 0)
			System.out.print(ANSI_RESET);
		else
			System.out.print(ANSI_RED);
		//System.out.println(program.getBuildInfoString(device, CL10.CL_PROGRAM_BUILD_LOG) + ANSI_RESET);
		
		Util.checkCLError(error);
		CLKernel kernel = CL10.clCreateKernel(program, "ctf", null);
		//System.out.println(ANSI_GREEN + "done.");

		final int size = check.length();
		IntBuffer errorBuff = BufferUtils.createIntBuffer(1);
		
//		//System.out.println(ANSI_CYAN + "[MESSAGE] " + ANSI_RESET + "Input the password:" + ANSI_RED);
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String aString = "";
//		try {
//			aString = br.readLine();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		//System.out.print(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Creating arrays...");
		float[] aData = new float[size];
		for (int i = 0; i < size; i++){
			char c;
			if (i >= aString.length()){
				c = ' ';
			} else {
				c = aString.charAt(i);
			}
			
			int n = (int) c;
			aData[i] = n;
		}
		
		float[] bData = new float[size];
		for (int i = 0; i < size; i++){
			bData[i] = array[i];
		}
		//System.out.println(ANSI_GREEN + "done.");
		
		//System.out.print(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Creating buffers in memory...");
		FloatBuffer aBuff = BufferUtils.createFloatBuffer(size);
		aBuff.put(aData);
		aBuff.rewind();
		
		CLMem aMemory = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, aBuff, errorBuff);
		Util.checkCLError(errorBuff.get(0));
		
		FloatBuffer bBuff = BufferUtils.createFloatBuffer(bData.length);
		bBuff.put(bData);
		bBuff.rewind();
		
		CLMem bMemory = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, bBuff, errorBuff);
		Util.checkCLError(errorBuff.get(0));

		CLMem resultMemory = CL10.clCreateBuffer(context, CL10.CL_MEM_READ_ONLY, size*4, errorBuff);
		//System.out.println(ANSI_GREEN + "done.");
		
		//System.out.print(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Checking for errors...");
		Util.checkCLError(errorBuff.get(0));
		//System.out.println(ANSI_GREEN + "done.");

		//System.out.print(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Setting kernel parameters...");
		kernel.setArg(0, aMemory);
		kernel.setArg(1, bMemory);
		kernel.setArg(2, resultMemory);
		kernel.setArg(3, size);
		//System.out.println(ANSI_GREEN + "done.");
		
		//System.out.print(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Creating work size buffer...");
		final int dimensions = 1; 
		PointerBuffer globalWorkSize = BufferUtils.createPointerBuffer(dimensions);
		globalWorkSize.put(0, size);
		//System.out.println(ANSI_GREEN + "done.");
		
		startTime();
		
		//System.out.print(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Running kernels...");
		CL10.clEnqueueNDRangeKernel(queue, kernel, dimensions, null, globalWorkSize, null, null, null);
		CL10.clFinish(queue);
		//System.out.println(ANSI_GREEN + "done.");
		
		stopTime();

		//System.out.print(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Reading result buffer...");
		FloatBuffer resultBuff = BufferUtils.createFloatBuffer(size);
		CL10.clEnqueueReadBuffer(queue, resultMemory, CL10.CL_TRUE, 0, resultBuff, null, null);
		//System.out.println(ANSI_GREEN + "done.");
		
		String s = "";
		for(int i = 0; i < resultBuff.capacity(); i++) {
			s += (char) resultBuff.get(i);
		}
		//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Time taken: " + t / 1000000000f + " s");
		//if (s.equals(check))
		//	System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_GREEN + "Success! You found the flag (it's the password).");
		//else
		//	System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RED + "Hah nice try!");
		
		// Cleanup!
		CL10.clReleaseKernel(kernel);
		CL10.clReleaseProgram(program);
		
		// More cleanup!
		CL10.clReleaseMemObject(aMemory);
		CL10.clReleaseMemObject(bMemory);
		CL10.clReleaseMemObject(resultMemory);
		destroyCL();
		return s;
	}

	public void startTime(){
		t = System.nanoTime();
	}
	
	public void stopTime(){
		t = System.nanoTime() - t;
	}

	public void initializeCL() throws LWJGLException { 
		IntBuffer errorBuf = BufferUtils.createIntBuffer(1);
		//System.out.print(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Creating OpenCL instance...");
		CL.create();
		//System.out.println(ANSI_GREEN + "done.");
		
		List<CLPlatform> platforms = CLPlatform.getPlatforms();
		for (int i = 0; i < platforms.size(); i++){
			CLPlatform p = platforms.get(i);
			//System.out.println(ANSI_BLACK + "\n================================\n");
			//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Platform " + i);
			//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + p.getInfoString(CL10.CL_PLATFORM_NAME));
			//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + p.getInfoString(CL10.CL_PLATFORM_VENDOR));
			//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + p.getInfoString(CL10.CL_PLATFORM_VERSION));
			//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + p.getInfoString(CL10.CL_PLATFORM_PROFILE));
			//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + p.getInfoString(CL10.CL_PLATFORM_EXTENSIONS));
			//System.out.println("\n");
			
			List<CLDevice> devices = p.getDevices(CL10.CL_DEVICE_TYPE_GPU);
			for (int j = 0; j < devices.size(); j++){
				CLDevice d = devices.get(j);
				//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Device " + j);
				//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + d.getInfoString(CL10.CL_DEVICE_NAME));
				//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + d.getInfoString(CL10.CL_DEVICE_VERSION));
				//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + d.getInfoString(CL10.CL_DEVICE_VENDOR));
				//System.out.println("\n");
			}
		}
		
		int plat = 0;
//		try {
//			//System.out.println(ANSI_CYAN + "[MESSAGE] " + ANSI_RESET + "Input platform id:" + ANSI_CYAN);
//			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			plat = Integer.parseInt(br.readLine());
//		} catch (NumberFormatException e){
//			//System.out.println(ANSI_RED + "[ERROR] " + ANSI_RESET + "Not a number...exiting");
//			System.exit(0);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		platform = CLPlatform.getPlatforms().get(plat);
		//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + platform.getInfoString(CL10.CL_PLATFORM_NAME));
		
		int dev = 0;
//		try {
//			//System.out.println(ANSI_CYAN + "[MESSAGE] " + ANSI_RESET + "Input device id:" + ANSI_CYAN);
//			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			dev = Integer.parseInt(br.readLine());
//		} catch (NumberFormatException e){
//			//System.out.println(ANSI_RED + "[ERROR] " + ANSI_RESET + "Not a number...exiting");
//			System.exit(0);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		devices = platform.getDevices(CL10.CL_DEVICE_TYPE_GPU);
		device = devices.get(dev);
		//System.out.println(ANSI_YELLOW + "[INFO] " + ANSI_RESET + device.getInfoString(CL10.CL_DEVICE_NAME));

		//System.out.print(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Creating OpenCL context...");
		context = CLContext.create(platform, devices, errorBuf);
		//System.out.println(ANSI_GREEN + "done.");

		//System.out.print(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Creating command queue...");
		queue = CL10.clCreateCommandQueue(context, device, CL10.CL_QUEUE_PROFILING_ENABLE, errorBuf);
		Util.checkCLError(errorBuf.get(0)); 
		//System.out.println(ANSI_GREEN + "done.");
	}


	public void destroyCL() {
		//System.out.print(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Releasing resources...");
		// Finish destroying anything we created
		CL10.clReleaseCommandQueue(queue);
		CL10.clReleaseContext(context);
		// And release OpenCL, after this method call we cannot use OpenCL unless we re-initialize it
		CL.destroy();
		//System.out.println(ANSI_GREEN + "done." + ANSI_RESET);
	}


	public String loadText(String name) {
		//System.out.print(ANSI_YELLOW + "[INFO] " + ANSI_RESET + "Loading " + name + "...");
		InputStream is = null;
		BufferedReader br = null;
		String source = null;
		try {
			// Get the file containing the OpenCL kernel source code
			is = Boot.class.getResourceAsStream(name);
			// Create a buffered file reader for the source file
			br = new BufferedReader(new InputStreamReader(is));
			// Read the file's source code line by line and store it in a string builder
			String line = null;
			StringBuilder result = new StringBuilder();
			while((line = br.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}
			// Convert the string builder into a string containing the source code to return
			source = result.toString();
		} catch(NullPointerException npe) {
			npe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				br.close();
				is.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		//System.out.println(ANSI_GREEN + "done.");
		return source;
	}
	
	public void loadPass(){
		String s = loadText("/opencl/pass.key");
		array = new int[s.length()];
		for (int i = 0; i < s.length(); i++){
			try {
				array[i] = Integer.parseInt(Character.toString(s.charAt(i)));
			} catch (NumberFormatException e){}
		}
	}
	
}
