# Projekat iz razvoja softvera
*JavaFX, Scene Builder, Material design for JavaFX.*

## Database

persistance.xml -> /src/META-INF *Rename raspored , set your projects name.*

* Right click on project -> JPA Tools -> Generate Tables from Entities;
* Error will occure, dont worry it's normal.
* Now go to Run Configuration -> chose EclipseLink Table Generator -> Dependencies -> remove derbytools.jar -> Apply -> Run 
* Run data_adder
* Run tester

## JavaFX

* CREATE FILE .classpath IN ROOT FOLDER OF PROJECT 
* CHANGE */home/mahha/razvoj_projekat/raspored/libs/* -> to your path.
* https://gluonhq.com/products/javafx/ download archive that is ment for you (Linux 11) and extract to your path as in previous step.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
	<classpathentry kind="src" path="src"/>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-11">
		<attributes>
			<attribute name="module" value="true"/>
			<attribute name="owner.project.facets" value="java"/>
		</attributes>
	</classpathentry>
	<classpathentry kind="con" path="org.eclipse.jdt.USER_LIBRARY/EclipseLink 2.5.2">
		<attributes>
			<attribute name="owner.project.facets" value="jpt.jpa"/>
		</attributes>
	</classpathentry>
	<classpathentry kind="con" path="org.eclipse.datatools.connectivity.jdt.DRIVERLIBRARY/Derby Embedded JDBC Driver"/>
	<classpathentry kind="lib" path="/home/mahha/razvoj_projekat/raspored/libs/src.zip"/>
	<classpathentry kind="lib" path="/home/mahha/razvoj_projekat/raspored/libs/javafx-swt.jar"/>
	<classpathentry kind="lib" path="/home/mahha/razvoj_projekat/raspored/libs/javafx.web.jar"/>
	<classpathentry kind="lib" path="/home/mahha/razvoj_projekat/raspored/libs/javafx.swing.jar"/>
	<classpathentry kind="lib" path="/home/mahha/razvoj_projekat/raspored/libs/javafx.media.jar"/>
	<classpathentry kind="lib" path="/home/mahha/razvoj_projekat/raspored/libs/javafx.graphics.jar"/>
	<classpathentry kind="lib" path="/home/mahha/razvoj_projekat/raspored/libs/javafx.fxml.jar"/>
	<classpathentry kind="lib" path="/home/mahha/razvoj_projekat/raspored/libs/javafx.controls.jar"/>
	<classpathentry kind="lib" path="/home/mahha/razvoj_projekat/raspored/libs/javafx.base.jar"/>
	<classpathentry kind="output" path="build/classes"/>
</classpath>
```

* Run configurations for application go to arguments.
* Under VM arguments put :
```bash
--module-path /home/mahha/razvoj_projekat/raspored/libs/ --add-modules=javafx.controls,javafx.fxml
```
* Apply
* Run

## ADD MATERIAL DESIGN
* Right click on project -> Properties
* Java Build Path -> Libraries
* Add jfoenix-9.0.9.jar apply and close.

## LOGIN

**Prodekan:**  
&nbsp;&nbsp;*USERNAME:* emir.meskovic  
&nbsp;&nbsp;*PASSWORD:* emir123  
