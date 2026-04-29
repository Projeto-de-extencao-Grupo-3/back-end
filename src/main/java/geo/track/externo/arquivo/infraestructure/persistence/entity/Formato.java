package geo.track.externo.arquivo.infraestructure.persistence.entity;

public enum Formato {
    PDF, IMAGEM, OUTROS;

    public static Formato fromContentType(String contentType) {
        if (contentType == null) return OUTROS;
        if (contentType.contains("pdf")) return PDF;
        if (contentType.contains("image")) return IMAGEM;
        return OUTROS;
    }
}
