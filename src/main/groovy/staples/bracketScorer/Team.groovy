package staples.bracketScorer

import groovy.transform.ToString

@ToString
class Team {
    String name
    Conference conference
    Integer seed
    Boolean eliminated = false

}
