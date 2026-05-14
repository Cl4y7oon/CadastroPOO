package cadastropoo;

import java.util.Scanner;
import model.PessoaFisica;
import model.PessoaFisicaRepo;
import model.PessoaJuridica;
import model.PessoaJuridicaRepo;

public class CadastroPOO {

    private static final Scanner ENTRADA = new Scanner(System.in);
    private static final PessoaFisicaRepo REPO_FISICA = new PessoaFisicaRepo();
    private static final PessoaJuridicaRepo REPO_JURIDICA = new PessoaJuridicaRepo();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Opcao: ");

            switch (opcao) {
                case 1:
                    incluir();
                    break;
                case 2:
                    alterar();
                    break;
                case 3:
                    excluir();
                    break;
                case 4:
                    exibirPorId();
                    break;
                case 5:
                    exibirTodos();
                    break;
                case 6:
                    salvar();
                    break;
                case 7:
                    recuperar();
                    break;
                case 0:
                    System.out.println("Sistema finalizado.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
                    break;
            }
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("1 - Incluir");
        System.out.println("2 - Alterar");
        System.out.println("3 - Excluir");
        System.out.println("4 - Exibir pelo id");
        System.out.println("5 - Exibir todos");
        System.out.println("6 - Salvar dados");
        System.out.println("7 - Recuperar dados");
        System.out.println("0 - Finalizar");
    }

    private static void incluir() {
        String tipo = lerTipo();
        if (tipo.equalsIgnoreCase("F")) {
            REPO_FISICA.inserir(lerPessoaFisica());
        } else {
            REPO_JURIDICA.inserir(lerPessoaJuridica());
        }
    }

    private static void alterar() {
        String tipo = lerTipo();
        int id = lerInteiro("ID: ");

        if (tipo.equalsIgnoreCase("F")) {
            PessoaFisica pessoaFisica = REPO_FISICA.obter(id);
            if (pessoaFisica != null) {
                pessoaFisica.exibir();
                REPO_FISICA.alterar(lerPessoaFisicaComId(id));
            } else {
                System.out.println("Pessoa fisica nao encontrada.");
            }
        } else {
            PessoaJuridica pessoaJuridica = REPO_JURIDICA.obter(id);
            if (pessoaJuridica != null) {
                pessoaJuridica.exibir();
                REPO_JURIDICA.alterar(lerPessoaJuridicaComId(id));
            } else {
                System.out.println("Pessoa juridica nao encontrada.");
            }
        }
    }

    private static void excluir() {
        String tipo = lerTipo();
        int id = lerInteiro("ID: ");

        if (tipo.equalsIgnoreCase("F")) {
            REPO_FISICA.excluir(id);
        } else {
            REPO_JURIDICA.excluir(id);
        }
    }

    private static void exibirPorId() {
        String tipo = lerTipo();
        int id = lerInteiro("ID: ");

        if (tipo.equalsIgnoreCase("F")) {
            PessoaFisica pessoaFisica = REPO_FISICA.obter(id);
            if (pessoaFisica != null) {
                pessoaFisica.exibir();
            } else {
                System.out.println("Pessoa fisica nao encontrada.");
            }
        } else {
            PessoaJuridica pessoaJuridica = REPO_JURIDICA.obter(id);
            if (pessoaJuridica != null) {
                pessoaJuridica.exibir();
            } else {
                System.out.println("Pessoa juridica nao encontrada.");
            }
        }
    }

    private static void exibirTodos() {
        String tipo = lerTipo();

        if (tipo.equalsIgnoreCase("F")) {
            for (PessoaFisica pessoaFisica : REPO_FISICA.obterTodos()) {
                pessoaFisica.exibir();
                System.out.println();
            }
        } else {
            for (PessoaJuridica pessoaJuridica : REPO_JURIDICA.obterTodos()) {
                pessoaJuridica.exibir();
                System.out.println();
            }
        }
    }

    private static void salvar() {
        System.out.print("Prefixo dos arquivos: ");
        String prefixo = ENTRADA.nextLine();

        try {
            REPO_FISICA.persistir(prefixo + ".fisica.bin");
            REPO_JURIDICA.persistir(prefixo + ".juridica.bin");
            System.out.println("Dados salvos com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    private static void recuperar() {
        System.out.print("Prefixo dos arquivos: ");
        String prefixo = ENTRADA.nextLine();

        try {
            REPO_FISICA.recuperar(prefixo + ".fisica.bin");
            REPO_JURIDICA.recuperar(prefixo + ".juridica.bin");
            System.out.println("Dados recuperados com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao recuperar dados: " + e.getMessage());
        }
    }

    private static PessoaFisica lerPessoaFisica() {
        int id = lerInteiro("ID: ");
        return lerPessoaFisicaComId(id);
    }

    private static PessoaFisica lerPessoaFisicaComId(int id) {
        System.out.print("Nome: ");
        String nome = ENTRADA.nextLine();
        System.out.print("CPF: ");
        String cpf = ENTRADA.nextLine();
        int idade = lerInteiro("Idade: ");

        return new PessoaFisica(id, nome, cpf, idade);
    }

    private static PessoaJuridica lerPessoaJuridica() {
        int id = lerInteiro("ID: ");
        return lerPessoaJuridicaComId(id);
    }

    private static PessoaJuridica lerPessoaJuridicaComId(int id) {
        System.out.print("Nome: ");
        String nome = ENTRADA.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = ENTRADA.nextLine();

        return new PessoaJuridica(id, nome, cnpj);
    }

    private static String lerTipo() {
        String tipo;
        do {
            System.out.print("Tipo (F - Fisica / J - Juridica): ");
            tipo = ENTRADA.nextLine();
        } while (!tipo.equalsIgnoreCase("F") && !tipo.equalsIgnoreCase("J"));

        return tipo;
    }

    private static int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        int valor = ENTRADA.nextInt();
        ENTRADA.nextLine();
        return valor;
    }
}
