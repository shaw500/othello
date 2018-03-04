## Notes
- A guide is provided when the game starts, run the game as described below to find out what commands are supported
- Building and running the app requires mvn, along with maven-assembly-plugin
- The code only has two dependencies: Guava, and JUnit.

## How to execute:
Run the following commands in the project root:
```
> mvn package
> java -jar target/othello-1.0-jar-with-dependencies.jar 
```

Alternatively, you can run the main method in org.ui.OthelloREPL