package ${namePackage};

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

public abstract ${typeFile} ${nameClass} {
	private static final String PATH = "${directoryGenerationReport}";
	protected JasperPrint jp;
	protected Map<String, Object> params = new HashMap<String, Object>();
	protected DynamicReport dr;
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

	public String getNameReport(){
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd-MM-yyyy");
		return "Reporte - " + sdf.format(new Date());
	};
	/**
	 * Solo considera aquellos atributos simple, sin relacion alguna.
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

	public DynamicReport buildReport() throws Exception {
		return new ReflectiveReportBuilder(getData(), getColumns()).build();
	};

	protected ClassicLayoutManager getLayoutManager() {
		return new ClassicLayoutManager();
	}

	protected void exportReport(Type type) throws Exception {

		final String path = PATH + getNameReport();

		if (type.equals(Type.pdf)) {
			exportReportPdf(path);
		} else {
			exportReportXls(path);
		}
	}

	protected void exportReportPdf(String path) throws Exception {
		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path
				+ Type.pdf.extension);
		exporter.exportReport();

	}

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

