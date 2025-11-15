package ma.project.graph.controllers;

import ma.project.graph.entities.Compte;
import ma.project.graph.entities.Transaction;
import ma.project.graph.entities.TransactionRequest;
import ma.project.graph.entities.TypeTransaction;
import ma.project.graph.repositories.CompteRepository;
import ma.project.graph.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class CompteControllerGraphQL {

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @QueryMapping
    public List<Compte> allComptes() {
        return compteRepository.findAll();
    }

    @QueryMapping
    public Compte compteById(@Argument Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte " + id + " not found"));
    }

    @MutationMapping
    public Compte saveCompte(@Argument Compte compte) {
        return compteRepository.save(compte);
    }

    @QueryMapping
    public Map<String, Object> totalSolde() {
        long count = compteRepository.count();
        double sum = compteRepository.sumSoldes();
        double average = count > 0 ? sum / count : 0;

        return Map.of(
                "count", count,
                "sum", sum,
                "average", average
        );
    }
    @MutationMapping
    public Transaction addTransaction(@Argument TransactionRequest transactionRequest) {

        // Vérification du compte
        Compte compte = compteRepository.findById(transactionRequest.getCompteId())
                .orElseThrow(() -> new RuntimeException("Compte not found"));

        // Création de la transaction
        Transaction transaction = new Transaction();
        transaction.setMontant(transactionRequest.getMontant());
        transaction.setDateTransaction(transactionRequest.getDate());
        transaction.setType(transactionRequest.getType().equalsIgnoreCase("DEPOT")
                ? TypeTransaction.DEPOT
                : TypeTransaction.RETRAIT);
        transaction.setCompte(compte);

        // Mise à jour du solde
        if (transaction.getType() == TypeTransaction.DEPOT) {
            compte.setSolde(compte.getSolde() + transaction.getMontant());
        } else if (transaction.getType() == TypeTransaction.RETRAIT) {
            if (compte.getSolde() < transaction.getMontant()) {
                throw new RuntimeException("Solde insuffisant !");
            }
            compte.setSolde(compte.getSolde() - transaction.getMontant());
        }

        compteRepository.save(compte);
        return transactionRepository.save(transaction);
    }
}
