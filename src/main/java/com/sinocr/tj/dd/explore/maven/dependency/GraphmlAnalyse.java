package com.sinocr.tj.dd.explore.maven.dependency;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sinocr.tj.dd.explore.maven.dependency.Domain.DependencyInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.DependencyInfo.GroupInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.PerProjectInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.PerProjectInfo.PerDepInfo;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;

public class GraphmlAnalyse {

	public static final String KEY = "d0";
	public static final String DIRECT_STRING = "Direct";
	public static final String IN_DIRECT_STRING = "Indirect";
	public static final String SPLIT_STRING = ":";
	public static final int DEP_ITEM_NUM = 5;
	
	private DependencyInfo depInfo=new DependencyInfo();

	public static PerProjectInfo analyse(String filePath) throws IOException {

		PerProjectInfo projectInfo = new PerProjectInfo();
		Graph graph = new TinkerGraph();
		GraphMLReader reader = new GraphMLReader(graph);
		FileModify.modify(filePath);
		InputStream is = new BufferedInputStream(new FileInputStream(filePath));
		reader.inputGraph(is);
		for (Vertex vertex : graph.getVertices()) {
			boolean isHasEdge = false;
			boolean isTopNode = true;

			for (@SuppressWarnings("unused")
			Edge edge : vertex.getEdges(Direction.IN)) {
				isTopNode = false;
			}
			if (isTopNode) {
				projectInfo.setProjectName((String) vertex.getProperty(KEY));
				continue;
			}
			for (Edge edge : vertex.getEdges(Direction.IN)) {
				Vertex vertex1 = edge.getVertex(Direction.OUT);

				for (@SuppressWarnings("unused")
				Edge dege1 : vertex1.getEdges(Direction.IN)) {
					isHasEdge = true;
				}

			}
			String depString = vertex.getProperty(KEY);
			String[] depStrings = depString.split(SPLIT_STRING);
			if (depStrings.length != DEP_ITEM_NUM) {
				System.out.println("Warn: dependency item number doesn't match!");
				continue;
			}
			PerDepInfo depInfo = new PerDepInfo();
			depInfo.setGroupId(depStrings[0]);
			depInfo.setArtifactId(depStrings[1]);
			depInfo.setVersion(depStrings[3]);
			depInfo.setScope(depStrings[4]);
			depInfo.setDirectDep(!isHasEdge);
			projectInfo.addDepInfo(depInfo);

		}
		return projectInfo;
	}
	
	public void recordProject(PerProjectInfo projectInfo){
		for ( PerDepInfo perDepinfo : projectInfo.getDepInfos()) {
			GroupInfo groupInfo=new GroupInfo();
			
			
		}
	}
	


	public static void main(String[] args) {

		try {
			PerProjectInfo projectInfo = GraphmlAnalyse.analyse("D:/Work/workspace/explore-maven/aa.graphml");
			System.out.println(projectInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
