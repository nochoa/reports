<#-- Template que genera la vista para el reporte -->
package ${namePackage};

import ${namePackageHelper}.${nameHelper}.Type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public ${typeFile} ${nameFile} {
	public static void main(String[] args) {

		
		buildViewReport();

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
				${nameClassReport} simpleReportOne = new ${nameClassReport}();
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

				${nameClassReport} simpleReportOne = new ${nameClassReport}();
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
}
