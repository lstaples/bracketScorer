package staples.bracketScorer.result

import staples.bracketScorer.Round
import staples.bracketScorer.Team

class RoundResult extends Round{
    ArrayList<MatchupResult> results

    ArrayList<Team> getWinners(){
        super.getWinners(results)
    }
}
