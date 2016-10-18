package com.sinocr.tj.dd.explore.maven.dependency;

import java.io.IOException;

import com.sinocr.tj.dd.explore.maven.tools.FileTools;

public class FileModify {

	public static final String before1 = "yfiles.type=\"nodegraphics\"";
	public static final String after1 = "yfiles.type=\"nodegraphics\" attr.name=\"d0\" attr.type=\"string\"";
	public static final String before2 = "<y:ShapeNode><y:NodeLabel>";
	public static final String after2 = "";
	public static final String before3 = "</y:NodeLabel></y:ShapeNode>";
	public static final String after3 = "";

	public static void modify(String filePath) throws IOException {
		String content = FileTools.read(filePath);
		if (!content.contains(after1)) {
			content = content.replaceAll(before1, after1);
			content = content.replaceAll(before2, after2);
			content = content.replaceAll(before3, after3);
		}

		FileTools.write(filePath, content);
	}

}
