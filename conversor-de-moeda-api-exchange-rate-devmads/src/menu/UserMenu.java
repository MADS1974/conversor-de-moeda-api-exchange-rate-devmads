package menu;

import enums.Currencies;
import service.DataHistoryService;
import service.ValueService;

import javax.swing.*;
import java.util.List;

public class UserMenu {
    private final ValueService service;

    public UserMenu() {
        this.service = new ValueService();
    }

    public void run() {
        showMessage("""
                #############################################
                #      BEM VINDO AO CONVERSOR DE MOEDA      #
                #############################################
                """);

        while (true) {
            String[] options = {"Comparar valores entre as moedas", "Exibir o histórico da sessão atual", "Sair"};
            int option = JOptionPane.showOptionDialog(null, "Escolha a opção desejada para realizar a conversão!", "Conversor de Moeda",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (option) {
                case 0:
                    compareValues();
                    break;
                case 1:
                    showHistoryLog();
                    break;
                case 2:
                    System.exit(0);
                    break;
                default:
                    showMessage("ATENÇÃO: Escolha incorreta, tente novamente");
            }
        }
    }

    private void compareValues() {
        String firstValue, secondValue, amount;
        firstValue = showInputDialog("Principais moedas: EUR, USD, CHF, CNY, JPY, CAD, BRL, CLP, ARS, EGP, ILS.\nPara mais opções, consulte a Tabela de Moedas.\nO formato esperado é XXX, conforme normas ISO 4217.\nEscolha a moeda base:");

        while (!existsByIsoCurrencyFromEnum(firstValue)) {
            firstValue = showInputDialog("Moeda não encontrada, favor insira corretamente");
        }

        secondValue = showInputDialog("Escolha a moeda alvo para converter");

        while (!existsByIsoCurrencyFromEnum(secondValue)) {
            secondValue = showInputDialog("Moeda não encontrada, favor insira corretamente");
        }

        amount = showInputDialog("Digite o valor da moeda base para calcular");

        while (!isValidValue(amount)) {
            amount = showInputDialog("Valor inválido, favor insira corretamente");
        }

        String valorCalculado = service.findValueInPairs(firstValue, secondValue, amount);
        String result = String.format("O valor após o câmbio ficou em %s.\n A moeda usada como base foi %s.\n A conversão foi para %s.\n Foram convertidos %s da moeda base.", valorCalculado, firstValue, secondValue, amount);
        saveToHistory(result);
        showMessage(result);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private String showInputDialog(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

    private boolean existsByIsoCurrencyFromEnum(String currency) {
        Currencies item = Currencies.findByISOValue(currency.toUpperCase());
        return item != null;
    }

    private boolean isValidValue(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private void saveToHistory(String message) {
        DataHistoryService.addToList(message);
    }

    private void showHistoryLog() {
        List<String> fullList = DataHistoryService.getHistoryList();

        if (fullList.isEmpty()) {
            showMessage("Não existe histórico para exibir!");
            return;
        }

        StringBuilder history = new StringBuilder();
        history.append("############## HISTÓRICO ###############\n");
        for (String item : fullList) {
            history.append(item).append("\n");
        }
        history.append("############ FIM DO HISTÓRICO ############");
        showMessage(history.toString());
    }
}
