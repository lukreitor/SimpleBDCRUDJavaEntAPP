/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package DTO.Mappers;

/**
 *
 * @author auxlu
 */
public enum ERole {
    CLIENT {
        @Override
        public String toString() {
            return "CLIENT";
        }
    },
    MANAGER {
        @Override
        public String toString() {
            return "MANAGER";
        }
    },
    TRAINER {
        @Override
        public String toString() {
            return "TRAINER";
        }
    },
    ADMINISTRATOR {
        @Override
        public String toString() {
            return "ADMIN";
        }
    },
    GUEST {
        @Override
        public String toString() {
            return "GUEST";
        }
    },
    USER {
        @Override
        public String toString() {
            return "USER";
        }
    },
    PREMIUM_USER {
        @Override
        public String toString() {
            return "PREMIUM_USER";
        }
    },
    UPLOADER {
        @Override
        public String toString() {
            return "UPLOADER";
        }
    },
    MODERATOR {
        @Override
        public String toString() {
            return "MODERATOR";
        }
    },
    ANALYTICS {
        @Override
        public String toString() {
            return "ANALYTICS";
        }
    },
    MARKETING {
        @Override
        public String toString() {
            return "MARKETING";
        }
    },
    SALES {
        @Override
        public String toString() {
            return "SALES";
        }
    },
    CUSTOMER_SERVICE {
        @Override
        public String toString() {
            return "CUSTOMER_SERVICE";
        }
    },
}
