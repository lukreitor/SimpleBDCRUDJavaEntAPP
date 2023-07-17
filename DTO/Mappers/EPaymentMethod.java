/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package DTO.Mappers;

/**
 *
 * @author auxlu
 */
public enum EPaymentMethod {
    CREDIT_CARD() {
        @Override
        public String toString() {
            return "Credit Card";
        }
    },

    DEBIT_CARD() {
        @Override
        public String toString() {
            return "Debit Card";
        }
    },

    PAYPAL() {
        @Override
        public String toString() {
            return "PayPal";
        }
    },

    PIX() {
        @Override
        public String toString() {
            return "Pix";
        }
    }
}
