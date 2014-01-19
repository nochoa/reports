package util;

public enum FileGeneration {
	CLASS("class", ".java"), INTERFACE("interface", ".CLASE");
	private String type;
	private String extension;

	private FileGeneration(String type, String extension) {
		this.type = type;
		this.extension = extension;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
