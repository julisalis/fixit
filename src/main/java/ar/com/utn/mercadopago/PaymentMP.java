package ar.com.utn.mercadopago;

import ar.com.utn.mercadopago.model.UserMP;
import ar.com.utn.models.PersistentEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by julian on 22/10/17.
 */
@Entity
@Table(name = "paymentMP")
public class PaymentMP extends PersistentEntity {
    private boolean binary_mode;
    private String status;
    private String status_detail;
    private double application_fee;
    private double transaction_amount;
    private String token;
    private String description;
    private int installments;
    private String payment_method_id;
    @Embedded
    private UserMP payer;
    @Embedded
    private AdditionalInfoMP additional_info;

    public PaymentMP(){

    }

    public PaymentMP(double presupAprox, String tokenMP, String title, int installments, String paymentMethodId, UserMP userMP, AdditionalInfoMP additionalInfoMP, double commission) {
        this.transaction_amount = presupAprox;
        this.token = tokenMP;
        this.description = title;
        this.installments = installments;
        this.payment_method_id = paymentMethodId;
        this.payer = userMP;
        this.additional_info = additionalInfoMP;
        this.binary_mode = true;
        this.application_fee = commission * transaction_amount;
    }

    public boolean isBinary_mode() {
        return binary_mode;
    }

    public void setBinary_mode(boolean binary_mode) {
        this.binary_mode = binary_mode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_detail() {
        return status_detail;
    }

    public void setStatus_detail(String status_detail) {
        this.status_detail = status_detail;
    }

    public double getApplication_fee() {
        return application_fee;
    }

    public void setApplication_fee(double application_fee) {
        this.application_fee = application_fee;
    }

    public double getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(double transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public String getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(String payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public UserMP getPayer() {
        return payer;
    }

    public void setPayer(UserMP payer) {
        this.payer = payer;
    }

    public AdditionalInfoMP getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(AdditionalInfoMP additional_info) {
        this.additional_info = additional_info;
    }

}
