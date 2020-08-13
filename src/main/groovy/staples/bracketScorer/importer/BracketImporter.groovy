package staples.bracketScorer.importer

import groovy.json.*
import staples.bracketScorer.Conference
import staples.bracketScorer.Matchup
import staples.bracketScorer.Team
import staples.bracketScorer.prediction.Bracket
import staples.bracketScorer.prediction.MatchupPrediction
import staples.bracketScorer.prediction.RoundPrediction

class BracketImporter {

    //helper objects to get out of searching through JSON
    Map teams= [:]
    ArrayList<Matchup> matchups= new ArrayList<Matchup>()

    ArrayList<Bracket> importBrackets(){
        loadResources()

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
        def matchup
        //used in rounds 2-3
        ArrayList<Matchup> seededMatchups

        def roundPrediction = new RoundPrediction(conference: conference,number: roundNumber)
        bracket.rounds << roundPrediction
        sortedRounds[roundNumber][conference].each{prediction ->
            def matchUpPrediciton = new MatchupPrediction()
            matchUpPrediciton.winner = teams[prediction.winner]
            matchUpPrediciton.gamesPlayed = prediction.gamesPlayed
            switch(roundNumber){
                case 1:
                    matchup = matchups.find{it.highSeed.name == prediction.winner || it.lowSeed.name == prediction.winner}
                    break
                case [2,3]:
                    if(!seededMatchups)
                        seededMatchups = bracket.getSeededNextRound(roundNumber - 1,conference)
                    matchup = seededMatchups.find{it.highSeed.name == prediction.winner || it.lowSeed.name == prediction.winner}
                    break
                case 4:
                    matchup = bracket.getCupFinalist()
                    break
            }

            matchUpPrediciton.highSeed = matchup.highSeed
            matchUpPrediciton.lowSeed = matchup.lowSeed
            roundPrediction.predictions << matchUpPrediciton
        }

    }

    private loadResources(){
        def slurper = new JsonSlurper()
        def url = getClass().getResource('/round1Matchups.json')
        def round1 = slurper.parse(url)
        round1.each{
            def conference = it.conference as Conference
            it.matchups.each{matchup ->
                def team1 = new Team(name: matchup[0].team, seed: matchup[0].seed, conference: conference)
                def team2 = new Team(name: matchup[1].team, seed: matchup[1].seed, conference: conference)
                teams[team1.name] = team1
                teams[team2.name] = team2
                def highSeed = (team1.seed < team2.seed) ? team1: team2
                def lowSeed = (team1.seed > team2.seed) ? team1: team2
               matchups << new Matchup(highSeed: highSeed, lowSeed: lowSeed)

            }
        }
    }

    private List getBracketJson(){
        def slurper = new JsonSlurper()
        def url = getClass().getResource('/brackets.json')
        slurper.parse(url)

    }

    private Map sortRounds(List roundsJson){
        def sorted = [:]
        roundsJson.each{
            if(!sorted[it.round])
                sorted[it.round] = [:]
            sorted[it.round][it.conference as Conference] = it.predictions}
        sorted
    }

}
