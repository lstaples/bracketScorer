package staples.bracketScorer


class Round {
    Conference conference
    Integer number


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
