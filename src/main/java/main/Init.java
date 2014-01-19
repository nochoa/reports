/**
 * 
 */
package main;

import helper.FreeMarkerHelper;
import helper.ReportHelper.Type;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nathalia Ochoa
 * 
 * @since 1.0
 * @version 1.0 Dec 27, 2013
 */
public class Init {
	public static void main(String[] args) {

		FreeMarkerHelper.generateReportHelper();
		FreeMarkerHelper.generateReport("Producto");
		buildViewReport();

		// String URL = "jdbc:postgresql://localhost:5432/report";
		// PostgreSQLHelper connection = new PostgreSQLHelper(URL, "postgres",
		// "santi");
		// ResultSet resultSet = connection.getData("select * from persona");
		// formatEstructure(resultSet);
		// formatData(resultSet);

	}

	private static void buildViewReport() {
		Display display = new Display();

		final Shell shell = new Shell(display);
		shell.setText("Generacion de reportes");
		shell.setSize(300, 200);

		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();

		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;

		shell.setLocation(x, y);

		// Agregamos dos columnas a la ventana
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		// Gneracion del reporte en formato PDF
		Button generarPDF = new Button(shell, SWT.PUSH);
		generarPDF.setText("Generar PDF");

		Listener listenerPDF = new Listener() {
			public void handleEvent(Event event) {
				Prueba simpleReportOne = new Prueba();
				try {
					simpleReportOne.generateReport(Type.pdf);
					System.out
							.println("El reporte en formato PDF fue generado exitosamente");

				} catch (Exception e) {
					System.out
							.println("No se pudo generar el reporte en formato XLS");
				}
			}
		};
		generarPDF.addListener(SWT.Selection, listenerPDF);

		// Gneracion del reporte en formato XLS
		Button generarXLS = new Button(shell, SWT.PUSH);
		generarXLS.setText("Generar XLS");

		Listener listenerXLS = new Listener() {
			public void handleEvent(Event event) {

				Prueba simpleReportOne = new Prueba();
				try {
					simpleReportOne.generateReport(Type.xls);
					System.out
							.println("El reporte en formato XLS fue generado exitosamente");
				} catch (Exception e) {
					System.out
							.println("No se pudo generar el reporte en formato XLS");
				}
			}
		};

		generarXLS.addListener(SWT.Selection, listenerXLS);

		// Cancelar la operacion
		Button cancelar = new Button(shell, SWT.PUSH);
		cancelar.setText("Cancelar");

		Listener listenerCancelar = new Listener() {
			public void handleEvent(Event event) {
				shell.close();
			}
		};

		cancelar.addListener(SWT.Selection, listenerCancelar);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public static void formatData(ResultSet list) {

		try {
			while (list.next()) {

				System.out.println(list.getString(1) + "        "
						+ list.getString(2) + "        " + list.getString(3));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void formatEstructure(ResultSet list) {
		ResultSetMetaData aRet;
		try {
			aRet = list.getMetaData();
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= aRet.getColumnCount(); i++) {
				sb.append(aRet.getColumnName(i) + "("
						+ aRet.getColumnTypeName(i) + ") ");
			}
			System.out.println(sb.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
