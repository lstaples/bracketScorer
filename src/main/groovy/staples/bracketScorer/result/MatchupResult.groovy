package staples.bracketScorer.result

import staples.bracketScorer.Matchup

class MatchupResult extends Matchup{

    RoundResult roundResult

    void setRoundResult(RoundResult roundResult){
        this.roundResult = roundResult
        roundResult.matchResults << this
    }


    Integer pointsAvailable(){
        def points = theRound.pointsAvailable()
        if(theRound.number == 1)
            points += 3
        points
    }
}
