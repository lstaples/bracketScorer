package staples.bracketScorer.prediction

import staples.bracketScorer.Conference
import staples.bracketScorer.Matchup
import staples.bracketScorer.Team

class Bracket {
    String owner
    ArrayList<RoundPrediction> rounds = new ArrayList<RoundPrediction>()

    ArrayList<Matchup> getSeededNextRound(Integer roundNumber, Conference conference){
        RoundPrediction roundToSeedFrom = rounds.find{it.conference == conference && it.number == roundNumber}
        ArrayList<Team> predictedWinners = roundToSeedFrom.predictedWinners()
        ArrayList<Team> seededPredictedWinners = predictedWinners.sort{it.seed}
        def result = new ArrayList<Matchup>()
        for(int i=0; i<= seededPredictedWinners.size()/2; i++){
            def matchup = new Matchup()
            matchup.highSeed = seededPredictedWinners[i]
            matchup.lowSeed = seededPredictedWinners[seededPredictedWinners.size() - i -1]
            result << matchup
        }
        result

    }

    Matchup getCupFinalist(){
        def matchup = new Matchup()
        matchup.highSeed = rounds.find{it.conference == Conference.Eastern && it.number == 3}.predictions[0].winner
        matchup.lowSeed = rounds.find{it.conference == Conference.Western && it.number == 3}.predictions[0].winner
        matchup
    }


    String print(){
        def output = "This bracket belongs to ${owner}\n"
        def downhill = rounds.collect{it.print()}.join('\n')
        return output + downhill

    }

}
