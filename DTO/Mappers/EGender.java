/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package DTO.Mappers;

/**
 *
 * @author auxlu
 */
public enum EGender {
    MALE {
        @Override
        public String toString() {
            return "MALE";
        }
    },

    FEMALE {
        @Override
        public String toString() {
            return "FEMALE";
        }
    },

    OTHER {
        @Override
        public String toString() {
            return "OTHER";
        }
    },
}
