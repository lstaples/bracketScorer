package staples.bracketScorer.importer

import groovy.json.JsonSlurper
import staples.bracketScorer.Conference
import staples.bracketScorer.Matchup
import staples.bracketScorer.RoundHolder

abstract class RoundHolderImporter {
    //helper objects to get out of searching through JSON
    RoundOneData roundOneData

    protected Map sortRounds(List roundsJson){
        def sorted = [:]
        roundsJson.each{
            if(!sorted[it.round])
                sorted[it.round] = [:]
            sorted[it.round][it.conference as Conference] = it.predictions}
        sorted
    }

    protected List loadJsonResource(String resourcePath){
        def slurper = new JsonSlurper()
        def url = getClass().getResource(resourcePath)
        slurper.parse(url)

    }

    void constructMatchUpFromJson(Matchup matchUp,Map matchUpJson,RoundHolder roundHolder,Integer roundNumber,Conference conference)  {
        def matchupMatch
        matchUp.winner = roundOneData.teams[matchUpJson.winner]
        matchUp.gamesPlayed = matchUpJson.gamesPlayed
        switch(roundNumber){
            case 1:
                matchupMatch = roundOneData.matchups.find{it.highSeed.name == matchUpJson.winner || it.lowSeed.name == matchUpJson.winner}
                break
            case [2,3]:
                def seededMatchups = roundHolder.getSeededNextRound(roundNumber - 1,conference)
                matchupMatch = seededMatchups.find{it.highSeed.name == matchUpJson.winner || it.lowSeed.name == matchUpJson.winner}
                break
            case 4:
                matchupMatch = roundHolder.getCupFinalist()
                break
        }

        matchUp.highSeed = matchupMatch.highSeed
        matchUp.lowSeed = matchupMatch.lowSeed

    }


}
