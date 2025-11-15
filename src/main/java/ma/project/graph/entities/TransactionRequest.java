package ma.project.graph.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionRequest {

    private double montant;

    private String type; // "DEPOT" ou "RETRAIT"

    private LocalDateTime date; // date de la transaction

    private Long compteId; // identifiant du compte

    public TransactionRequest() {
        this.date = LocalDateTime.now();
    }

    public TransactionRequest(double montant, String type, LocalDateTime date, Long compteId) {
        this.montant = montant;
        this.type = type;
        this.date = date;
        this.compteId = compteId;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getCompteId() {
        return compteId;
    }

    public void setCompteId(Long compteId) {
        this.compteId = compteId;
    }
}
