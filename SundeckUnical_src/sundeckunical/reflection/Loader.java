package sundeckunical.reflection;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Loader {

	static File myFile=null;
	static URL myJarFile=null;
	static URLClassLoader cl=null;
	static URL[] urls=null;
	static Class<?> myClass;
	static Method printMeMethod=null;
	static Object MyClassObj=null;
	static Constructor MyClassConstruct=null;
	
	public Loader(String PATH) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		myFile = new File(PATH);
		//myJarFile = new URL("jar","","file:"+myFile.getPath()+"!/");
		myJarFile = new URL("jar","","file:"+myFile.getAbsolutePath()+"!/");
		//cl=Thread.currentThread().getContextClassLoader();
		cl = URLClassLoader.newInstance(new URL[] {myJarFile});
		//cl = new URLClassLoader(urls);
		myClass = cl.loadClass("C:/Users/Rocco/workspaceMars/Sundeck/sundeckunical/reflection2/ReflectionPlayer.java");
		
		//myClass = cl.loadClass("ReflectionPlayer.class");
		//MyClassConstruct = myClass.getConstructor(new Class[] {});
		//Object MyClassObj= MyClassConstruct.newInstance();
	}
	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String path = "C:/Users/Rocco/workspaceMars/Sundeck/sundeckunical/reflection2/ReflectionPlayer.java";
		Loader l = new Loader(path);
		System.out.println("" + l.myFile.toString());
		System.out.println("" + l.myJarFile.toString());
		System.out.println(""+l.myClass.toString());
	}
	//URLClassLoader cl = URLClassLoader.newInstance(new URL[] {myJarFile});
	
//	Class MyClass = cl.loadClass("com.mycomp.proj.myclass");
//	Method printMeMethod = MyClass.getMethod(“printMe”, new Class[] {String.class, String.class});
//	Object MyClassObj = MyClass.newInstance();
//	Object response = printMeMethod.invoke(MyClassObj,”String1″, “String2″);
	
	
}
