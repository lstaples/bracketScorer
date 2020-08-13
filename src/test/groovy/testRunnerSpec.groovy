import spock.lang.Specification
import staples.bracketScorer.importer.BracketImporter

class testRunnerSpec extends Specification{
    //too lazy to write real tests.  so this is a runner to eyeball results

    def" does this fucking look right to you?"(){
        when:
        def brackets = new BracketImporter().importBrackets()
        brackets.each{println(it.print())}
        then: println("end of outputt")
    }
}
