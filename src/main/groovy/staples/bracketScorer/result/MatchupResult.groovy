package staples.bracketScorer.result

import staples.bracketScorer.Matchup

class MatchupResult extends Matchup{

    RoundResult roundResult

    void setRoundResult(RoundResult roundResult){
        this.roundResult = roundResult
        roundResult.matchResults << this
    }
}
