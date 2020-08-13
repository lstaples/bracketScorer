package staples.bracketScorer.prediction

import staples.bracketScorer.Round
import staples.bracketScorer.Team

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

    String print(){
        def output = "   Predictions for ${conference.toString()} conference round ${number}\n"
        def downhill = predictions.sort{it.highSeed.seed}.collect{it.print()}.join('\n')
        return output + downhill
    }
}
