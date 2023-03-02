package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Geo {
            private String lat;
            private String lng;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Company {
        private String name;
        private String catchPhrase;
        private String bs;
    }
}
