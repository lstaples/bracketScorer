package staples.bracketScorer.result

import staples.bracketScorer.Matchup

class MatchupResult extends Matchup{


    Integer pointsAvailable(){
        def points = theRound.pointsAvailable()
        if(theRound.number == 1)
            points += 3
        points
    }
}
