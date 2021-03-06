/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.es.saep.sandbox.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Encapsula um valor, que pode ser uma sequência
 * de caracteres, um {@code boolean}, um {@code float}
 * ou uma data ({@code LocalDate}).
 *
 * <p>Um relato é descrito por uma coleção de
 * valores, por exemplo, um relato correspondente
 * a um "livro" pode ter atributos como "titulo"
 * e "numeroPaginas", dentre outros. Um valor
 * correspondente para "titulo" de um dado relato
 * pode ser "Amar e ser livre", enquanto o valor
 * para "numeroPaginas" pode ser 209, por exemplo.
 *
 * <p>Uma instância dessa classe é empregada para
 * reter qualquer um desses valores. A recuperação
 * do valor depende do uso do método <b>get</b>
 * correspondente ao tipo. Cabe a quem envia uma
 * mensagem para uma instância de valor fazer
 * uso do método correto.
 */
public class Valor {

    /**
     * Formato de data empregado quando fornecida em
     * uma sequência de caracteres.
     */
    public static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Contêiner para o valor numérico
     * da instância.
     */
    private float real;

    /**
     * Contêiner para o valor lógico
     * da instância.
     */
    private boolean logico;

    /**
     * Contêiner para a sequência de caracteres
     * mantida pela instância.
     */
    private String string;

    /**
     * Contêiner para a data mantida
     * pela instância.
     */
    private LocalDate data;

    /**
     * Cria uma instância cujo valor é
     * uma sequência de caracteres.
     *
     * @param valor Sequência de caracteres
     *              do valor.
     */
    public Valor(final String valor) {
        this.string = valor;
    }

    /**
     * Cria uma instância cujo valor é
     * um número real.
     *
     * @param valor Número real correspondente
     *              ao valor.
     */
    public Valor(final float valor) {
        real = valor;
    }

    /**
     * Cria uma instância cujo valor é
     * lógico.
     *
     * @param valor Valor lógico a ser
     *              retido pela instância.
     */
    public Valor(final boolean valor) {
        logico = valor;
    }

    /**
     * Cria uma instância cujo valor é uma data
     * no formato (dd/MM/aaaa).
     *
     * @param umaData Data a ser associada ao valor.
     */
    public Valor(final LocalDate umaData) {
        this.data = umaData;
    }

    /**
     * Recupera a sequência de caracteres
     * do valor.
     *
     * @return Sequência de caracteres da
     *      instância.
     */
    public final String getString() {
        return string;
    }

    /**
     * Recupera o valor lógico correspondente
     * à instância.
     *
     * @return O valor {@code true} ou
     *      {@code false} correspondente à
     *      instância.
     */
    public final boolean getBoolean() {
        return logico;
    }

    /**
     * Recupera o valor real (numérico)
     * armazenado na instância.
     *
     * @return O valor numérico correspondente
     *      à instância.
     */
    public final float getReal() {
        return real;
    }

    /**
     * Recupera o valor da data armazenado
     * na instância.
     *
     * @return A data armazenada na instância.
     */
    public final LocalDate getData() {
        return data;
    }

    /**
     * Cria uma instância cujo valor armazenado é
     * a data fornecida por meio de uma sequência de
     * caracteres.
     *
     * @param data Sequência de caracteres no formato
     *             dd/MM/aaaa.
     *
     * @return Instância correspondente à data fornecida
     * como sequência de caracteres.
     */
    public static Valor dataFromString(final String data) {
        LocalDate parsedDate;

        try {
            parsedDate = LocalDate.parse(data, FORMATO_DATA);
        } catch (DateTimeParseException exp) {
            parsedDate = LocalDate.now();
        }

        return new Valor(parsedDate);
    }
}
