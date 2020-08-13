package staples.bracketScorer.importer

import staples.bracketScorer.Conference
import staples.bracketScorer.result.MatchupResult
import staples.bracketScorer.result.ResultSet
import staples.bracketScorer.result.RoundResult

class ResultImporter extends RoundHolderImporter{

    ResultSet importResults(){
        if(!roundOneData)
            roundOneData = new RoundOneImporter().loadResources().data

        def resultSet = new ResultSet()
        List resultsJson = getResultsJson()
        Map sortedRounds =  sortRounds(resultsJson)

        //loop through 6 rounds of results and add them in, then cup final, need to go in order to compute seeding
        (1..3).each{roundNumber->
            ["Eastern","Western"].each{ conference ->
                //don't assume round exists...partial results
                if(sortedRounds[roundNumber] && sortedRounds[roundNumber][conference as Conference])
                    buildRoundResults(resultSet,roundNumber,conference as Conference, sortedRounds)
            }
        }
        //same non-assumption
        if(sortedRounds[4])
            buildRoundResults(resultSet,4,"CupFinal" as Conference, sortedRounds)

        resultSet
    }

    private buildRoundResults(ResultSet resultSet, Integer roundNumber, Conference conference, Map sortedRounds){
        def roundResult = new RoundResult(conference: conference,number: roundNumber)
        roundResult.resultSet = resultSet
        sortedRounds[roundNumber][conference].each{prediction ->
            def matchUpResult = new MatchupResult()
            matchUpResult.roundResult = roundResult
            super.constructMatchUpFromJson(matchUpResult,prediction,resultSet,roundNumber,conference)
        }

    }
    private List getResultsJson(){
        super.loadJsonResource(('/results.json'))
    }
}
