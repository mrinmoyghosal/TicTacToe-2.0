# tictactoe2

This a 3 Player TicTacToe implementation.

* Player 1 -> AI Player (Starts with a random move then uses Maximin algorithm for each move)
* Player 2 & 3 -> Human Player

### Required Dependencies
* Java 8 
* Maven

### Download Repository
`git clone https://github.com/mrinmoyghosal/tictactoe2.git`

### Run Test and Code Coverage

`mvn jacoco:prepare-agent test jacoco:report`

### Building

`mvn clean package`

### Start Playing

`java -jar java -jar target/tictactoe2-0.0.1-SNAPSHOT.jar`

### Build with Docker

` 
docker build -t tictactoe .
`

