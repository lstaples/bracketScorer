package staples.bracketScorer.prediction

import groovy.transform.ToString
import staples.bracketScorer.Round
import staples.bracketScorer.Team

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

    Integer getAwardedPoints(){
        predictions.inject(0) {result, i -> result + i.pointsAwared()}
    }

    Integer getAvailablePoints(){
        predictions.inject(0) {result, i -> result + i.pointsAvailable()}
    }

    String print(Boolean cascade){
        def output = "   Predictions for ${super.print()}"
        if(cascade)
            output += '\n' + predictions.sort{it.highSeed.seed}.collect{it.print()}.join('\n')
        output
    }
}
