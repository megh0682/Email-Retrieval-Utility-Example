package com.megha.junittest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRunner {

final Logger logger = (Logger)LoggerFactory.getLogger(TestRunner.class);
public static void main(String[] args) {
	final Logger logger = (Logger)LoggerFactory.getLogger(TestRunner.class);
	Result result = JUnitCore.runClasses(com.megha.junittest.TestSuite.class);
      for (Failure failure : result.getFailures()) {
    	  logger.info("Failures reported while running a test suite are {}",failure.toString());
      }
     logger.info("TestSuite result flag is {}" , result.wasSuccessful());
   }
}  	