package com.example.jsonschema.web.model;

import lombok.*;

/**
 * Created by IntelliJ IDEA.
 * User: Balaji Varadharajan
 * Date: 9/3/2021
 * Time: 2:58 PM
 * To change this template use File | Settings | File and Code Templates.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passport {

    private Boolean vaccinationProcessBegun;
    private Boolean vaccinationProcessEnded;
    private Boolean testedNegative;
    private String testDate;
    private Boolean hasBeenSickWithCorona;
}
