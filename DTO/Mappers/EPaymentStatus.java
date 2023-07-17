/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package DTO.Mappers;

/**
 *
 * @author auxlu
 */
public enum EPaymentStatus {
    PENDING() {
        @Override
        public String toString() {
            return "Pending";
        }
    },

    PAID() {
        @Override
        public String toString() {
            return "Paid";
        }
    },

    CANCELED() {
        @Override
        public String toString() {
            return "Canceled";
        }
    },

    FAILED() {
        @Override
        public String toString() {
            return "Failed";
        }
    },

    REFUNDED() {
        @Override
        public String toString() {
            return "Refunded";
        }
    },

    DISPUTED() {
        @Override
        public String toString() {
            return "Disputed";
        }
    },

    CHARGEBACK() {
        @Override
        public String toString() {
            return "Chargeback";
        }
    },

    EXPIRED() {
        @Override
        public String toString() {
            return "Expired";
        }
    }
}
