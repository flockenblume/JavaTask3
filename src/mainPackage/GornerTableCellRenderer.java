package mainPackage;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer implements TableCellRenderer {

    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private String needle = null;

    private DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();

    public GornerTableCellRenderer() {
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        String formattedDouble = formatter.format(value);
        label.setText(formattedDouble);

        panel.setBackground(Color.WHITE);

        if (col == 1 && value instanceof Number) {
            double numericValue = ((Number) value).doubleValue();
            String strValue = formatter.format(numericValue);
            String[] parts = strValue.split("\\.");

            if (parts.length > 1) {
                String fractionalPart = parts[1];
                int sumOfDigits = 0;

                for (char digit : fractionalPart.toCharArray()) {
                    sumOfDigits += Character.getNumericValue(digit);
                }

                if (sumOfDigits % 10 == 0) {
                    panel.setBackground(Color.YELLOW);
                }
            }
        }

        if (col == 1 && needle != null && needle.equals(formattedDouble)) {
            panel.setBackground(Color.RED);
        }

        return panel;
    }

    public void setNeedle(String needle) {
        this.needle = needle;
    }
}