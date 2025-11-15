package ma.project.graph.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double montant;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTransaction;

    @Enumerated(EnumType.STRING)
    private TypeTransaction type; // DEPOT ou RETRAIT

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte; // 🔗 Relation ManyToOne

    public Transaction() {}

    public Transaction(double montant, LocalDateTime  dateTransaction, TypeTransaction type, Compte compte) {
        this.montant = montant;
        this.dateTransaction = dateTransaction;
        this.type = type;
        this.compte = compte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDateTime dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public TypeTransaction getType() {
        return type;
    }

    public void setType(TypeTransaction type) {
        this.type = type;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }
}
