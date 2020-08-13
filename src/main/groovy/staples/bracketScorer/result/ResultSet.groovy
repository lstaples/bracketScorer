package staples.bracketScorer.result

import staples.bracketScorer.Conference
import staples.bracketScorer.Matchup
import staples.bracketScorer.RoundHolder

class ResultSet extends RoundHolder{
    ArrayList<RoundResult> rounds = new ArrayList<RoundResult>()

    ArrayList<Matchup> getSeededNextRound(Integer roundNumber, Conference conference){
        super.getSeededNextRound(roundNumber,conference,rounds)
    }

    Matchup getCupFinalist(){
        super.getCupFinalist(rounds)
    }


    String print(){
        def output = "Playoff Results:${owner}\n"
        def downhill = rounds.collect{it.print()}.join('\n')
        return output + downhill

    }
}
