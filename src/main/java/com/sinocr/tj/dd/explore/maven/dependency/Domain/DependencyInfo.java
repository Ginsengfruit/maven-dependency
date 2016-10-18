package com.sinocr.tj.dd.explore.maven.dependency.Domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DependencyInfo {

	private Date date;

	private Set<GroupInfo> groupInfos = new HashSet<>();

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<GroupInfo> getGroupInfos() {
		return groupInfos;
	}

	public void setGroupInfos(Set<GroupInfo> groupInfos) {
		this.groupInfos = groupInfos;
	}

	public void addGroupInfo(GroupInfo info) {
		groupInfos.add(info);
	}

	public boolean isContainGroupInfo(GroupInfo info) {
		return groupInfos.contains(info);
	}

	public static class GroupInfo {

		private String groupId;
		private Set<ArtifactInfo> artifactInfos = new HashSet<>();

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			GroupInfo other = (GroupInfo) obj;
			if (groupId == null) {
				if (other.groupId != null)
					return false;
			} else if (!groupId.equals(other.groupId))
				return false;
			return true;
		}

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public Set<ArtifactInfo> getArtifactInfos() {
			return artifactInfos;
		}

		public void setArtifactInfos(Set<ArtifactInfo> artifactInfos) {
			this.artifactInfos = artifactInfos;
		}

		public void addArtifactInfo(ArtifactInfo info) {
			artifactInfos.add(info);
		}

		public boolean isContainArtifactInfo(ArtifactInfo info) {
			return artifactInfos.contains(info);
		}

		public static class ArtifactInfo {

			private String artifactId;
			private Set<VersionInfo> versionInfos = new HashSet<>();

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((artifactId == null) ? 0 : artifactId.hashCode());
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				ArtifactInfo other = (ArtifactInfo) obj;
				if (artifactId == null) {
					if (other.artifactId != null)
						return false;
				} else if (!artifactId.equals(other.artifactId))
					return false;
				return true;
			}

			public String getArtifact() {
				return artifactId;
			}

			public void setArtifact(String artifact) {
				this.artifactId = artifact;
			}

			public Set<VersionInfo> getVersionInfos() {
				return versionInfos;
			}

			public void setVersionInfos(Set<VersionInfo> versionInfos) {
				this.versionInfos = versionInfos;
			}

			public void addVersoionInfo(VersionInfo versionInfo) {
				versionInfos.add(versionInfo);
			}

			public boolean isContainVersionInfo(VersionInfo info) {
				return versionInfos.contains(info);
			}

			public static class VersionInfo {

				private String version;
				private Set<ProjectInfo> projectInfos = new HashSet<>();

				@Override
				public int hashCode() {
					final int prime = 31;
					int result = 1;
					result = prime * result + ((version == null) ? 0 : version.hashCode());
					return result;
				}

				@Override
				public boolean equals(Object obj) {
					if (this == obj)
						return true;
					if (obj == null)
						return false;
					if (getClass() != obj.getClass())
						return false;
					VersionInfo other = (VersionInfo) obj;
					if (version == null) {
						if (other.version != null)
							return false;
					} else if (!version.equals(other.version))
						return false;
					return true;
				}

				public String getVersion() {
					return version;
				}

				public void setVersion(String version) {
					this.version = version;
				}

				public Set<ProjectInfo> getProjectInfos() {
					return projectInfos;
				}

				public void setProjectInfos(Set<ProjectInfo> projectInfos) {
					this.projectInfos = projectInfos;
				}

				public void addProjectInfo(ProjectInfo info) {
					projectInfos.add(info);
				}

				public boolean isContainProjectInfo(ProjectInfo info) {
					return projectInfos.contains(info);
				}

				public static class ProjectInfo {

					private String name;
					private boolean isDirectDep;

					@Override
					public int hashCode() {
						final int prime = 31;
						int result = 1;
						result = prime * result + ((name == null) ? 0 : name.hashCode());
						return result;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						ProjectInfo other = (ProjectInfo) obj;
						if (name == null) {
							if (other.name != null)
								return false;
						} else if (!name.equals(other.name))
							return false;
						return true;
					}

					public String getName() {
						return name;
					}

					public void setName(String name) {
						this.name = name;
					}

					public boolean isDirectDep() {
						return isDirectDep;
					}

					public void setDirectDep(boolean isDirectDep) {
						this.isDirectDep = isDirectDep;
					}

				}

			}

		}

	}

}
