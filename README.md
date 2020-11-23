### Schritt für Schritt beschreibung wie das Produkt installiert wird:

##### System und IDE's aufsetzen
1.	Java mind. Version 8, Java Version 13 empfohlen
(https://www.oracle.com/java/technologies/javase-jdk13-downloads.html)
2.	Auf Kommandozeile Java Version mit `java –version` überprüfen
3.	Dazu benötigt man folgende Entwicklungsumgebungen (teilweise auch Lizenzen notwendig):
    1.	IntelliJ (https://www.jetbrains.com/de-de/idea/download/) (auch mit Eclipse oder anderer IDE möglich)
    2.	WebStorm (https://www.jetbrains.com/de-de/webstorm/download/) (auch mit VS Code oder anderer IDE möglich)
    3.	MySQL Workbrench GUI (https://dev.mysql.com/downloads/workbench/)
    4.	MySQL Server (https://dev.mysql.com/downloads/mysql/)

##### Datenbank aufsetzen:
1.	Auf Workbrench neue MySQL Connection erstellen  
    1.	Connection-Name: Digitale-Agenda
    2.	Hostname: localhost oder 127.0.0.1
    3.	Post: 3306
    4.	Username: root
    5.	Passwort: sml12345
    6. 	Verbindung kann getestet werden durch drücken auf «Test Connection»
2.	Mit der Datenbank verbinden (auf Datenbank drücken und Passwort eingeben)
3.	Neues Schema erstellen  
1.	Schema Name: termin-agenda dann: Apply --> Apply --> Close
4.	Auf “Schemas” drücken, dort sollte nun das Schema termin-agenda neben dem sys Schema sein

##### Backend aufsetzen:
1.	Programm von GitHub herunterladen und in der Kommandozeile ausführen: `https://github.com/KFreiburghaus/digitale_agenda.git`
2.	IntelliJ öffnen und «Open or Import» drücken
3.	In den durch `git clone` generierten Ordner (**digitale_agenda**) navigieren, dann Code/Backend öffnen und die pom.xml-Datei auswählen und als Projekt öffnen («Open as Project»)
4.	Im Verzeichnis *Backend/src/main/java/com.example.demo* die Datei DemoApplication öffnen und starten (drücken auf den grünen Run Button)  
5.	Nun sollten im Backend im Schema «termin-agenda» folgende Tabellen erstellt worden sein:


##### Frontend aufsetzen:
1.	WebStorm öffnen und «Open» drücken
2.	In den durch `git clone` generierten Ordner (digitale_agenda) navidiere, Ordner Code öffnen und den Ordner Frontend auswählen (kein Doppelklick) und dann «Open» drücken»
3.	In der Kommandozeile npm (ein Paketmanage) installieren: `sudo npm install npm@latest -global`
4.	Danach mit npm Angular CLI installieren: `sudo npm install -global @angular/cli`
5.	«npm install» auf der Kommandozeile eingeben und ausführen (dauert eine Weile)
6.	Danach ?ng serve -o? in der Kommandozeile eingeben und ausführen (dauert eine Weile)
7.	Dann öffnet sicher der Browser automatisch mit der Startseite
8.	Nun können Firmen registriert werden und dann Termine bei den jeweiligen Firmen erstellt werden (alle Daten (Firmen, Termine, Öffnungszeiten...) sind in den Datenbank-Tabellen ersichtlich)





