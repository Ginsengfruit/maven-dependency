#!/bin/sh
dirpom() {
	curdir=$1
	cd $curdir
	for subdir in $curdir/*; do
		folderPath=$curdir
		pname=${folderPath##*/}
		result=$(echo $subdir | grep "$exString")
		#echo "CURENT: $curdir"
		#echo "subdir: $subdir"
		if [ "$result" != "" ]; then
			echo "Fold Name: $subdir include @ do nothing"
		else
			if [ -d $subdir ];then
				dirpom $subdir
				curdir=$1
			elif [ -f $subdir ] && [ "$subdir" = "$curdir/pom.xml" ]; then
				echo "pom: " $subdir
				cd $curdir
				mvn dependency:tree -DoutputFile="$direc/graphml/$pname.graphml" -DoutputType="graphml"
			
			fi
		fi	
		
	done
}

direc=$PWD
exString="@"
echo $direc
dirpom $direc




