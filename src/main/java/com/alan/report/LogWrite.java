package com.alan.report;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWrite {
	private static boolean fileLog = true;
	private static String logFileName = "_log.txt";// 指定程序执行结果保存的文件路径

	public static OutputStream getOutputStream() throws IOException {
		if (fileLog) {
			File file = new File(logFileName);
			if (!file.exists())
				file.createNewFile();
			return new FileOutputStream(file, true);
		} else {
			return System.out;
		}
	}

	public static void log(String info) throws IOException {
		OutputStream out = getOutputStream();
		out.write(info.getBytes("utf-8"));
	}
	
	public static void saveToFile(String text) {
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy_MM_dd_HH" );
		Date d = new Date();
		String s = sdf.format(d);
		
		File file = new File(s + logFileName);
		BufferedWriter bf = null;
		try {
			FileOutputStream outputStream = new FileOutputStream(file, true);
			OutputStreamWriter outWriter = new OutputStreamWriter(outputStream);
			bf = new BufferedWriter(outWriter);
			bf.append(d.toString() + "\t" + text);
			bf.newLine();
			bf.flush();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
