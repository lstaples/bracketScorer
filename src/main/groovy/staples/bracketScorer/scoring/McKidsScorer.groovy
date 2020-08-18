package staples.bracketScorer.scoring

import staples.bracketScorer.prediction.MatchupPrediction

class McKidsScorer implements  MatchupScorer{
    Integer pointsAvailable(MatchupPrediction m){
        if(m.winner.eliminated || m.scored()){
            return 0
        }
        def points = getRoundPoint(m)
        if(m.roundPrediction.number == 1)
            points += 3
        points
    }
    Integer pointsAwared(MatchupPrediction m){
        def points = 0
        if(m.winnerIsRight){
            points += getRoundPoint(m)
            if(m.gamesPlayedIsRight)
                points += 3
        }
        points
    }


    private Integer getRoundPoint(MatchupPrediction m){
        switch(m.roundPrediction.number){
            case 1:
                return 10
            case 2:
                return 25
            case 3:
                return 50
            case 4:
                return 100
        }
    }
}
