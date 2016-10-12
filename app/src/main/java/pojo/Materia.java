package pojo;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by felipe on 24/09/16.
 */

public class Materia {

    private int codigo;
    private String descricao;
    private boolean ativo;
    private String professor;
    private int[] questoes_favoritas;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int[] getQuestoes_favoritas() {
        return questoes_favoritas;
    }

    public void setQuestoes_favoritas(int[] questoes_favoritas) {
        this.questoes_favoritas = questoes_favoritas;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "ativo=" + ativo +
                ", codigo=" + codigo +
                ", descricao='" + descricao + '\'' +
                ", professor='" + professor + '\'' +
                ", questoes_favoritas=" + Arrays.toString(questoes_favoritas) +
                '}';
    }
}
