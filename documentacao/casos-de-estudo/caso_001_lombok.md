# Lombok

#### [Instalação](https://projectlombok.org/)
#### [Documentação](https://projectlombok.org/features/all)

### Problema enfrentado no projeto:
* Código verboso e repetitivo.

### O que o Projeto Lombok consegue fazer?
* O Projeto Lombok tem diversas annotations que permitem minimizar o uso de código repetitivo.
* Anotações utilizada atualmente no projeto:
```java  
* @Getter/@Setter
```
Inicialmente usavamos a anotação, sua funcionalidade é praticamente a mesma da anotação que utilizamos atualmente mas por ela fazer algumas manipulações na classe que pra nós não é necessária e por alguns bugs encontrados, descontinuamos a utilização da anotação abaixo em nosso projeto
```java
@Data
```

Exemplo Com utilização do Lombok anotação **(@Getter @Setter)**
```java 
@ApiModel("Departamento")
@Getter @Setter
@Table
@Entity
public class Departamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@NotNull
	@Column(length=200)
	@ApiModelProperty("Nome")
	private String nome;

 }
 ```

Exemplo sem a utilização de Lombok.
```java  
@ApiModel("Departamento")
@Table
@Entity
public class Departamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty("Código")
	private Long id;
	
	@NotNull
	@Column(length=200)
	@ApiModelProperty("Nome")
	private String nome;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
```

@autor Rafael Volkmer

@bibliografia
* http://blog.caelum.com.br/java-menos-verboso-com-lombok/
* https://www.devmedia.com.br/uma-visao-sobre-o-projeto-lombok/28321
