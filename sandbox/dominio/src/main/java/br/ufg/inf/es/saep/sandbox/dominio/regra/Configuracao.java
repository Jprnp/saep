/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.es.saep.sandbox.dominio.regra;

import br.ufg.inf.es.saep.sandbox.dominio.Entidade;
import br.ufg.inf.es.saep.sandbox.dominio.excecoes.CampoExigidoNaoFornecido;

import java.util.Date;
import java.util.List;

/**
 * Reúne regras para avaliação de processos
 * de promoção, progressão e estágio probatório.
 *
 * A legislação da Universidade Federal de Goiás (UFG)
 * está organizada por meio de resoluções. Uma instância dessa
 * classe simplesmente registra os itens relevantes ou aqueles
 * considerados em uma avaliação.
 */
public class Configuracao extends Entidade {

    /**
     * Data da configuração de regras.
     */
    private Date data;

    /**
     * Identificador da configuração.
     * Ou seja, é empregado aqui como um "nome de fantasia".
     * Contraste com {@link #id}.
     */
    private String nome;

    /**
     * Descrição ou informação adicional sobre
     * a configuração.
     */
    private String descricao;

    /**
     * Conjunto de regras definido pela configuração.
     */
    private List<Regra> regras;

    /**
     * Recupera o nome da configuração.
     *
     * @return O nome da configuração.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Recupera a descrição da configuração.
     *
     * @return A descrição da configuração.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Recupera a data de criação da configuração.
     *
     * @return Data de criação da resolução.
     */
    public Date getData() {
        return data;
    }

    /**
     * Recupera o conjunto de regras definido
     * pela configuração.
     *
     * @return Conjunto de regras definido pela configuração.
     */
    public List<Regra> getRegras() {
        return regras;
    }

    /**
     * Cria uma configuração.
     * @param id O identificador único da configuração.
     * @param nome O nome pelo qual seres humanos identificam a configuração.
     * @param descricao A descrição da configuração.
     * @param data A data de criação da configuração.
     * @param regras Conjunto de regras que definem a configuração.
     */
    public Configuracao(String id, String nome, String descricao, Date data, List<Regra> regras) {
        super(id);

        if (descricao == null || descricao.isEmpty()) {
            throw new CampoExigidoNaoFornecido("descricao");
        }

        if (data == null) {
            throw new CampoExigidoNaoFornecido("data");
        }

        if (regras == null || regras.size() < 1) {
            throw new CampoExigidoNaoFornecido("regras");
        }

        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.regras = regras;
    }
}