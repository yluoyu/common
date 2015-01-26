package com.vincent.common;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vincent.common.convert.Convertor;
import com.vincent.common.convert.Convertor.ConvertorFactory;
import com.vincent.common.convert.DefaultConvertor;

public class Util {
	
	private static final Log log = LogFactory.getLog(Util.class);
	
	private static boolean HOME_INITIALIZED = false;
	private static File HOME = null;
	private static SystemProperty systemProperty = null;
	
	private static final String ALPHA_CAPS 	= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALPHA 	= "abcdefghijklmnopqrstuvwxyz";
	private static final String NUM 	= "0123456789";
	private static final String SPL_CHARS	= "!@#$%^&*_=+-/";
	private static final String PWD_CHARS = ALPHA_CAPS+ALPHA+NUM+SPL_CHARS;
	private static final String ALPHA_NUM_CHARS  =ALPHA_CAPS+ALPHA+NUM;
	
	public static final String randomAlphaNumCode(int length){
		StringBuilder builder = new StringBuilder();
		Random rnd = new Random();
		for(int i=0;i<length;i++){
			builder.append(ALPHA_NUM_CHARS.charAt(rnd.nextInt(ALPHA_NUM_CHARS.length())));
		}
		
		return builder.toString();
	}
	
	public static final String randomNumCode(int length){
		StringBuilder builder = new StringBuilder();
		Random rnd = new Random();
		for(int i=0;i<length;i++){
			builder.append(NUM.charAt(rnd.nextInt(NUM.length())));
		}
		
		return builder.toString();
	}
	
	public static String randomPassword(int length){
		StringBuilder builder = new StringBuilder();
		Random rnd = new Random();
		for(int i=0;i<length;i++){
			builder.append(PWD_CHARS.charAt(rnd.nextInt(PWD_CHARS.length())));
		}
		
		return builder.toString();
	}

	public static void copy(InputStream in, OutputStream out) throws IOException{
		IOUtils.copy(in, out);
	}
	
	public static void close(Closeable closeable) {
		if(closeable != null){
			try {
				closeable.close();
			} catch (IOException e) {
				log.warn(e.getMessage(), e);
			}
		}
	}
	
