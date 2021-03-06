/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package br.ufg.inf.es.saep.sandbox.persistencia.gds;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.google.cloud.datastore.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do repositório de resoluções usando
 * Google Datastore (NoSQL).
 *
 */
public class ResolucaoRepositoryGoogleDatastore implements ResolucaoRepository {

    private String RESOLUCAO_KIND = "RESOLUCAO";
    private String TIPO_KIND = "TIPO";
    private Datastore gds;
    private final String PAYLOAD = "objeto";

    /**
     * Define o objeto por meio do qual acesso aos serviços do
     * Google Datastore serão usufruídos.
     *
     * @param ds Objeto de acesso ao Google Datastore.
     */
    public void setDatastore(Datastore ds) {
        gds = ds;
    }

    private Key getKeyResolucao(String id) {
        // TODO Verificar necessidade de cache para factory.
        return gds.newKeyFactory().kind(RESOLUCAO_KIND).newKey(id);
    }

    private Key getKeyTipo(String id) {
        return gds.newKeyFactory().kind(TIPO_KIND).newKey(id);
    }

    @Override
    public Resolucao byId(String id) {
        try {
            Entity dsr = gds.get(getKeyResolucao(id));

            if (dsr == null) {
                return null;
            }

            String objeto = dsr.getString(PAYLOAD);

            Type tipo = new TypeToken<Resolucao>(){}.getType();
            return (Resolucao)new Gson().fromJson(objeto, tipo);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String persiste(Resolucao resolucao) {
        try {
            Key key = getKeyResolucao(resolucao.getId());
            String json = new Gson().toJson(resolucao);

            gds.put(Entity.builder(key).set(PAYLOAD, json).build());

            return resolucao.getId();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean remove(String s) {
        try {
            gds.delete(getKeyResolucao(s));
            return true;
        } catch (Exception exp) {
            return false;
        }
    }

    @Override
    public List<String> resolucoes() {
        Query<Key> all = Query.keyQueryBuilder().kind(RESOLUCAO_KIND).build();
        QueryResults<Key> resultados = gds.run(all);

        List<String> retorno = new ArrayList<>();
        while (resultados.hasNext()) {
            retorno.add(resultados.next().name());
        }

        return retorno;
    }

    @Override
    public void persisteTipo(Tipo tipo) {
        try {
            Key key = getKeyTipo(tipo.getId());
            String json = new Gson().toJson(tipo);

            gds.put(Entity.builder(key).set(PAYLOAD, json).build());
        } catch (Exception e) {
            // TODO Nada definido aqui, contrato incompleto!!!
        }
    }

    @Override
    public void removeTipo(String s) {
        try {
            gds.delete(getKeyTipo(s));
        } catch (Exception exp) {
            // TODO Nada definido aqui, contrato incompleto!!!
        }
    }

    @Override
    public Tipo tipoPeloCodigo(String s) {
        try {
            Entity dsr = gds.get(getKeyTipo(s));

            if (dsr == null) {
                return null;
            }

            String objeto = dsr.getString(PAYLOAD);

            Type tipo = new TypeToken<Tipo>(){}.getType();
            return (Tipo)new Gson().fromJson(objeto, tipo);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Tipo> tiposPeloNome(String s) {
        Query<Entity> all = Query.entityQueryBuilder().kind(TIPO_KIND).build();
        QueryResults<Entity> resultados = gds.run(all);

        List<Tipo> tipos = new ArrayList<>();
        while (resultados.hasNext()) {
            Entity entidade = resultados.next();
            if (entidade.key().name().contains(s)) {
                Type tipo = new TypeToken<Tipo>(){}.getType();
                String objeto = entidade.getString(PAYLOAD);
                tipos.add(new Gson().fromJson(objeto, tipo));
            }
        }

        return tipos;
    }
}
