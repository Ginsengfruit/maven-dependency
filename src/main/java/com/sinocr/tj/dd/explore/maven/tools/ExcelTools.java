package com.sinocr.tj.dd.explore.maven.tools;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sinocr.tj.dd.explore.maven.dependency.Domain.DependencyInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.DependencyInfo.GroupInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.DependencyInfo.GroupInfo.ArtifactInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.DependencyInfo.GroupInfo.ArtifactInfo.VersionInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.DependencyInfo.GroupInfo.ArtifactInfo.VersionInfo.ProjectInfo;
import com.sinocr.tj.dd.explore.maven.dependency.Domain.PerProjectInfo.PerDepInfo;

public class ExcelTools {

	public static final int GROUP_INDEX = 0;
	public static final int ARTIFACT_INDEX = 1;
	public static final int VERSION_INDEX = 2;
	public static final int PROJECT_INDEX = 3;
	public static final int DIRECT_INDEX = 4;

	public static void writeWorkBook(DependencyInfo info, String path) throws IOException {
		int rowIndex = 0;
		Workbook wb = new XSSFWorkbook();
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		Sheet sheet = wb.createSheet("DEPENDENCY");
		sheet.createRow(rowIndex);
		rowIndex++;
		for (GroupInfo gropuInfo : info.getGroupInfos()) {
			Row row = sheet.createRow(rowIndex);
			row.createCell(GROUP_INDEX).setCellValue(gropuInfo.getGroupId());
			boolean isFirstArtifact = true;
			for (ArtifactInfo artifactInfo : gropuInfo.getArtifactInfos()) {
				if (!isFirstArtifact) {
					row = sheet.createRow(rowIndex);
				}
				row.createCell(ARTIFACT_INDEX).setCellValue(artifactInfo.getArtifact());
				boolean isFirstVersion = true;
				for (VersionInfo versionInfo : artifactInfo.getVersionInfos()) {
					if (!isFirstVersion) {
						row = sheet.createRow(rowIndex);
					}
					Cell versionCell = row.createCell(VERSION_INDEX);
					versionCell.setCellValue(versionInfo.getVersion());
					if (artifactInfo.getVersionInfos().size() > 1) {
						versionCell.setCellStyle(style);
					}
					boolean isFirstProject = true;
					for (ProjectInfo projectInfo : versionInfo.getProjectInfos()) {
						if (!isFirstProject) {
							row = sheet.createRow(rowIndex);
						}
						row.createCell(PROJECT_INDEX).setCellValue(projectInfo.getName());
						row.createCell(DIRECT_INDEX).setCellValue(
								projectInfo.isDirectDep() ? PerDepInfo.DIRECT_STRING : PerDepInfo.IN_DIRECT_STRING);
						rowIndex++;
						isFirstProject = false;
					}
					isFirstVersion = false;
				}
				isFirstArtifact = false;
			}

		}
		FileOutputStream fos = new FileOutputStream(path);
		wb.write(fos);
		wb.close();

	}

}
