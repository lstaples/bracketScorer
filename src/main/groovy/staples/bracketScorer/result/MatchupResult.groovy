package staples.bracketScorer.result

import staples.bracketScorer.Matchup
import staples.bracketScorer.Team

class MatchupResult extends Matchup{
    Team winner
    Integer gamesPlayed

    Integer pointsAvailable(){
        def points = theRound.pointsAvailable()
        if(theRound.number == 1)
            points += 3
        points
    }
}
