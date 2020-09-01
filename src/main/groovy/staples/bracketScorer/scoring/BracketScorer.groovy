package staples.bracketScorer.scoring

import staples.bracketScorer.prediction.Bracket
import staples.bracketScorer.result.ResultSet

class BracketScorer {
    void scoreBracket(Bracket bracket,ResultSet resultSet){
        //pretty sure these are sorted
        resultSet.rounds.each {roundResult ->
            def roundPrediction = bracket.findRound(roundResult.number,roundResult.conference)
            def winners = roundResult.getWinners()
            def losers = roundResult.getLosers()
            losers.each {team -> team.eliminated = true}
            roundPrediction.predictions.each{prediction ->
                //if the winning team won a matchup this round (doesn't matter against who) the prediction is right
                def matchedWinningTeam = winners.find{it == prediction.winner}
                if(matchedWinningTeam){
                    prediction.winnerIsRight = true
                    //work backwords to check and see if the games played is right
                    def matchedWinningMatchup = roundResult.matchResults.find{it.winner == matchedWinningTeam}
                    if(prediction.roundPrediction.number == 1 && prediction.gamesPlayed == matchedWinningMatchup.gamesPlayed)
                        prediction.gamesPlayedIsRight = true
                }

                //vice versa
                if(losers.find{it == prediction.winner})
                    prediction.winnerIsRight = false

                //if both teams have been eliminitated the prediction is wrong
                if(prediction.highSeed.eliminated && prediction.lowSeed.eliminated)
                    prediction.winnerIsRight = false
                //if no condition is true then we are in a partial round and don't have info to call the prediction
            }

        }
    }
}
