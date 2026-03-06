package com.revature.applicationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class WithdrawRequest {

    @NotBlank(message = "Withdraw reason is required")
    @Size(max = 1000, message = "Withdraw reason cannot exceed 1000 characters")
    private String withdrawReason;

    public WithdrawRequest() {
    }

    public WithdrawRequest(String withdrawReason) {
        this.withdrawReason = withdrawReason;
    }

    public String getWithdrawReason() {
        return withdrawReason;
    }

    public void setWithdrawReason(String withdrawReason) {
        this.withdrawReason = withdrawReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithdrawRequest that = (WithdrawRequest) o;
        return Objects.equals(withdrawReason, that.withdrawReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(withdrawReason);
    }

    @Override
    public String toString() {
        return "WithdrawRequest{" +
                "withdrawReason='" + withdrawReason + '\'' +
                '}';
    }
}
