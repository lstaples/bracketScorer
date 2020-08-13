package staples.bracketScorer.importer

import staples.bracketScorer.Conference
import staples.bracketScorer.prediction.Bracket
import staples.bracketScorer.prediction.MatchupPrediction
import staples.bracketScorer.prediction.RoundPrediction

class BracketImporter extends RoundHolderImporter{

    ArrayList<Bracket> importBrackets(){
        if(!roundOneData)
            roundOneData = new RoundOneImporter().loadResources().data

        def brackets = new ArrayList<Bracket>()
        List bracketsJson = getBracketJson()

        bracketsJson.each{ bracketJson ->
            def bracket = new Bracket(owner:bracketJson.owner)
            Map sortedRounds =  sortRounds(bracketJson.rounds)

            //loop through 6 rounds of prections and add them in, then cup final, need to go in order to compute seeding
            (1..3).each{roundNumber->
                ["Eastern","Western"].each{ conference ->
                    buildRoundPredications(bracket,roundNumber,conference as Conference, sortedRounds)
                }
            }
            buildRoundPredications(bracket,4,"CupFinal" as Conference, sortedRounds)

            brackets << bracket
        }
        brackets
    }

    private buildRoundPredications(Bracket bracket,Integer roundNumber,Conference conference, Map sortedRounds){

        def roundPrediction = new RoundPrediction(conference: conference,number: roundNumber)
        roundPrediction.bracket = bracket
        sortedRounds[roundNumber][conference].each{prediction ->
            def matchUpPrediciton = new MatchupPrediction()
            matchUpPrediciton.roundPrediction = roundPrediction
            super.constructMatchUpFromJson(matchUpPrediciton,prediction,bracket,roundNumber,conference)
        }

    }

    private List getBracketJson(){
        super.loadJsonResource(('/brackets.json'))

    }

}
