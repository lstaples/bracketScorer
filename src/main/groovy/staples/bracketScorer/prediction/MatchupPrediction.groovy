package staples.bracketScorer.prediction

import staples.bracketScorer.Matchup
import staples.bracketScorer.Team

class MatchupPrediction extends Matchup{
    RoundPrediction roundPrediction
    Boolean winnerIsRight
    Boolean gamesPlayedIsRight


    void setRoundPrediction(RoundPrediction roundPrediction){
        this.roundPrediction = roundPrediction
        roundPrediction.predictions << this
    }

    Integer pointsAvailable(){
        if(winner.eliminated || scored()){
            return 0
        }
        def points = roundPrediction.pointsAvailable()
        if(roundPrediction.number == 1)
            points += 3
        points
    }

    Boolean scored(){
        winnerIsRight != null
    }

    Integer pointsAwared(){
        def points = 0
        if(winnerIsRight){
            points += roundPrediction.pointsAvailable()
            if(gamesPlayedIsRight)
                points += 3
        }
        points
    }

    String print(){
        def output = super.print()
        if(scored())
            output += ". ${pointsAwared()} points awarded."
        else
            output += ". ${pointsAvailable()} points available."
        output
    }

}
