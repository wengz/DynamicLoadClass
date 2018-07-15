package pers.wengzc;

import java.awt.image.ByteLookupTable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
	
	public static void main(String[] args) throws Exception{
		ZipOutputStream zipOutputStream =  new ZipOutputStream(new FileOutputStream("patch.jar"));
		FileInputStream fileInputStream = new FileInputStream("Hello.class");
		int res = -1;
		byte[] buff = new byte[1024];
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		while ( (res = fileInputStream.read(buff)) != -1 ) {
			byteArrayOutputStream.write(buff, 0, res);
		}
		fileInputStream.close();
		byte[] bytes = byteArrayOutputStream.toByteArray();
		ZipEntry zipEntry = new ZipEntry("Hello.class");
		zipOutputStream.putNextEntry(zipEntry);
		zipOutputStream.write(bytes, 0, bytes.length);
		zipOutputStream.closeEntry();
		zipOutputStream.flush();
		zipOutputStream.close();
	}
}
