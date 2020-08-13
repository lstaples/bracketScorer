package staples.bracketScorer.prediction

import staples.bracketScorer.Matchup
import staples.bracketScorer.Team
import staples.bracketScorer.result.MatchupResult

class MatchupPrediction extends Matchup{
    Team winner
    Integer gamesPlayed

    Integer pointsAwared(MatchupResult result){
        def points = 0
        if(winner == result.winner){
            points += theRound.pointsAvailable()
            if(theRound.number == 1  && gamesPlayed == result.gamesPlayed)
                points += 3
        }
        points
    }

    String print(){
        def output = "      ${highSeed?.name}{${highSeed.seed}) is playing ${lowSeed?.name}{${lowSeed.seed}).  ${winner.name} wins"
        if(gamesPlayed)
            output += " in ${gamesPlayed} games"
        output
    }
}
