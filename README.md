# maven-dependency
Transform maven dependency of many projects in different form.
Like showed below:
<table>
<tr> <td>-GroupId0</td> <td>-ArtifactId0</td> <td>-Version0</td> <td>-UsedProjectName0</td> <td>-isDirectDependency</td> </tr>   
<tr> <td></td>          <td></td>                 <td></td>          <td>-UsedProjectName1</td> <td>-isDirectDependency</td> </tr>
<tr> <td></td>      <td></td>                 <td>-Version1</td> <td>-UsedProjectName2</td> <td>-isDirectDependency</td> </tr>   
<tr> <td></td>      <td>-ArtifactId1</td> <td>-Version0</td> <td>-UsedProjectName0</td> <td>-isDirectDependency</td></tr>
<tr> <td>-GroupId1</td> <td>-ArtifactId0</td> <td>-Version0</td> <td>-UsedProjectName0</td> <td>-isDirectDependency</td> </tr>
</table>


use .sh file in linux to generate graphml file, 
then use java to generate excel xlsx file
