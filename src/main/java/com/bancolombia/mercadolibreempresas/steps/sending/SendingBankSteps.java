package com.bancolombia.mercadolibreempresas.steps.sending;

import java.util.List;

import org.fluentlenium.core.annotation.Page;

import com.bancolombia.mercadolibreempresas.pageobjects.sending.SecondScreenPage;
import com.bancolombia.mercadolibreempresas.utilities.Util;
import com.bancolombia.mercadolibreempresas.utilities.database.CibBeneficiaryAccountsQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.CurrencyQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.RatesQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.SendingQueries;
import com.bancolombia.mercadolibreempresas.utilities.database.commission.SimonCommissionQueries;

public class SendingBankSteps {

	@Page
	private SecondScreenPage secondScreenPage;

	private SimonCommissionQueries commissionQueries;
	private RatesQueries rateQueries;
	private SendingQueries sendingQueries;
	private CibBeneficiaryAccountsQueries cibBeneficiaryAccountsQueries;
	private CurrencyQueries currencyQueries;

	private static final String CONCAT_CORRESPONDANT = "X";
	private static final String ANY_CITY = "WAKANDA";

	public void setBeneficiaryBankAsSubsidiary(String bconocomNum) {
		String beneficiaryAcc = secondScreenPage.getFirstBeneficiaryAccount();

		setBeneficiaryBankAsSubsidiaryForSending(bconocomNum, beneficiaryAcc);
	}

	public void setBeneficiaryBankAsSubsidiaryForSending(String bconocomNum, String beneficiaryAccount) {
		commissionQueries = new SimonCommissionQueries();
		sendingQueries = new SendingQueries();
		cibBeneficiaryAccountsQueries = new CibBeneficiaryAccountsQueries();
		currencyQueries = new CurrencyQueries();

		String[] beneficiaryInfo = beneficiaryAccount.split("-");
		String beneficiaryName = beneficiaryInfo[1];

		String codeSwiftBenef = commissionQueries.searchSwiftCodeBeneficiary(beneficiaryName,
				"Consulta codigo swift del beneficiario");

		commissionQueries.updateBankNoCommission(bconocomNum, codeSwiftBenef, "Actualizacion codigo swift como filial");

		String InfoInscriptedAccount[] = cibBeneficiaryAccountsQueries
				.searchActiveAccountBeneficiaryByUser(beneficiaryName);

		String codswiftXAdded = codeSwiftBenef.substring(0, 8) + CONCAT_CORRESPONDANT
				+ codeSwiftBenef.substring(8, codeSwiftBenef.length());

		String[] correspondantBank = sendingQueries.searchCorrespondantBankBySwiftCodeAndCurrency(codswiftXAdded,
				InfoInscriptedAccount[1]);

		if (correspondantBank[0] == null) {
			String codbco = String.valueOf(Util.randomNumber(4));
			String subcodbco = String.valueOf(Util.randomNumber(3));

			String[] infoBankBiccd = sendingQueries.searchInfoBankBySwiftCode(codeSwiftBenef);
			String bankName = infoBankBiccd[1].trim();
			String city = ANY_CITY;

			String[] countryInfo = currencyQueries.getAllInfoCountryCurrencyByCountry(InfoInscriptedAccount[0]);
			String country = countryInfo[1];
			String currency = InfoInscriptedAccount[1];

			sendingQueries.insertCorrespondantBank(codbco, subcodbco, codswiftXAdded, bankName, city, country,
					currency);
		}
	}

	public void setHomologatedBankAsSubsidiaryByCurrency(String currency, String desiredBconocomNum) {
		String beneficiaryAcc = secondScreenPage.getFirstBeneficiaryAccount();
		setHomologatedBankAsSubsidiaryByCurrencyForSending(currency, desiredBconocomNum, beneficiaryAcc);
	}

	public void setHomologatedBankAsSubsidiaryByCurrencyForSending(String currency, String desiredBconocomNum,
			String beneficiaryAcc) {
		rateQueries = new RatesQueries();
		commissionQueries = new SimonCommissionQueries();
		sendingQueries = new SendingQueries();

		String[] beneficiaryInfo = beneficiaryAcc.split("-");
		String beneficiaryName = beneficiaryInfo[1];

		String codeSwiftBenef = commissionQueries.searchSwiftCodeBeneficiary(beneficiaryName,
				"Consulta codigo swift del beneficiario");

		commissionQueries.removeBankNoCommissionBySwiftCode("XXXXXXX", codeSwiftBenef,
				"Removiendo codigo swift como filial");

		List<List<String>> allRatesByCurrency = rateQueries.searchAllTasas(currency);

		String wayOfPayment = allRatesByCurrency.get(0).get(7);

		String[] homologatedBankInfo = sendingQueries.searchHomologatedBankInfo(wayOfPayment.trim());

		String[] correspondantBank = sendingQueries.searchCorrespondantBankByCodeBankAndSubCode(homologatedBankInfo[0],
				homologatedBankInfo[1]);

		if (correspondantBank[0] != null) {
			String swiftCodeHomologatedBank = correspondantBank[2];

			String swiftCodeRemovedX = swiftCodeHomologatedBank.substring(0, 8) + swiftCodeHomologatedBank.substring(9);

			commissionQueries.updateBankNoCommission(desiredBconocomNum, swiftCodeRemovedX, "Modificando banco filial");
		}

	}

	public void setHomologatedBankAsSendingBank(String currency, String bconocomNum) {
		String beneficiaryAcc = secondScreenPage.getFirstBeneficiaryAccount();
		setHomologatedBankAsSubsidiaryByCurrencyForSending(currency, bconocomNum, beneficiaryAcc);
	}

	public void setHomologatedBankAsSendingBankForSending(String currency, String bconocomNum, String beneficiaryAcc) {

		rateQueries = new RatesQueries();
		commissionQueries = new SimonCommissionQueries();
		sendingQueries = new SendingQueries();

		String[] beneficiaryInfo = beneficiaryAcc.split("-");
		String beneficiaryName = beneficiaryInfo[1];

		String codeSwiftBenef = commissionQueries.searchSwiftCodeBeneficiary(beneficiaryName,
				"Consulta codigo swift del beneficiario");

		commissionQueries.removeBankNoCommissionBySwiftCode("XXXXXXX", codeSwiftBenef,
				"Removiendo codigo swift como filial");

		commissionQueries.searchBankNoCommission("Bancos filiales registrados");

		List<List<String>> allRatesByCurrency = rateQueries.searchAllTasas(currency);

		String wayOfPayment = allRatesByCurrency.get(0).get(7);

		String[] homologatedBankInfo = sendingQueries.searchHomologatedBankInfo(wayOfPayment);

		String[] correspondantBank = sendingQueries.searchCorrespondantBankByCodeBankAndSubCode(homologatedBankInfo[0],
				homologatedBankInfo[1]);

		if (correspondantBank[0] != null) {
			String swiftCodeHomologatedBank = correspondantBank[2];

			String swiftCodeRemovedX = swiftCodeHomologatedBank.substring(0, 8) + swiftCodeHomologatedBank.substring(9);

			commissionQueries.removeBankNoCommissionBySwiftCode("XXXXXXX", swiftCodeRemovedX,
					"Removiendo codigo swift como filial");
		}
	}

}
