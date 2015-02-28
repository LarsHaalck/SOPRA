# SOPRA
Aktueller Status: [![Travis-Stats](https://magnum.travis-ci.com/LHulk/SOPRA.svg?token=xByAUd2R8jHHWnUTMZau&branch=master)](https://magnum.travis-ci.com/LHulk/SOPRA/builds)

# Code Style
- Öffnende geschweifte Klammern in eigener Zeile
```
private boolean sagJa()
{
    return true;
}
```
- Variablennamen beginnen immer mit Kleinbuchstaben und verwenden camelCase
`diesIstEineVariable`
- Nach Kommata folgt ein Leerzeichen
`public funktion(String name, boolean wahrheitswert)`

#build.properties
- build.properties gehört in das Hauptverzeichnis
- beispielhafte build.properties (für Windows)
```
path.variable.maven_repository=C\:/Users/userName/.m2/repository
path.variable.idea.home=D\:/Program Files (x86)/JetBrains/IntelliJ IDEA 14.0.3
jdk.home.1.8=D\:/Program Files/Java/jdk1.8.0_25
idea.home=D\:\\Program Files (x86)\\JetBrains\\IntelliJ IDEA 14.0.3
javac2.instrumentation.includeJavaRuntime=false
```
