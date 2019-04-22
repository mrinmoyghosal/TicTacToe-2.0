# tictactoe2

Tic-Tac-Toe2 Implementation for 3 players with customizable board size (3 -10)
* Player One is AI Player that starts with a random move and then uses [Minimax](https://en.wikipedia.org/wiki/Minimax) algorithm to find next optimum move.
* Player Two and Three is Human Player takes input from console.

### Dependencies
* JAVA 8
* Maven

### Clone the repository
` git clone https://github.com/mrinmoyghosal/tictactoe2.git`

### Run tests and generate coverage reports
* `cd tictactoe2`
* `mvn clean` 
* `mvn jacoco:prepare-agent test jacoco:report`

### Build Package
* `mvn package`

### Play the game!
* `java -jar target/tictactoe2-0.0.1-SNAPSHOT.jar`

### Build and Play with Docker
* `docker build -t tictactoe .`
* `docker run -it tictactoe`

