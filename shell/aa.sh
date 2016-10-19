#!/bin/sh
dirpom() {
	curdir=$1
	cd $curdir
	for subdir in $curdir/*; do
		if [ -d $subdir ];then
			dirpom $subdir
		elif [ -f $subdir ] && [ "$subdir" = "$curdir/pom.xml" ]; then
				echo "pom: " $subdir
				echo $direc
				folderPath=$curdir
				pname=${folderPath##*/}
				echo "$pname"
				mvn dependency:tree -DoutputFile="$direc/graphml/$pname.graphml" -DoutputType="graphml"
		fi
	done
}

direc=$PWD
echo $direc
dirpom $direc




