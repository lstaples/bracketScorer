package staples.bracketScorer

abstract class RoundHolder {
    Matchup getCupFinalist(List rounds){
        def matchup = new Matchup()
        matchup.highSeed = rounds.find{it.conference == Conference.Eastern && it.number == 3}.predictions[0].winner
        matchup.lowSeed = rounds.find{it.conference == Conference.Western && it.number == 3}.predictions[0].winner
        matchup
    }

    ArrayList<Matchup> getSeededNextRound(Integer roundNumber, Conference conference, List round){
        Round roundToSeedFrom = rounds.find{it.conference == conference && it.number == roundNumber}
        ArrayList<Team> predictedWinners = roundToSeedFrom.getWinners()
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

}
