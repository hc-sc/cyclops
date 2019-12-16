package ca.gc.hc.nhpos

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import groovy.lang.GroovyShell

// In groovy every class is public by default
// so you don't need to write "public class" anymore
@SpringBootApplication
class Application {

    // Every method is public as well
    static void main(String[] args) {

        // You can omit the last method call parenthesis
        // This is the same as .run(Application, args)
        // also you can omit ;
        //SpringApplication.run Application, args
        
        def shell = new GroovyShell();
        
        def report = new Report()
        def currentDatetime = report.currentDatetime()

        new File( "database/SDV_20191128/" + currentDatetime + ".log" ).write("a", "UTF-8" );
        
         
    }
}