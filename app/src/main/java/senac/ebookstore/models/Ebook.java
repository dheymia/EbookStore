package senac.ebookstore.models;

public class Ebook {

    private String isbn;
    private String imageUrl;
    private String titulo;
    private String autor;
    private String sinopse;
    private String tipo;
    private String url;

    public Ebook() {
    }

    public Ebook(String isbn, String imageUrl, String titulo, String autor, String sinopse, String tipo, String url) {
        this.isbn = isbn;
        this.imageUrl = imageUrl;
        this.titulo = titulo;
        this.autor = autor;
        this.sinopse = sinopse;
        this.tipo = tipo;
        this.url = url;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUrl() {
        return url;
    }
}
