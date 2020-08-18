package staples.bracketScorer.scoring

import staples.bracketScorer.prediction.MatchupPrediction

class StaplesFamilyScorer implements MatchupScorer{
    Integer pointsAvailable(MatchupPrediction m){
        if(m.winner.eliminated || m.scored())
             0
        else
            getRoundPoint(m)
    }
    Integer pointsAwared(MatchupPrediction m){
      m.winnerIsRight ? getRoundPoint(m) : 0
    }

    private Integer getRoundPoint(MatchupPrediction m){
        switch(m.roundPrediction.number){
            case 1:
                return 1
            case 2:
                return 2
            case 3:
                return 4
            case 4:
                return 8
        }
    }
}
