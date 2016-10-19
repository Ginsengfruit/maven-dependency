package com.sinocr.tj.dd.explore.maven;

import java.io.IOException;

import com.sinocr.tj.dd.explore.maven.dependency.GraphmlAnalyse;

/**
 * Entry
 *
 */
public class App {
	public static void main(String[] args) {

		try {

			GraphmlAnalyse analyse = new GraphmlAnalyse();
			analyse.analyseFolder("D:/Work/graphml/");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
