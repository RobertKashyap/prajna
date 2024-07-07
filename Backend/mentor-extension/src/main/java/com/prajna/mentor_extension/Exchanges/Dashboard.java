package com.prajna.mentor_extension.Exchanges;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dashboard {
    Status status;
    List<InlineSuggestion> inlineSuggestion;
    CheckStyle checkStyle;
    Summary summary;

    /*
     * ordered list:
     * 1.Code Reusability(avoiding repeatation)
     * 2.Secrets of constant and keys
     * 3.Code Structure(no need to check project Structure as its pre-provided by
     * us)
     * 4.Naming Conventions
     * 5.Industrial comments naming Structure
     * 6.Cohesion(well defined functionality)
     * 7.Loose Coupling
     * 8.Avoiding Over Complexity
     * 9.Encapsulation
     * 10.DeadCode(unused code/methods)
     * not checking solid because structure is pre-provided by us
     * not a project assessment but a code evaluator
     */

}
