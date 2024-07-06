package com.prajna.mentor_extension.Exchanges;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard {
    Status status;
    List<InlineSuggestion> inlineSuggestion;
    CheckStyle checkStyle;
    Summary summary;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Status {
        Integer noOfQueries;// up till now per user (in demo, only one user)
        Integer inlineCompletions;// up till now counter for all requests in User entity
        Integer overallQuality;// inPercentage
        public Integer getOverAllQuality() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getOverAllQuality'");
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class InlineSuggestion {
        Integer lineNumber;
        String text;// we just suggest an idea, not solution code
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CheckStyle {
        List<Boolean> checkboxes;// boolean checkbox to check the ordered list, find the list below*
        List<List<Integer>> occuranceLineNumbers;// corresponding line numbers for occurances and reason for false
                                                 // occurance at the given line number i.e user didnt follow the rule
        Integer Score; // out of ten
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Summary {
        String strength;
        String scopeForImprovement;
    }
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
