import spock.lang.Specification
import staples.bracketScorer.importer.BracketImporter
import staples.bracketScorer.importer.ResultImporter
import staples.bracketScorer.result.ResultSet

class testRunnerSpec extends Specification{
    //too lazy to write real tests.  so this is a runner to eyeball results

    def " does this fucking look right to you?"(){
        when:
        def brackets = new BracketImporter().importBrackets()
        brackets.each{println(it.print(true))}
        then: println("end of output")
    }

    def "how about this son a bitch?"(){
        when:
        ResultSet resultSet = new ResultImporter().importResults()
        println(resultSet.print(true))
        then: println("end of output")
    }
}
