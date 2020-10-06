package com.bootcamp.cdd.request;

import com.bootcamp.cdd.models.Author;
import com.bootcamp.cdd.models.Book;
import com.bootcamp.cdd.models.Category;
import com.bootcamp.cdd.shared.UniqueValue;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class BookRequest {
    @NotBlank(message = "Titulo é obrigatório")
    private String titulo;
    private String sumario;
    private int quantidadePaginas;
    @NotBlank(message = "Resumo é obrigatório") @Length(min = 1, max = 500, message = "Sumario deve ter entre 1 e 500 caracteres.")
    private String resumo;
    @Min(value = 20, message = "Preço deve ser maior que 20.")
    private double preco;
    @NotBlank(message = "Isnb é obrigatório")
    private String isnb;
    private LocalDate dataPublicacao;
    private long categoryId;
    private long authorId;


    public BookRequest(@NotBlank(message = "Titulo é obrigatório") String titulo, String sumario, int quantidadePaginas, @NotBlank(message = "Resumo é obrigatório") @Length(min = 1, max = 500, message = "Sumario deve ter entre 1 e 500 caracteres.") String resumo, @Min(value = 20, message = "Preço deve ser maior que 20.") double preco, @NotBlank(message = "Isnb é obrigatório") String isnb, LocalDate dataPublicacao, long categoryId, long authorId) {
        this.titulo = titulo;
        this.sumario = sumario;
        this.quantidadePaginas = quantidadePaginas;
        this.resumo = resumo;
        this.preco = preco;
        this.isnb = isnb;
        this.dataPublicacao = dataPublicacao;
        this.categoryId = categoryId;
        this.authorId = authorId;
    }

    public Book toModel (EntityManager entityManager) {
        Author authorOptional = entityManager.find(Author.class, this.authorId);
        Category categoryOptional = entityManager.find(Category.class, this.categoryId);
        Assert.state(authorOptional != null, "O author com o id: "+authorId+" não existe no banco.");
        Assert.state(categoryOptional != null, "A categoria com o id: "+categoryId+" não existe no banco.");
        return new Book(this.titulo, this.sumario, this.quantidadePaginas, this.resumo, this.preco, this.isnb, this.dataPublicacao, authorOptional, categoryOptional);
    }
}