	public static void close(Statement stmt) {
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				log.warn(e.getMessage(), e);
			}
		}
	}
	
	public static void close(Connection conn) {
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				log.warn(e.getMessage(), e);
			}
		}
	}
	
	public static void close(ResultSet rs) {
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				log.warn(e.getMessage(), e);
			}
		}
	}
	
	public static synchronized File getHome(String projectName){
		if(!HOME_INITIALIZED){
			HOME_INITIALIZED = true;
			String home = System.getProperty(projectName.toUpperCase() + "_HOME");
			if(home == null){
				home = System.getenv(projectName.toUpperCase() + "_HOME");
			}
			if(StringUtil.isNullOrEmpty(home) == false){
				File file = new File(home);
				if(file.exists() == false){
					log.warn("HOME does not exist");
				}else if(file.isDirectory() == false){
					log.warn("HOME is not a directory");
				}else{
					HOME = file;
				}
			}
		}
		return HOME;
	}
	
	public static String getSystemProperty(String projectName, Class<?> clz, String propFileName, String propName){
		try {
			if(systemProperty == null){
				File file = new File(getHome(projectName) + "conf/"+projectName.toLowerCase()+".conf");
				systemProperty = new SystemProperty(file);
			}
			String prop = systemProperty.getProperty(propName);
			if(prop != null) return prop;
			
			InputStream in = clz.getResourceAsStream("/"+propFileName);
			if(in!=null){
				Properties props = loadProperties(in, true);
				prop = props.getProperty(propName);
				if(prop != null) return prop;
			}
			
			in = clz.getResourceAsStream(propFileName);
			if(in!=null){
				Properties props = loadProperties(in, true);
				prop = props.getProperty(propName);
				if(prop != null) return prop;
			}
			
			File file = new File(propFileName);
			if(file.exists()) {
				in = new FileInputStream(file);
				if(in!=null){
					Properties props = loadProperties(in, true);
					prop = props.getProperty(propName);
					if(prop != null) return prop;
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static Properties loadProperties(InputStream in, boolean closeOnReturn) throws IOException{
		try{
			Properties props = new Properties();
			props.load(in);
			return props;
		} finally{
			if(closeOnReturn){
				close(in);
			}
		}
	}
	
//	public static Properties loadProperties(Class<?> clz, String propFileName){
//		Properties props = new Properties();
//		try {
//			InputStream in = clz.getResourceAsStream(propFileName);
//			if(in==null){
//				in = clz.getResourceAsStream("/"+propFileName);
//			}
//			
//			if(in != null){
//				props.load(in);
//			}
//		} catch (IOException e) {
//			log.error(e.getMessage(), e);
//		}
//		return props;
//	}

	public static String urlEncode(Object value, String enc) {
		if(value == null) return "";
			
		try {
			return URLEncoder.encode(""+value, enc);
		} catch (UnsupportedEncodingException e) {
			return value.toString();
		}
	}
	
	public static String urlDecode(Object value, String enc) {
		if(value == null) return "";
		
		try {
			return URLDecoder.decode(""+value, enc);
		} catch (UnsupportedEncodingException e) {
			return value.toString();
		}
	}
	
	public static <T> List<T> copyBeanList(List<T> beanLst) throws Exception{
		List<T> newList = new ArrayList<T>();
		for(T bean : beanLst){
			newList.add(copyBean(bean));
		}
		
		return newList;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T copyBean(T bean) throws Exception {
		T newBean = (T)bean.getClass().newInstance();
		for(PropertyDescriptor pd : Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors()){
			try{
				Method rm = pd.getReadMethod();
				Method wm = pd.getWriteMethod();

				if(rm!=null & wm!=null){
					Object value = rm.invoke(bean);
					wm.invoke(newBean, value);
				}
			} catch(Exception e){
			}
		}
		
		return newBean;
	}
	
	public static String getFilePath(File file){
		if(file == null) return null;
		try {
			return file.getCanonicalPath();
		} catch (Exception e) {
			return file.getAbsolutePath();
		}
	}
	
	public static void zoomImage(InputStream in, int width, int height, OutputStream output, String outFormat) throws IOException {
		Graphics2D g = null;
		try{
			BufferedImage sourceImage = ImageIO.read(in);
			if(sourceImage != null){
				int srcWidth = sourceImage.getWidth();
				int srcHeight = sourceImage.getHeight();
				double widthZoom = (double)width/srcWidth;
				double heightZoom = (double)height/srcHeight;
				if( widthZoom < heightZoom){
					height = new Double(srcHeight * widthZoom).intValue();
				}else{
					width = new Double(srcWidth * heightZoom).intValue();
				}
				
				BufferedImage resizedImage = new BufferedImage(width , height, BufferedImage.TYPE_INT_RGB);
				g = resizedImage.createGraphics();
				g.drawImage(sourceImage, 0, 0, width , height , null);
				ImageIO.write(resizedImage, outFormat, output);
			}
		}finally {
			if(g != null){
				g.dispose();
			}
		}
	}
	
	public static boolean isValidProperty(String prop) {
		if (StringUtil.isNullOrEmpty(prop, false))
			return false;

		for (int i = 0, n = prop.length(); i < n; i++) {
			char c = prop.charAt(i);
			if (c != '.' && Character.isJavaIdentifierPart(c) == false) {
				return false;
			}
		}

		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String propstr(String string, Object beanOrMap){
		if (StringUtil.isNullOrEmpty(string, false) || beanOrMap == null)
			return "";

		StringBuilder builder = new StringBuilder(string);
		Pattern pattern = Pattern.compile("\\{(.+?)\\}");
		Matcher matcher = pattern.matcher(string);

		Stack<Replacer> stack = new Stack<Replacer>();
		int start = 0;
		while (matcher.find(start)) {
			for (int i = 0, n = matcher.groupCount(); i < n;) {
				String prop = matcher.group(++i);
				if (!isValidProperty(prop)) {
					start = matcher.start() + 1;
					continue;
				}

				Object replacement;
				PropertyValue pv = getPropertyValue(beanOrMap, prop);
				if (pv == null || pv.getValue()==null) {
					replacement = "";
				}else{
					replacement = pv.getValue();
				}
				
				Class<? extends Convertor> convertorClz = null;
				if(pv!=null && pv.getClz()!=null && pv.getFiled()!=null
						&& !Map.class.isAssignableFrom(pv.getClz())){
					try{
						Field field = pv.getClz().getDeclaredField(pv.getFiled());
						if(field.isAnnotationPresent(StringAdaptor.class)){
							convertorClz = field.getAnnotation(StringAdaptor.class).value();
						}
					} catch(Exception e){
					}
				}
				
				if(convertorClz == null){
					convertorClz = ConvertorFactory.getConvertor(replacement.getClass());
				}
				try{
					replacement = convertorClz.newInstance().convert(replacement);
				} catch(Exception e){
					replacement = new DefaultConvertor().convert(replacement);
				}
				
				stack.push(new Replacer(matcher.start(), matcher.end(),
						replacement.toString()));
				start = matcher.end();
			}
		}

		while (!stack.empty()) {
			Replacer replacer = stack.pop();
			builder.replace(replacer.getStart(), replacer.getEnd(),
					replacer.getReplacement());
		}

		return builder.toString();
	
	}
	
	public static Object getProperty(Object beanOrMap, String prop){
		PropertyValue value = getPropertyValue(beanOrMap, prop);
		if(value == null){
			return null;
		}else{
			return value.getValue();
		}
	}
	
	public static PropertyValue getPropertyValue(Object beanOrMap, String prop) {
		if (prop == null || beanOrMap == null)
			return null;
		try {
			int index = prop.indexOf(".");
			if (index == -1) {
				if(beanOrMap instanceof Map){
					Object value = ((Map<?, ?>)beanOrMap).get(prop);
					return new PropertyValue(beanOrMap.getClass(), null, value);
				}else{
					Class<?> clz =  beanOrMap.getClass();
					Method method = clz.getMethod("get" + StringUtil.capitalize(prop));
					return new PropertyValue(clz, prop, method.invoke(beanOrMap));
				}
			} else {
				if(beanOrMap instanceof Map){
					return getPropertyValue(
							((Map<?, ?>)beanOrMap).get(prop.substring(0, index)), prop.substring(index + 1));
				}else{
					return getPropertyValue(
							beanOrMap.getClass()
									.getMethod(
											"get"+ StringUtil.capitalize(prop.substring(0, index)))
									.invoke(beanOrMap), prop.substring(index + 1));
				}
			}
		} catch (Exception e) {
			return null;
		}
	}

	static class Replacer {
		private int start;
		private int end;
		private String replacement;

		Replacer(int start, int end, String replacement) {
			this.start = start;
			this.end = end;
			this.replacement = replacement;
		}

		public int getStart() {
			return start;
		}

		public int getEnd() {
			return end;
		}

		public String getReplacement() {
			return replacement;
		}

	}
	
	static class PropertyValue {
		
		private Class<?> clz;
		private String filed;
		private Object value;

		PropertyValue(Class<?> clz, String filed, Object value){
			this.clz = clz;
			this.filed = filed;
			this.value = value;
		}

		public Class<?> getClz() {
			return clz;
		}

		public String getFiled() {
			return filed;
		}

		public Object getValue() {
			return value;
		}
		
	}
	
    public static String getMD5Str(String str) {   
        MessageDigest messageDigest = null;   
   
        try {   
            messageDigest = MessageDigest.getInstance("MD5");   
   
            messageDigest.reset();   
   
            messageDigest.update(str.getBytes("UTF-8"));   
        } catch (NoSuchAlgorithmException e) {   
            System.out.println("NoSuchAlgorithmException caught!");   
            System.exit(-1);   
        } catch (UnsupportedEncodingException e) {   
            e.printStackTrace();   
        }   
   
        byte[] byteArray = messageDigest.digest();   
   
        StringBuffer md5StrBuff = new StringBuffer();   
   
        for (int i = 0; i < byteArray.length; i++) {               
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)   
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));   
            else   
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));   
        }   
   
        return md5StrBuff.toString();   
    } 
	
}
