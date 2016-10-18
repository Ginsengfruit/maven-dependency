package com.sinocr.tj.dd.explore.maven.dependency.Domain;

import java.util.ArrayList;
import java.util.List;

public class PerProjectInfo {

	private String projectName;
	private List<PerDepInfo> depInfos = new ArrayList<>();

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<PerDepInfo> getDepInfos() {
		return depInfos;
	}

	public void setDepInfos(List<PerDepInfo> depInfos) {
		this.depInfos = depInfos;
	}

	public void addDepInfo(PerDepInfo info) {
		depInfos.add(info);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(projectName + "\r\n");
		for (PerDepInfo perDepInfo : depInfos) {
			sb.append(perDepInfo.toString() + "\r\n");
		}
		return sb.toString();
	}

	public static class PerDepInfo {

		public static final String DIRECT_STRING = "Direct";
		public static final String IN_DIRECT_STRING = "Indirect";

		private String groupId;
		private String artifactId;
		private String version;
		private String scope;
		private boolean isDirectDep;

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public String getArtifactId() {
			return artifactId;
		}

		public void setArtifactId(String artifactId) {
			this.artifactId = artifactId;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}

		public boolean isDirectDep() {
			return isDirectDep;
		}

		public void setDirectDep(boolean isDirectDep) {
			this.isDirectDep = isDirectDep;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(groupId + " " + artifactId + " " + version + " " + scope + " "
					+ (isDirectDep ? DIRECT_STRING : IN_DIRECT_STRING));
			return sb.toString();
		}

	}

}
