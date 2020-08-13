package staples.bracketScorer.result

import staples.bracketScorer.Round
import staples.bracketScorer.Team

class RoundResult extends Round{
    ArrayList<MatchupResult> matchResults
    ResultSet resultSet

    void setResultSet(ResultSet resultSet){
        this.resultSet = resultSet
        resultSet.rounds << this
    }

    ArrayList<Team> getWinners(){
        super.getWinners(matchResults)
    }

    String print(Boolean cascade){
        def output = "   Results for ${super.print()}"
        if(cascade)
            output += '\n' + matchResults.sort{it.highSeed.seed}.collect{it.print()}.join('\n')
        output
    }
}
