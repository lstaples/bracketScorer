package staples.bracketScorer.prediction

import staples.bracketScorer.Conference
import staples.bracketScorer.Matchup
import staples.bracketScorer.RoundHolder
import staples.bracketScorer.scoring.MatchupScorer

class Bracket extends RoundHolder{
    String owner
    ArrayList<RoundPrediction> rounds = new ArrayList<RoundPrediction>()
    ArrayList<League> leagues = new ArrayList<League>()

    ArrayList<Matchup> getSeededNextRound(Integer roundNumber, Conference conference){
        super.getSeededNextRound(roundNumber,conference,rounds)
    }

    Matchup getCupFinalist(){
        super.getCupFinalist(rounds)
    }


    String print(Boolean cascade, MatchupScorer m){
        def output = "This bracket belongs to ${owner}"
        if(cascade)
            output += '\n' + rounds.collect{it.print(true,m)}.join('\n')
        output

    }

    RoundPrediction findRound(Integer roundNumber, Conference conference){
        rounds.find{round -> round.number == roundNumber && round.conference == conference}
    }

    Integer getAwardedPoints(MatchupScorer m){
        rounds.inject(0) {result, i -> result + i.getAwardedPoints(m)}
    }

    Integer getAvailablePoints(MatchupScorer m){
        rounds.inject(0) {result, i -> result + i.getAvailablePoints(m)}
    }

}
