package com.bancolombia.mercadolibreempresas.testqueries.verifications;

import org.junit.Before;
import org.junit.Test;

import com.bancolombia.mercadolibreempresas.steps.sending.SendingFilesSteps;

public class TestTriggerValidateFile {

	private static SendingFilesSteps sendingFilesSteps;

	@Before
	public void init() {
		sendingFilesSteps = new SendingFilesSteps();
	}

	@Test
	public void testTriggerValidateCibffmov() {
		sendingFilesSteps.validateFileCibffmov("25060002313", "3550.00");
	}

	@Test
	public void testTriggerValidateCibdccns() {
		sendingFilesSteps.validateFileCibffdccns("25060002313", "20180705", "yyyyMMdd");
	}

	@Test
	public void testTriggerValidateCdcffpagir() {
		sendingFilesSteps.validateFileCibffpagir("25060002313", "USD", "20180705", "3550");
	}

}
