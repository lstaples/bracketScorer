package staples.bracketScorer.prediction

import staples.bracketScorer.Conference
import staples.bracketScorer.Matchup
import staples.bracketScorer.RoundHolder

class Bracket extends RoundHolder{
    String owner
    ArrayList<RoundPrediction> rounds = new ArrayList<RoundPrediction>()

    ArrayList<Matchup> getSeededNextRound(Integer roundNumber, Conference conference){
        super.getSeededNextRound(roundNumber,conference,rounds)
    }

    Matchup getCupFinalist(){
        super.getCupFinalist(rounds)
    }


    String print(){
        def output = "This bracket belongs to ${owner}\n"
        def downhill = rounds.collect{it.print()}.join('\n')
        return output + downhill

    }

}
