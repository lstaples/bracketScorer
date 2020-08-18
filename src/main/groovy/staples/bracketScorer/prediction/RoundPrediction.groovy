package staples.bracketScorer.prediction

import groovy.transform.ToString
import staples.bracketScorer.Round
import staples.bracketScorer.Team
import staples.bracketScorer.scoring.MatchupScorer

//@ToString(includeSuper = true,includeNames = true)
class RoundPrediction extends Round{
    ArrayList<MatchupPrediction> predictions = new ArrayList<MatchupPrediction>()
    Bracket bracket

    void setBracket(Bracket bracket){
        this.bracket = bracket
        bracket.rounds << this
    }

    ArrayList<Team> getWinners(){
        super.getWinners(predictions)
    }

    Integer getAwardedPoints(MatchupScorer m){
        predictions.inject(0) {result, i -> result + i.pointsAwared(m)}
    }

    Integer getAvailablePoints(MatchupScorer m){
        predictions.inject(0) {result, i -> result + i.pointsAvailable(m)}
    }

    String print(Boolean cascade,MatchupScorer m){
        def output = "   Predictions for ${super.print()}"
        if(cascade)
            output += '\n' + predictions.sort{it.highSeed.seed}.collect{it.print(m)}.join('\n')
        output
    }
}
