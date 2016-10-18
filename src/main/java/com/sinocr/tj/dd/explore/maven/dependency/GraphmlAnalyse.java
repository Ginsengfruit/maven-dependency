package com.sinocr.tj.dd.explore.maven.dependency;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import com.sinocr.tj.dd.explore.maven.dependency.Domain.DependencyInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.DependencyInfo.GroupInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.DependencyInfo.GroupInfo.ArtifactInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.DependencyInfo.GroupInfo.ArtifactInfo.VersionInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.DependencyInfo.GroupInfo.ArtifactInfo.VersionInfo.ProjectInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.PerProjectInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.PerProjectInfo.PerDepInfo;
import com.sinocr.tj.dd.explore.maven.tools.ExcelTools;
import com.sinocr.tj.dd.explore.maven.tools.FileTools;
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

	private DependencyInfo totalDepInfo = new DependencyInfo();

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
			if (depStrings.length == DEP_ITEM_NUM) {
				PerDepInfo depInfo = new PerDepInfo();
				depInfo.setGroupId(depStrings[0]);
				depInfo.setArtifactId(depStrings[1]);
				depInfo.setVersion(depStrings[3]);
				depInfo.setScope(depStrings[4]);
				depInfo.setDirectDep(!isHasEdge);
				projectInfo.addDepInfo(depInfo);
			} else if (depStrings.length == 6) {
				System.out.println("Info: dependency item number is 6!");
				System.out.println("FilePath: " + filePath + " dep: " + depString);
				PerDepInfo depInfo = new PerDepInfo();
				depInfo.setGroupId(depStrings[0]);
				depInfo.setArtifactId(depStrings[1] + ":" + depStrings[3]);
				depInfo.setVersion(depStrings[4]);
				depInfo.setScope(depStrings[5]);
				depInfo.setDirectDep(!isHasEdge);
				projectInfo.addDepInfo(depInfo);
			} else {
				System.out.println("Warn: dependency item number doesn't match!");
				System.out.println("FilePath: " + filePath + " dep: " + depString);
			}
		}
		return projectInfo;
	}

	public void analyseFolder(String folder, String outputPath) throws IOException {
		String[] graphmlFiles = FileTools.listGraphmlFiles(folder);
		String folderPath = folder;
		if (!folder.endsWith("/")) {
			folderPath = folder + "/";
		}
		for (String string : graphmlFiles) {

			PerProjectInfo info = analyse(folderPath + string);
			recordProject(info);
		}
		ExcelTools.writeWorkBook(totalDepInfo, outputPath);

	}

	public void recordProject(PerProjectInfo projectInfo) {
		for (PerDepInfo perDepinfo : projectInfo.getDepInfos()) {
			GroupInfo groupInfo = new GroupInfo();
			// Add groupId
			groupInfo.setGroupId(perDepinfo.getGroupId());
			if (!totalDepInfo.isContainGroupInfo(groupInfo)) {
				totalDepInfo.addGroupInfo(groupInfo);
			}
			for (Iterator<GroupInfo> iterator = totalDepInfo.getGroupInfos().iterator(); iterator.hasNext();) {
				GroupInfo storedGroupInfo = (GroupInfo) iterator.next();
				if (perDepinfo.getGroupId().equals(storedGroupInfo.getGroupId())) {
					addArtifact(storedGroupInfo, perDepinfo, projectInfo.getProjectName());
				}
			}
		}
	}

	// Add ArtifactID
	private void addArtifact(GroupInfo groupInfo, PerDepInfo depInfo, String projectName) {
		ArtifactInfo artifactInfo = new ArtifactInfo();
		artifactInfo.setArtifact(depInfo.getArtifactId());
		if (!groupInfo.isContainArtifactInfo(artifactInfo)) {
			groupInfo.addArtifactInfo(artifactInfo);
		}
		for (Iterator<ArtifactInfo> iterator = groupInfo.getArtifactInfos().iterator(); iterator.hasNext();) {
			ArtifactInfo storedInfo = (ArtifactInfo) iterator.next();
			if (depInfo.getArtifactId().equals(storedInfo.getArtifact())) {
				addVersion(storedInfo, depInfo, projectName);
			}
		}

	}

	// Add Version
	private void addVersion(ArtifactInfo artifactInfo, PerDepInfo depInfo, String projectName) {
		VersionInfo versionInfo = new VersionInfo();
		versionInfo.setVersion(depInfo.getVersion());
		if (!artifactInfo.isContainVersionInfo(versionInfo)) {
			artifactInfo.addVersoionInfo(versionInfo);
		}
		for (Iterator<VersionInfo> iterator = artifactInfo.getVersionInfos().iterator(); iterator.hasNext();) {
			VersionInfo storedVersonInfo = (VersionInfo) iterator.next();
			if (depInfo.getVersion().equals(storedVersonInfo.getVersion())) {
				addProject(storedVersonInfo, depInfo, projectName);
			}
		}

	}

	// Add Project
	private void addProject(VersionInfo versionInfo, PerDepInfo depInfo, String projectName) {
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setName(projectName);
		projectInfo.setDirectDep(depInfo.isDirectDep());
		versionInfo.addProjectInfo(projectInfo);
	}

	public DependencyInfo getTotalDepInfo() {
		return totalDepInfo;
	}

	public static void main(String[] args) {

		try {

			GraphmlAnalyse analyse = new GraphmlAnalyse();
			analyse.analyseFolder("D:/Work/graphml/", "D:/Work/graphml/result.xlsx");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
