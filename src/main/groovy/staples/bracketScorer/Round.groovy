package staples.bracketScorer


abstract class Round {
    Conference conference
    Integer number

    ArrayList<Team> getWinners(List matchups){
        matchups.collect{it.winner}
    }

    Integer pointsAvailable(){
        switch(number){
            case 1:
                return 10
            case 2:
                return 25
            case 3:
                return 50
            case 4:
                return 100
        }

    }
}
