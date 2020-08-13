package staples.bracketScorer

import groovy.transform.ToString

@ToString
class Matchup {
    Team highSeed
    Team lowSeed
    Team winner
    Integer gamesPlayed
}
