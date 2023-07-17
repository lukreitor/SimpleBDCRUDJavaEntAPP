/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package DTO.Mappers;

/**
 *
 * @author auxlu
 */
public enum EMembershipStatus {

    ADMINISTRATOR {
        @Override
        public String toString() {
            return "ADMIN";
        }
    },

    ACTIVE {
        @Override
        public String toString() {
            return "ACTIVE";
        }
    },

    INACTIVE {
        @Override
        public String toString() {
            return "INACTIVE";
        }
    },

    BANNED {
        @Override
        public String toString() {
            return "BANNED";
        }
    },

    SUSPENDED {
        @Override
        public String toString() {
            return "SUSPENDED";
        }
    },

    PENDING {
        @Override
        public String toString() {
            return "PENDING";
        }
    }
}
