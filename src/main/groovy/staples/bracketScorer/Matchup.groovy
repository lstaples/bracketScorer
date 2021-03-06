package staples.bracketScorer

import groovy.transform.ToString

@ToString
class Matchup {
    Team highSeed
    Team lowSeed
    Team winner
    Team loser
    Integer gamesPlayed

    void setWinner(Team team){
        winner = team
        loser = (team == highSeed)? lowSeed: highSeed
    }

    String print(){
        def output = "      ${highSeed?.name}{${highSeed.seed}) is playing ${lowSeed?.name}{${lowSeed.seed}).  ${winner.name} wins"
        if(gamesPlayed)
            output += " in ${gamesPlayed} games"
        output
    }
}
