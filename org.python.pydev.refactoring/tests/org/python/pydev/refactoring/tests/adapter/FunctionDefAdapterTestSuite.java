package org.python.pydev.refactoring.tests.adapter;

import java.io.File;

import junit.framework.Test;

import org.python.pydev.refactoring.tests.core.AbstractIOTestSuite;
import org.python.pydev.refactoring.tests.core.IInputOutputTestCase;

public class FunctionDefAdapterTestSuite extends AbstractIOTestSuite {

	public static Test suite() {
		TESTDIR = "tests" + File.separator + "python" + File.separator + "adapter" + File.separator + "functiondef";
		FunctionDefAdapterTestSuite testSuite = new FunctionDefAdapterTestSuite();

		testSuite.createTests();

		return testSuite;
	}

	@Override
	protected IInputOutputTestCase createTestCase(String testCaseName) {
		return new FunctionDefAdapterTestCase(testCaseName);
	}
}
