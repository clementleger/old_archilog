# variables
JAVAC = javac
RM_CMD = rm -f *.class Main.jar

all: main

main:
	rm -rf *.class
	$(JAVAC) Main.java
	
jar: main
	rm -rf Main.jar
	jar cmf MANIFEST.MF Main.jar *.class *.xml sprites/ org/

launch:
	java Main

clean: source_clean

source_clean:
	@echo "--- Nettoyage du répertoire de source"
	@$(RM_CMD)
	
