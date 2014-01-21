/**
 * 
 */
package helper;

import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.commons.beanutils.PropertyUtils;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.ReflectiveReportBuilder;

/**
 * 
 * Clase utilizada para la generación de reportes.
 * 
 * @author Nathalia Ochoa
 * 
 * @since 1.0
 * @version 1.0 Dec 27, 2013
 */
public abstract class ReportHelper {
	public static final String DIRECTORY_GENERATION_REPORT = "/home/nochoa/devel/generaciones/generality/";
	protected JasperPrint jp;
	protected Map<String, Object> params = new HashMap<String, Object>();
	protected DynamicReport dr;

	/**
	 * Define los tipos de reportes permitidos
	 * 
	 * @author Nathalia Ochoa
	 * 
	 */
	public enum Type {
		pdf(".pdf"), xls(".xls");
		private String extension;

		Type(String value) {
			this.extension = value;
		}

		public String getExtension() {
			return this.extension;
		}

	}

	/**
	 * Define el nombre del reporte que sera generado.
	 * 
	 * @return "Reporte" - fecha de la generacion
	 */
	public String getNameReport() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd-MM-yyyy");
		return "Reporte - " + sdf.format(new Date());
	};

	/**
	 * Recorre las propiedades de la entidad y solo considera aquellos atributos
	 * simples, sin relacion alguna y que no sea el id.
	 * 
	 * @return Lista de columnas a visualizar en el reporte
	 */
	public String[] getColumns() {
		final Object item = getData().iterator().next();
		PropertyDescriptor[] properties = PropertyUtils
				.getPropertyDescriptors(item);
		List<String> columns = new ArrayList<String>();

		for (int i = 0; i < properties.length; i++) {
			String name = properties[i].getName();
			if (isValidProperty(properties[i])) {
				columns.add(name);
			}

		}

		return columns.toArray(new String[0]);
	}

	/**
	 * Verifica si una propiedad de la entidad es valida, esto es, si no es el
	 * identidicador, ni el atributo ni una propiedad compuesta.
	 * 
	 * @param _property
	 *            Propiedad a analizar
	 * @return
	 */
	private static boolean isValidProperty(final PropertyDescriptor _property) {
		String name = _property.getName();
		return !"class".equals(name) && !"id".equals(name)
				&& isValidPropertyClass(_property);
	}

	/**
	 * Verifica si el tipo de dato de la propiedad es valido para ser incluido
	 * en el reporte.
	 * 
	 * @param _property
	 *            Propiedad a analizar.
	 * @return true si la propiedad es valida.
	 */
	private static boolean isValidPropertyClass(
			final PropertyDescriptor _property) {
		@SuppressWarnings("rawtypes")
		final Class type = _property.getPropertyType();
		return Number.class.isAssignableFrom(type) || type == String.class
				|| Date.class.isAssignableFrom(type) || type == Boolean.class;
	}

	public abstract List<?> getData();

	/**
	 * Construye un reporte simple, basandose en los atributos simples de la
	 * entidad de la lista de elementos.
	 * 
	 * @return Reporte configurado correctamente.
	 * @throws Exception
	 */
	public DynamicReport buildReport() throws Exception {

		return new ReflectiveReportBuilder(getData(), getColumns()).build();
	};

	protected ClassicLayoutManager getLayoutManager() {
		return new ClassicLayoutManager();
	}

	/**
	 * Genera un determinado reporte, cosiderando el tipo del mismo.
	 * 
	 * @param type
	 *            Tipo del reporte que se desea generar.
	 * @throws Exception
	 */
	protected void exportReport(Type type) throws Exception {

		final String path = DIRECTORY_GENERATION_REPORT + getNameReport();

		if (type.equals(Type.pdf)) {
			exportReportPdf(path);
		} else {
			exportReportXls(path);
		}
	}

	/**
	 * Genera un reporte en formato PDF.
	 * 
	 * @param path
	 *            Ubicacion donde se generaran los reportes.
	 * @throws Exception
	 */
	protected void exportReportPdf(String path) throws Exception {
		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path
				+ Type.pdf.extension);
		exporter.exportReport();

	}

	/**
	 * Genera un reporte en formato XLS.
	 * 
	 * @param path
	 *            Ubicacion donde se generaran los reportes.
	 * @throws Exception
	 */
	protected void exportReportXls(String path) throws Exception {

		JRXlsExporter exporter = new JRXlsExporter();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path
				+ Type.xls.extension);

		// Excel specific parameter
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
				Boolean.FALSE);
		exporter.setParameter(
				JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
				Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
				Boolean.FALSE);
		exporter.exportReport();

	}

	/**
	 * Metodo que configura el dataSource y los parametros del reporte a
	 * generar.
	 * 
	 * @param type
	 *            Tipo de reporte que se desea generar.
	 * @throws Exception
	 */
	public void generateReport(Type type) throws Exception {

		dr = buildReport();

		/**
		 * Ontenemos la fuente de datos en base a una colleccion de objetos
		 */
		JRDataSource ds = new JRBeanCollectionDataSource(getData());

		/**
		 * Creamos el objeto que imprimiremos pasando como parametro el
		 * JasperReport object, y el JRDataSource
		 */
		// Cambiar por la condicion de los parametros
		if (params != null) {
			jp = DynamicJasperHelper.generateJasperPrint(dr,
					getLayoutManager(), ds);
		} else {
			jp = DynamicJasperHelper.generateJasperPrint(dr,
					getLayoutManager(), ds, params);
		}

		exportReport(type);

	}
}
