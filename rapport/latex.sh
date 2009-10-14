#!/bin/sh
for FICH in *.dia
do
	EPS=`echo ${FICH} | head -c -5`
	dia --filter=eps-pango --export=${EPS}.eps ${FICH}
done
mkdir -p eps
mv *.eps eps/
latex rapport.tex
dvipdf rapport.dvi
rm rapport.dvi
