package staples.bracketScorer.prediction

import staples.bracketScorer.Round
import staples.bracketScorer.Team

class RoundPrediction extends Round{
    ArrayList<MatchupPrediction> predictions = new ArrayList<MatchupPrediction>()

    ArrayList<Team> predictedWinners(){
        predictions.collect{it.winner}
    }

    String print(){
        def output = "   Predictions for ${conference.toString()} conference round ${number}\n"
        def downhill = predictions.sort{it.highSeed.seed}.collect{it.print()}.join('\n')
        return output + downhill
    }
}
