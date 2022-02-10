## Creazione file Jar
Per poter creare il file jar si dovra clonare la repositori in una cartella a scelte sul proprio pc,una volta clonato il progetto lo si dovrà aprre con un ide a scelta.(si consiglia netbeans)
Successivamente dal proprio ide basta andare sulla voce "Build",  il progetto verra buildato nella cartella "jar" creatasi in automatico nella WD del progetto.

##Esecuzione JAR
Il file jar puo essere eseguito in qualsiasi Directory basta che al suo interno sia presente la cartella "input" e il file di properties reperibile all'interno della root del progetto.
Se verra eseguito il jar senza aver caricato il file ServiceMenu.json dentro la cartella "input" il programma si fermerà stampando a console il seguente messaggio 
"Il file di input nel percorso :""path del presunto fil""  non esiste.Il programma verra terminato in modo da permettere all'utente di inserire il file."

comando per eseguire il jar:

java -jar EsercizioOmicron-1.jar
