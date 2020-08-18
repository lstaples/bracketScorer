package staples.bracketScorer.prediction

import staples.bracketScorer.Matchup
import staples.bracketScorer.Team
import staples.bracketScorer.scoring.MatchupScorer

class MatchupPrediction extends Matchup{
    RoundPrediction roundPrediction
    Boolean winnerIsRight
    Boolean gamesPlayedIsRight


    void setRoundPrediction(RoundPrediction roundPrediction){
        this.roundPrediction = roundPrediction
        roundPrediction.predictions << this
    }

    Integer pointsAvailable(MatchupScorer m){
        m.pointsAvailable(this)
    }

    Boolean scored(){
        winnerIsRight != null
    }

    Integer pointsAwared(MatchupScorer m){
        m.pointsAwared(this)
    }

    String print(MatchupScorer m){
        def output = super.print()
        if(scored())
            output += ". ${pointsAwared(m)} points awarded."
        else
            output += ". ${pointsAvailable(m)} points available."
        output
    }

}
