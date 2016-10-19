#!/bin/sh
dirpom() {
	curdir=$1
	cd $curdir
	for subdir in $curdir/*; do
		folderPath=$curdir
		pname=${folderPath##*/}
		result=$(echo $subdir | grep "$exString")
		
		if [ "$result" != "" ]; then
			echo "CURENT" $curdir
			echo "SUB"  $subdir
			echo "AAAA: " $result
			echo "include @ do nothing"
		else
			if [ -d $subdir ];then
				dirpom $subdir
			elif [ -f $subdir ] && [ "$subdir" = "$curdir/pom.xml" ]; then
				echo "pom: " $subdir
				#echo $direc
				#echo "$pname"
				mvn dependency:tree -DoutputFile="$direc/graphml/$pname.graphml" -DoutputType="graphml"
			
			fi
		fi	
		
	done
}

direc=$PWD
exString="@"
echo $direc
dirpom $direc




